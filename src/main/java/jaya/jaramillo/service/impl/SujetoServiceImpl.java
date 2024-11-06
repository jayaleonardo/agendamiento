package jaya.jaramillo.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import jaya.jaramillo.domain.Sujeto;
import jaya.jaramillo.repository.SujetoRepository;
import jaya.jaramillo.service.SujetoService;
import jaya.jaramillo.service.dto.SujetoDTO;
import jaya.jaramillo.service.mapper.SujetoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Sujeto}.
 */
@Service
@Transactional
public class SujetoServiceImpl implements SujetoService {

    private static final Logger LOG = LoggerFactory.getLogger(SujetoServiceImpl.class);

    private final SujetoRepository sujetoRepository;

    private final SujetoMapper sujetoMapper;

    public SujetoServiceImpl(SujetoRepository sujetoRepository, SujetoMapper sujetoMapper) {
        this.sujetoRepository = sujetoRepository;
        this.sujetoMapper = sujetoMapper;
    }

    @Override
    public SujetoDTO save(SujetoDTO sujetoDTO) {
        LOG.debug("Request to save Sujeto : {}", sujetoDTO);
        Sujeto sujeto = sujetoMapper.toEntity(sujetoDTO);
        sujeto = sujetoRepository.save(sujeto);
        return sujetoMapper.toDto(sujeto);
    }

    @Override
    public SujetoDTO update(SujetoDTO sujetoDTO) {
        LOG.debug("Request to update Sujeto : {}", sujetoDTO);
        Sujeto sujeto = sujetoMapper.toEntity(sujetoDTO);
        sujeto = sujetoRepository.save(sujeto);
        return sujetoMapper.toDto(sujeto);
    }

    @Override
    public Optional<SujetoDTO> partialUpdate(SujetoDTO sujetoDTO) {
        LOG.debug("Request to partially update Sujeto : {}", sujetoDTO);

        return sujetoRepository
            .findById(sujetoDTO.getId())
            .map(existingSujeto -> {
                sujetoMapper.partialUpdate(existingSujeto, sujetoDTO);

                return existingSujeto;
            })
            .map(sujetoRepository::save)
            .map(sujetoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SujetoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Sujetos");
        return sujetoRepository.findAll(pageable).map(sujetoMapper::toDto);
    }

    /**
     *  Get all the sujetos where Contacto is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SujetoDTO> findAllWhereContactoIsNull() {
        LOG.debug("Request to get all sujetos where Contacto is null");
        return StreamSupport.stream(sujetoRepository.findAll().spliterator(), false)
            .filter(sujeto -> sujeto.getContacto() == null)
            .map(sujetoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the sujetos where Direccion is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SujetoDTO> findAllWhereDireccionIsNull() {
        LOG.debug("Request to get all sujetos where Direccion is null");
        return StreamSupport.stream(sujetoRepository.findAll().spliterator(), false)
            .filter(sujeto -> sujeto.getDireccion() == null)
            .map(sujetoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the sujetos where Especialista is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SujetoDTO> findAllWhereEspecialistaIsNull() {
        LOG.debug("Request to get all sujetos where Especialista is null");
        return StreamSupport.stream(sujetoRepository.findAll().spliterator(), false)
            .filter(sujeto -> sujeto.getEspecialista() == null)
            .map(sujetoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the sujetos where Paciente is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SujetoDTO> findAllWherePacienteIsNull() {
        LOG.debug("Request to get all sujetos where Paciente is null");
        return StreamSupport.stream(sujetoRepository.findAll().spliterator(), false)
            .filter(sujeto -> sujeto.getPaciente() == null)
            .map(sujetoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SujetoDTO> findOne(Long id) {
        LOG.debug("Request to get Sujeto : {}", id);
        return sujetoRepository.findById(id).map(sujetoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Sujeto : {}", id);
        sujetoRepository.deleteById(id);
    }
}
