package jaya.jaramillo.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jaya.jaramillo.domain.Cita;
import jaya.jaramillo.repository.CitaRepository;
import jaya.jaramillo.repository.ProgramacionRepository;
import jaya.jaramillo.service.CitaService;
import jaya.jaramillo.service.dto.CitaDTO;
import jaya.jaramillo.service.dto.CitaDataDTO;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.PacienteDTO;
import jaya.jaramillo.service.dto.ProgramacionDTO;
import jaya.jaramillo.service.mapper.CitaMapper;
import jaya.jaramillo.service.mapper.EspecialistaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Cita}.
 */
@Service
@Transactional
public class CitaServiceImpl implements CitaService {

    private static final Logger LOG = LoggerFactory.getLogger(CitaServiceImpl.class);

    private final CitaRepository citaRepository;

    private final CitaMapper citaMapper;
    private final ProgramacionRepository programacionRepository;
    private final EspecialistaMapper especialistaMapper;

    public CitaServiceImpl(
        CitaRepository citaRepository,
        CitaMapper citaMapper,
        ProgramacionRepository programacionRepository,
        EspecialistaMapper especialistaMapper
    ) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
        this.programacionRepository = programacionRepository;
        this.especialistaMapper = especialistaMapper;
    }

    @Override
    public CitaDTO save(CitaDTO citaDTO) {
        LOG.debug("Request to save Cita : {}", citaDTO);
        Cita cita = citaMapper.toEntity(citaDTO);
        cita = citaRepository.save(cita);
        return citaMapper.toDto(cita);
    }

    @Override
    public CitaDTO update(CitaDTO citaDTO) {
        LOG.debug("Request to update Cita : {}", citaDTO);
        Cita cita = citaMapper.toEntity(citaDTO);
        cita = citaRepository.save(cita);
        return citaMapper.toDto(cita);
    }

    @Override
    public Optional<CitaDTO> partialUpdate(CitaDTO citaDTO) {
        LOG.debug("Request to partially update Cita : {}", citaDTO);

        return citaRepository
            .findById(citaDTO.getId())
            .map(existingCita -> {
                citaMapper.partialUpdate(existingCita, citaDTO);

                return existingCita;
            })
            .map(citaRepository::save)
            .map(citaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CitaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Citas");
        return citaRepository.findAll(pageable).map(citaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CitaDTO> findOne(Long id) {
        LOG.debug("Request to get Cita : {}", id);
        return citaRepository.findById(id).map(citaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Cita : {}", id);
        citaRepository.deleteById(id);
    }

    @Override
    public List<CitaDataDTO> buscarCita(
        LocalDate desde,
        LocalDate hasta,
        String especialidad,
        Long especialistaId,
        String estado,
        String criterio
    ) {
        LOG.debug("Request to buscarCita Cita : {} {} {} {} {} {}", desde, hasta, especialidad, especialistaId, estado, criterio);
        return citaRepository.obtenerCitas(desde, hasta, especialidad, especialistaId, estado, criterio);
    }

    @Override
    public CitaDTO guardarCita(
        LocalDate fechaCita,
        Instant horaInicio,
        Instant horaFin,
        String estado,
        String tipoVisita,
        String canalAtencion,
        String observacion,
        String motivoConsulta,
        String detalleConsultaVirtual,
        Boolean virtual,
        Long pacienteId,
        Long programacionId,
        Long citaId
    ) {
        EspecialistaDTO especialista = especialistaMapper.toDto(programacionRepository.especialistaPorProgramacion(programacionId));

        PacienteDTO paciente = new PacienteDTO();
        paciente.setId(pacienteId);

        ProgramacionDTO programacion = new ProgramacionDTO();
        programacion.setId(programacionId);

        Duration duracion = Duration.between(horaInicio, horaFin);

        CitaDTO cita = null;
        if (citaId != null) {
            // buscar la cita para editarla
            cita = this.citaMapper.toDto(citaRepository.findById(citaId).get());
        } else {
            cita = new CitaDTO();
        }

        cita.setFechaCita(fechaCita);
        cita.setHoraInicio(horaInicio);
        cita.setDuracionMinutos((int) duracion.toMinutes());
        cita.setEstado(estado);
        cita.setTipoVisita(tipoVisita);
        cita.setCanalAtencion(canalAtencion);
        cita.setObservacion(observacion);
        cita.setMotivoConsulta(motivoConsulta);
        if (motivoConsulta.equals("Problemas_pareja")) {
            cita.setDetalleConsultaVirtual(motivoConsulta);
        }
        cita.setVirtual(virtual);
        if (virtual) {
            cita.setDetalleConsultaVirtual(detalleConsultaVirtual);
        }
        cita.setEspecialista(especialista);
        cita.setPaciente(paciente);
        cita.setProgramacion(programacion);

        Cita citaBD = this.citaRepository.save(citaMapper.toEntity(cita));
        return this.citaMapper.toDto(citaBD);
    }

    public CitaDTO registrarAsistencia(Long citaId, String tarea, String estado) {
        CitaDTO cita = this.citaMapper.toDto(citaRepository.findById(citaId).get());
        cita.setEstado(estado);
        return cita;
    }
}
