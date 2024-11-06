package jaya.jaramillo.service;

import java.util.Optional;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.HorarioConsulta}.
 */
public interface HorarioConsultaService {
    /**
     * Save a horarioConsulta.
     *
     * @param horarioConsultaDTO the entity to save.
     * @return the persisted entity.
     */
    HorarioConsultaDTO save(HorarioConsultaDTO horarioConsultaDTO);

    /**
     * Updates a horarioConsulta.
     *
     * @param horarioConsultaDTO the entity to update.
     * @return the persisted entity.
     */
    HorarioConsultaDTO update(HorarioConsultaDTO horarioConsultaDTO);

    /**
     * Partially updates a horarioConsulta.
     *
     * @param horarioConsultaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HorarioConsultaDTO> partialUpdate(HorarioConsultaDTO horarioConsultaDTO);

    /**
     * Get all the horarioConsultas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HorarioConsultaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" horarioConsulta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HorarioConsultaDTO> findOne(Long id);

    /**
     * Delete the "id" horarioConsulta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
