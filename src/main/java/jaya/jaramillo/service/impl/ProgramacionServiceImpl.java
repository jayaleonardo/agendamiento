package jaya.jaramillo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jaya.jaramillo.domain.Programacion;
import jaya.jaramillo.repository.ProgramacionRepository;
import jaya.jaramillo.service.ProgramacionService;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import jaya.jaramillo.service.dto.ProgramacionDTO;
import jaya.jaramillo.service.dto.TurnoEspecialidadDTO;
import jaya.jaramillo.service.mapper.ProgramacionMapper;
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

    public ProgramacionServiceImpl(ProgramacionRepository programacionRepository, ProgramacionMapper programacionMapper) {
        this.programacionRepository = programacionRepository;
        this.programacionMapper = programacionMapper;
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
	public HorarioConsultaDTO crearProgramacion(LocalDate desde, LocalDate hasta, String horaInicio, String horaFin,
			String almuerzoDesde, String almuerzoHasta) {

        HorarioConsultaDTO horario = new HorarioConsultaDTO();
        return horario;
	}



}
