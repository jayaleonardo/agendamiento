package jaya.jaramillo.service;

import java.util.Optional;
import jaya.jaramillo.service.dto.TipoTerapiaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.TipoTerapia}.
 */
public interface TipoTerapiaService {
    /**
     * Save a tipoTerapia.
     *
     * @param tipoTerapiaDTO the entity to save.
     * @return the persisted entity.
     */
    TipoTerapiaDTO save(TipoTerapiaDTO tipoTerapiaDTO);

    /**
     * Updates a tipoTerapia.
     *
     * @param tipoTerapiaDTO the entity to update.
     * @return the persisted entity.
     */
    TipoTerapiaDTO update(TipoTerapiaDTO tipoTerapiaDTO);

    /**
     * Partially updates a tipoTerapia.
     *
     * @param tipoTerapiaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoTerapiaDTO> partialUpdate(TipoTerapiaDTO tipoTerapiaDTO);

    /**
     * Get all the tipoTerapias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoTerapiaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tipoTerapia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoTerapiaDTO> findOne(Long id);

    /**
     * Delete the "id" tipoTerapia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
