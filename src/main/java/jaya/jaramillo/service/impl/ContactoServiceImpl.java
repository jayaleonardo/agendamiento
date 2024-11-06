package jaya.jaramillo.service.impl;

import java.util.Optional;
import jaya.jaramillo.domain.Contacto;
import jaya.jaramillo.repository.ContactoRepository;
import jaya.jaramillo.service.ContactoService;
import jaya.jaramillo.service.dto.ContactoDTO;
import jaya.jaramillo.service.mapper.ContactoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.Contacto}.
 */
@Service
@Transactional
public class ContactoServiceImpl implements ContactoService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactoServiceImpl.class);

    private final ContactoRepository contactoRepository;

    private final ContactoMapper contactoMapper;

    public ContactoServiceImpl(ContactoRepository contactoRepository, ContactoMapper contactoMapper) {
        this.contactoRepository = contactoRepository;
        this.contactoMapper = contactoMapper;
    }

    @Override
    public ContactoDTO save(ContactoDTO contactoDTO) {
        LOG.debug("Request to save Contacto : {}", contactoDTO);
        Contacto contacto = contactoMapper.toEntity(contactoDTO);
        contacto = contactoRepository.save(contacto);
        return contactoMapper.toDto(contacto);
    }

    @Override
    public ContactoDTO update(ContactoDTO contactoDTO) {
        LOG.debug("Request to update Contacto : {}", contactoDTO);
        Contacto contacto = contactoMapper.toEntity(contactoDTO);
        contacto = contactoRepository.save(contacto);
        return contactoMapper.toDto(contacto);
    }

    @Override
    public Optional<ContactoDTO> partialUpdate(ContactoDTO contactoDTO) {
        LOG.debug("Request to partially update Contacto : {}", contactoDTO);

        return contactoRepository
            .findById(contactoDTO.getId())
            .map(existingContacto -> {
                contactoMapper.partialUpdate(existingContacto, contactoDTO);

                return existingContacto;
            })
            .map(contactoRepository::save)
            .map(contactoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContactoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Contactos");
        return contactoRepository.findAll(pageable).map(contactoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactoDTO> findOne(Long id) {
        LOG.debug("Request to get Contacto : {}", id);
        return contactoRepository.findById(id).map(contactoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Contacto : {}", id);
        contactoRepository.deleteById(id);
    }
}
