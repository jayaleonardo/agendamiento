package jaya.jaramillo.service.impl;

import java.util.Optional;
import jaya.jaramillo.domain.Direccion;
import jaya.jaramillo.repository.DireccionRepository;
import jaya.jaramillo.service.DireccionService;
import jaya.jaramillo.service.dto.DireccionDTO;
import jaya.jaramillo.service.mapper.DireccionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Direccion}.
 */
@Service
@Transactional
public class DireccionServiceImpl implements DireccionService {

    private static final Logger LOG = LoggerFactory.getLogger(DireccionServiceImpl.class);

    private final DireccionRepository direccionRepository;

    private final DireccionMapper direccionMapper;

    public DireccionServiceImpl(DireccionRepository direccionRepository, DireccionMapper direccionMapper) {
        this.direccionRepository = direccionRepository;
        this.direccionMapper = direccionMapper;
    }

    @Override
    public DireccionDTO save(DireccionDTO direccionDTO) {
        LOG.debug("Request to save Direccion : {}", direccionDTO);
        Direccion direccion = direccionMapper.toEntity(direccionDTO);
        direccion = direccionRepository.save(direccion);
        return direccionMapper.toDto(direccion);
    }

    @Override
    public DireccionDTO update(DireccionDTO direccionDTO) {
        LOG.debug("Request to update Direccion : {}", direccionDTO);
        Direccion direccion = direccionMapper.toEntity(direccionDTO);
        direccion = direccionRepository.save(direccion);
        return direccionMapper.toDto(direccion);
    }

    @Override
    public Optional<DireccionDTO> partialUpdate(DireccionDTO direccionDTO) {
        LOG.debug("Request to partially update Direccion : {}", direccionDTO);

        return direccionRepository
            .findById(direccionDTO.getId())
            .map(existingDireccion -> {
                direccionMapper.partialUpdate(existingDireccion, direccionDTO);

                return existingDireccion;
            })
            .map(direccionRepository::save)
            .map(direccionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DireccionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Direccions");
        return direccionRepository.findAll(pageable).map(direccionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DireccionDTO> findOne(Long id) {
        LOG.debug("Request to get Direccion : {}", id);
        return direccionRepository.findById(id).map(direccionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Direccion : {}", id);
        direccionRepository.deleteById(id);
    }
}
