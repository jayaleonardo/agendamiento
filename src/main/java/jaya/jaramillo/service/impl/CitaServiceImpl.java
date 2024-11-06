package jaya.jaramillo.service.impl;

import java.util.Optional;
import jaya.jaramillo.domain.Cita;
import jaya.jaramillo.repository.CitaRepository;
import jaya.jaramillo.service.CitaService;
import jaya.jaramillo.service.dto.CitaDTO;
import jaya.jaramillo.service.mapper.CitaMapper;
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

    public CitaServiceImpl(CitaRepository citaRepository, CitaMapper citaMapper) {
        this.citaRepository = citaRepository;
        this.citaMapper = citaMapper;
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
}
