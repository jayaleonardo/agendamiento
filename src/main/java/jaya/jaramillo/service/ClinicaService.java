package jaya.jaramillo.service;

import java.util.Optional;
import jaya.jaramillo.service.dto.ClinicaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.Clinica}.
 */
public interface ClinicaService {
    /**
     * Save a clinica.
     *
     * @param clinicaDTO the entity to save.
     * @return the persisted entity.
     */
    ClinicaDTO save(ClinicaDTO clinicaDTO);

    /**
     * Updates a clinica.
     *
     * @param clinicaDTO the entity to update.
     * @return the persisted entity.
     */
    ClinicaDTO update(ClinicaDTO clinicaDTO);

    /**
     * Partially updates a clinica.
     *
     * @param clinicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClinicaDTO> partialUpdate(ClinicaDTO clinicaDTO);

    /**
     * Get all the clinicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClinicaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clinica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClinicaDTO> findOne(Long id);

    /**
     * Delete the "id" clinica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
