package jaya.jaramillo.service.impl;

import java.util.Optional;
import jaya.jaramillo.domain.Paciente;
import jaya.jaramillo.repository.PacienteRepository;
import jaya.jaramillo.service.PacienteService;
import jaya.jaramillo.service.dto.PacienteDTO;
import jaya.jaramillo.service.mapper.PacienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Paciente}.
 */
@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private static final Logger LOG = LoggerFactory.getLogger(PacienteServiceImpl.class);

    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    public PacienteServiceImpl(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    @Override
    public PacienteDTO save(PacienteDTO pacienteDTO) {
        LOG.debug("Request to save Paciente : {}", pacienteDTO);
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public PacienteDTO update(PacienteDTO pacienteDTO) {
        LOG.debug("Request to update Paciente : {}", pacienteDTO);
        Paciente paciente = pacienteMapper.toEntity(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public Optional<PacienteDTO> partialUpdate(PacienteDTO pacienteDTO) {
        LOG.debug("Request to partially update Paciente : {}", pacienteDTO);

        return pacienteRepository
            .findById(pacienteDTO.getId())
            .map(existingPaciente -> {
                pacienteMapper.partialUpdate(existingPaciente, pacienteDTO);

                return existingPaciente;
            })
            .map(pacienteRepository::save)
            .map(pacienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Pacientes");
        return pacienteRepository.findAll(pageable).map(pacienteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PacienteDTO> findOne(Long id) {
        LOG.debug("Request to get Paciente : {}", id);
        return pacienteRepository.findById(id).map(pacienteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Paciente : {}", id);
        pacienteRepository.deleteById(id);
    }
}
