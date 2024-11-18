package jaya.jaramillo.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import jaya.jaramillo.domain.HorarioConsulta;
import jaya.jaramillo.domain.Programacion;
import jaya.jaramillo.repository.HorarioConsultaRepository;
import jaya.jaramillo.repository.ProgramacionRepository;
import jaya.jaramillo.service.ProgramacionService;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import jaya.jaramillo.service.dto.ProgramacionDTO;
import jaya.jaramillo.service.dto.TurnoDisponibleDTO;
import jaya.jaramillo.service.dto.TurnoEspecialidadDTO;
import jaya.jaramillo.service.mapper.HorarioConsultaMapper;
import jaya.jaramillo.service.mapper.ProgramacionMapper;
import org.antlr.v4.runtime.atn.EpsilonTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Programacion}.
 */
@Service
@Transactional
public class ProgramacionServiceImpl implements ProgramacionService {

    private static final Logger LOG = LoggerFactory.getLogger(ProgramacionServiceImpl.class);

    private final ProgramacionRepository programacionRepository;

    private final ProgramacionMapper programacionMapper;

    private final HorarioConsultaRepository horarioConsultaRepository;
    private final HorarioConsultaMapper horarioConsultaMapper;

    public ProgramacionServiceImpl(
        ProgramacionRepository programacionRepository,
        ProgramacionMapper programacionMapper,
        HorarioConsultaRepository horarioConsultaRepository,
        HorarioConsultaMapper horarioConsultaMapper
    ) {
        this.programacionRepository = programacionRepository;
        this.programacionMapper = programacionMapper;
        this.horarioConsultaRepository = horarioConsultaRepository;
        this.horarioConsultaMapper = horarioConsultaMapper;
    }

    @Override
    public ProgramacionDTO save(ProgramacionDTO programacionDTO) {
        LOG.debug("Request to save Programacion : {}", programacionDTO);
        Programacion programacion = programacionMapper.toEntity(programacionDTO);
        programacion = programacionRepository.save(programacion);
        return programacionMapper.toDto(programacion);
    }

    @Override
    public ProgramacionDTO update(ProgramacionDTO programacionDTO) {
        LOG.debug("Request to update Programacion : {}", programacionDTO);
        Programacion programacion = programacionMapper.toEntity(programacionDTO);
        programacion = programacionRepository.save(programacion);
        return programacionMapper.toDto(programacion);
    }

    @Override
    public Optional<ProgramacionDTO> partialUpdate(ProgramacionDTO programacionDTO) {
        LOG.debug("Request to partially update Programacion : {}", programacionDTO);

        return programacionRepository
            .findById(programacionDTO.getId())
            .map(existingProgramacion -> {
                programacionMapper.partialUpdate(existingProgramacion, programacionDTO);

                return existingProgramacion;
            })
            .map(programacionRepository::save)
            .map(programacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramacionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Programacions");
        return programacionRepository.findAll(pageable).map(programacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramacionDTO> findOne(Long id) {
        LOG.debug("Request to get Programacion : {}", id);
        return programacionRepository.findById(id).map(programacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Programacion : {}", id);
        programacionRepository.deleteById(id);
    }

    @Override
    public List<TurnoEspecialidadDTO> buscarTurnos(Long especialistaId, LocalDate desde, LocalDate hasta) {
        LOG.debug("Request to buscarTurnos : {}, {}, {}", especialistaId, desde, hasta);
        return programacionRepository.obtenerTurnos(especialistaId, desde, hasta);
    }

    @Override
    public HorarioConsultaDTO crearProgramacion(
        LocalDate desde,
        LocalDate hasta,
        String horaInicio,
        String horaFin,
        String almuerzoDesde,
        String almuerzoHasta,
        Integer duracion,
        String diasSemana,
        Long especialistaId,
        Integer cantidad
    ) {
        LocalTime horaInicioLT = LocalTime.parse(horaInicio);
        LocalTime horaFinLT = LocalTime.parse(horaFin);
        LocalTime desayunoInicioLT = LocalTime.parse(almuerzoDesde);
        LocalTime desayunoFinLT = LocalTime.parse(almuerzoHasta);
        ZoneId zoneId = ZoneId.systemDefault();

        EspecialistaDTO especialista = new EspecialistaDTO();
        especialista.setId(especialistaId);

        HorarioConsultaDTO horario = new HorarioConsultaDTO();
        horario.setDesde(desde);
        horario.setHasta(hasta);
        horario.setHoraInicio(horaInicioLT.atDate(LocalDate.now()).atZone(zoneId).toInstant());
        horario.setHoraFin(horaFinLT.atDate(LocalDate.now()).atZone(zoneId).toInstant());
        horario.setDesdeHoraAlmuerzo(desayunoInicioLT.atDate(LocalDate.now()).atZone(zoneId).toInstant());
        horario.setHastaHoraAlmuerzo(desayunoFinLT.atDate(LocalDate.now()).atZone(zoneId).toInstant());
        horario.setDuracionMinutos(duracion);
        horario.setDiaSemana(diasSemana);
        horario.setEspecialista(especialista);

        boolean hayAlmuerzo = (almuerzoDesde != "" && almuerzoHasta != "");

        HorarioConsulta horarioDB = horarioConsultaRepository.save(this.horarioConsultaMapper.toEntity(horario));
        List<String> diasAplicar = Arrays.asList(diasSemana.split(","));
        LocalDate inicio = desde;
        while (inicio.isBefore(hasta) || inicio.isEqual(hasta)) {
            int diaSemana = inicio.getDayOfWeek().getValue();
            System.out.println(diaSemana);
            if (!diasAplicar.contains("" + diaSemana)) {
                inicio = inicio.plusDays(1L);
                continue;
            }

            LocalTime tiempoInicio = horaInicioLT;
            LocalTime auxiliar = tiempoInicio;
            for (int i = 1; i <= cantidad; i++) {
                tiempoInicio = tiempoInicio.plusMinutes(duracion.longValue());

                if (hayAlmuerzo) {
                    boolean bandera1 = (auxiliar.compareTo(desayunoInicioLT) >= 0 && auxiliar.compareTo(desayunoFinLT) <= 0);
                    boolean bandera2 =
                        (tiempoInicio.compareTo(desayunoInicioLT) >= 0 &&
                            tiempoInicio.compareTo(desayunoFinLT) <= 0 &&
                            !(desayunoInicioLT.compareTo(auxiliar.plusMinutes(duracion.longValue())) == 0));
                    if (bandera1 || bandera2) {
                        System.out.println("aumentar");
                        auxiliar = desayunoFinLT;
                        tiempoInicio = desayunoFinLT.plusMinutes(duracion.longValue());
                    }
                }

                ProgramacionDTO programacion = new ProgramacionDTO();
                programacion.setFecha(inicio);
                programacion.setDesde(auxiliar.atDate(inicio).atZone(ZoneOffset.UTC).toInstant());
                programacion.setHasta(tiempoInicio.atDate(inicio).atZone(ZoneOffset.UTC).toInstant());
                programacion.setHorarioConsulta(horarioConsultaMapper.toDto(horarioDB));

                Programacion programacionDB = programacionRepository.save(this.programacionMapper.toEntity(programacion));
                auxiliar = tiempoInicio;
            }

            inicio = inicio.plusDays(1L);
        }
        return this.horarioConsultaMapper.toDto(horarioDB);
    }

    @Override
    public List<TurnoDisponibleDTO> turnosDisponibles(LocalDate fecha) {
        LOG.debug("Request to turnosDisponibles : {}", fecha);
        return programacionRepository.buscarDisponibles(fecha);
    }
}
