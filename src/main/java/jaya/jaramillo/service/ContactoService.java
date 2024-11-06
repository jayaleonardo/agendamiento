package jaya.jaramillo.service;

import java.util.Optional;
import jaya.jaramillo.service.dto.ContactoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.Contacto}.
 */
public interface ContactoService {
    /**
     * Save a contacto.
     *
     * @param contactoDTO the entity to save.
     * @return the persisted entity.
     */
    ContactoDTO save(ContactoDTO contactoDTO);

    /**
     * Updates a contacto.
     *
     * @param contactoDTO the entity to update.
     * @return the persisted entity.
     */
    ContactoDTO update(ContactoDTO contactoDTO);

    /**
     * Partially updates a contacto.
     *
     * @param contactoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactoDTO> partialUpdate(ContactoDTO contactoDTO);

    /**
     * Get all the contactos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" contacto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactoDTO> findOne(Long id);

    /**
     * Delete the "id" contacto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
