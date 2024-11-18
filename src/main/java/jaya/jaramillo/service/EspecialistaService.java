package jaya.jaramillo.service;

import java.util.List;
import java.util.Optional;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.Especialista}.
 */
public interface EspecialistaService {
    /**
     * Save a especialista.
     *
     * @param especialistaDTO the entity to save.
     * @return the persisted entity.
     */
    EspecialistaDTO save(EspecialistaDTO especialistaDTO);

    /**
     * Updates a especialista.
     *
     * @param especialistaDTO the entity to update.
     * @return the persisted entity.
     */
    EspecialistaDTO update(EspecialistaDTO especialistaDTO);

    /**
     * Partially updates a especialista.
     *
     * @param especialistaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EspecialistaDTO> partialUpdate(EspecialistaDTO especialistaDTO);

    /**
     * Get all the especialistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EspecialistaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" especialista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EspecialistaDTO> findOne(Long id);

    /**
     * Delete the "id" especialista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<EspecialistaDTO> buscarPorEspecialidad(String especialidad);

    List<String> buscarEspecialidades();

    EspecialistaDTO fotoEspecialista(Long especialistaId);
}
