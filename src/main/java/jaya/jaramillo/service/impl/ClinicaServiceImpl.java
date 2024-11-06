package jaya.jaramillo.service.impl;

import java.util.Optional;
import jaya.jaramillo.domain.Clinica;
import jaya.jaramillo.repository.ClinicaRepository;
import jaya.jaramillo.service.ClinicaService;
import jaya.jaramillo.service.dto.ClinicaDTO;
import jaya.jaramillo.service.mapper.ClinicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Clinica}.
 */
@Service
@Transactional
public class ClinicaServiceImpl implements ClinicaService {

    private static final Logger LOG = LoggerFactory.getLogger(ClinicaServiceImpl.class);

    private final ClinicaRepository clinicaRepository;

    private final ClinicaMapper clinicaMapper;

    public ClinicaServiceImpl(ClinicaRepository clinicaRepository, ClinicaMapper clinicaMapper) {
        this.clinicaRepository = clinicaRepository;
        this.clinicaMapper = clinicaMapper;
    }

    @Override
    public ClinicaDTO save(ClinicaDTO clinicaDTO) {
        LOG.debug("Request to save Clinica : {}", clinicaDTO);
        Clinica clinica = clinicaMapper.toEntity(clinicaDTO);
        clinica = clinicaRepository.save(clinica);
        return clinicaMapper.toDto(clinica);
    }

    @Override
    public ClinicaDTO update(ClinicaDTO clinicaDTO) {
        LOG.debug("Request to update Clinica : {}", clinicaDTO);
        Clinica clinica = clinicaMapper.toEntity(clinicaDTO);
        clinica = clinicaRepository.save(clinica);
        return clinicaMapper.toDto(clinica);
    }

    @Override
    public Optional<ClinicaDTO> partialUpdate(ClinicaDTO clinicaDTO) {
        LOG.debug("Request to partially update Clinica : {}", clinicaDTO);

        return clinicaRepository
            .findById(clinicaDTO.getId())
            .map(existingClinica -> {
                clinicaMapper.partialUpdate(existingClinica, clinicaDTO);

                return existingClinica;
            })
            .map(clinicaRepository::save)
            .map(clinicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClinicaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Clinicas");
        return clinicaRepository.findAll(pageable).map(clinicaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClinicaDTO> findOne(Long id) {
        LOG.debug("Request to get Clinica : {}", id);
        return clinicaRepository.findById(id).map(clinicaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Clinica : {}", id);
        clinicaRepository.deleteById(id);
    }
}
