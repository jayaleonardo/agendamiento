package jaya.jaramillo.service.impl;

import java.util.List;
import java.util.Optional;
import jaya.jaramillo.domain.Especialista;
import jaya.jaramillo.repository.EspecialistaRepository;
import jaya.jaramillo.service.EspecialistaService;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.mapper.EspecialistaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Especialista}.
 */
@Service
@Transactional
public class EspecialistaServiceImpl implements EspecialistaService {

    private static final Logger LOG = LoggerFactory.getLogger(EspecialistaServiceImpl.class);

    private final EspecialistaRepository especialistaRepository;

    private final EspecialistaMapper especialistaMapper;

    public EspecialistaServiceImpl(EspecialistaRepository especialistaRepository, EspecialistaMapper especialistaMapper) {
        this.especialistaRepository = especialistaRepository;
        this.especialistaMapper = especialistaMapper;
    }

    @Override
    public EspecialistaDTO save(EspecialistaDTO especialistaDTO) {
        LOG.debug("Request to save Especialista : {}", especialistaDTO);
        Especialista especialista = especialistaMapper.toEntity(especialistaDTO);
        especialista = especialistaRepository.save(especialista);
        return especialistaMapper.toDto(especialista);
    }

    @Override
    public EspecialistaDTO update(EspecialistaDTO especialistaDTO) {
        LOG.debug("Request to update Especialista : {}", especialistaDTO);
        Especialista especialista = especialistaMapper.toEntity(especialistaDTO);
        especialista = especialistaRepository.save(especialista);
        return especialistaMapper.toDto(especialista);
    }

    @Override
    public Optional<EspecialistaDTO> partialUpdate(EspecialistaDTO especialistaDTO) {
        LOG.debug("Request to partially update Especialista : {}", especialistaDTO);

        return especialistaRepository
            .findById(especialistaDTO.getId())
            .map(existingEspecialista -> {
                especialistaMapper.partialUpdate(existingEspecialista, especialistaDTO);

                return existingEspecialista;
            })
            .map(especialistaRepository::save)
            .map(especialistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EspecialistaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Especialistas");
        return especialistaRepository.findAll(pageable).map(especialistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EspecialistaDTO> findOne(Long id) {
        LOG.debug("Request to get Especialista : {}", id);
        return especialistaRepository.findById(id).map(especialistaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Especialista : {}", id);
        especialistaRepository.deleteById(id);
    }

    @Override
    public List<EspecialistaDTO> buscarPorEspecialidad(String especialidad) {
        LOG.debug("Request to buscarPorEspecialidad: {}", especialidad);
        return this.especialistaMapper.toDto(this.especialistaRepository.especialistasPorEspecialidad(especialidad));
    }

    @Override
    public List<String> buscarEspecialidades() {
        LOG.debug("Request to buscarEspecialidades");
        return this.especialistaRepository.obtenerEspecialidades();
    }
}
