package jaya.jaramillo.service.impl;

import java.util.Optional;
import jaya.jaramillo.domain.TipoTerapia;
import jaya.jaramillo.repository.TipoTerapiaRepository;
import jaya.jaramillo.service.TipoTerapiaService;
import jaya.jaramillo.service.dto.TipoTerapiaDTO;
import jaya.jaramillo.service.mapper.TipoTerapiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.TipoTerapia}.
 */
@Service
@Transactional
public class TipoTerapiaServiceImpl implements TipoTerapiaService {

    private static final Logger LOG = LoggerFactory.getLogger(TipoTerapiaServiceImpl.class);

    private final TipoTerapiaRepository tipoTerapiaRepository;

    private final TipoTerapiaMapper tipoTerapiaMapper;

    public TipoTerapiaServiceImpl(TipoTerapiaRepository tipoTerapiaRepository, TipoTerapiaMapper tipoTerapiaMapper) {
        this.tipoTerapiaRepository = tipoTerapiaRepository;
        this.tipoTerapiaMapper = tipoTerapiaMapper;
    }

    @Override
    public TipoTerapiaDTO save(TipoTerapiaDTO tipoTerapiaDTO) {
        LOG.debug("Request to save TipoTerapia : {}", tipoTerapiaDTO);
        TipoTerapia tipoTerapia = tipoTerapiaMapper.toEntity(tipoTerapiaDTO);
        tipoTerapia = tipoTerapiaRepository.save(tipoTerapia);
        return tipoTerapiaMapper.toDto(tipoTerapia);
    }

    @Override
    public TipoTerapiaDTO update(TipoTerapiaDTO tipoTerapiaDTO) {
        LOG.debug("Request to update TipoTerapia : {}", tipoTerapiaDTO);
        TipoTerapia tipoTerapia = tipoTerapiaMapper.toEntity(tipoTerapiaDTO);
        tipoTerapia = tipoTerapiaRepository.save(tipoTerapia);
        return tipoTerapiaMapper.toDto(tipoTerapia);
    }

    @Override
    public Optional<TipoTerapiaDTO> partialUpdate(TipoTerapiaDTO tipoTerapiaDTO) {
        LOG.debug("Request to partially update TipoTerapia : {}", tipoTerapiaDTO);

        return tipoTerapiaRepository
            .findById(tipoTerapiaDTO.getId())
            .map(existingTipoTerapia -> {
                tipoTerapiaMapper.partialUpdate(existingTipoTerapia, tipoTerapiaDTO);

                return existingTipoTerapia;
            })
            .map(tipoTerapiaRepository::save)
            .map(tipoTerapiaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoTerapiaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all TipoTerapias");
        return tipoTerapiaRepository.findAll(pageable).map(tipoTerapiaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoTerapiaDTO> findOne(Long id) {
        LOG.debug("Request to get TipoTerapia : {}", id);
        return tipoTerapiaRepository.findById(id).map(tipoTerapiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete TipoTerapia : {}", id);
        tipoTerapiaRepository.deleteById(id);
    }
}
