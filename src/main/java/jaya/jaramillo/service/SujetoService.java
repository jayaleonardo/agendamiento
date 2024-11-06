package jaya.jaramillo.service;

import java.util.List;
import java.util.Optional;
import jaya.jaramillo.service.dto.SujetoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.Sujeto}.
 */
public interface SujetoService {
    /**
     * Save a sujeto.
     *
     * @param sujetoDTO the entity to save.
     * @return the persisted entity.
     */
    SujetoDTO save(SujetoDTO sujetoDTO);

    /**
     * Updates a sujeto.
     *
     * @param sujetoDTO the entity to update.
     * @return the persisted entity.
     */
    SujetoDTO update(SujetoDTO sujetoDTO);

    /**
     * Partially updates a sujeto.
     *
     * @param sujetoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SujetoDTO> partialUpdate(SujetoDTO sujetoDTO);

    /**
     * Get all the sujetos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SujetoDTO> findAll(Pageable pageable);

    /**
     * Get all the SujetoDTO where Contacto is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SujetoDTO> findAllWhereContactoIsNull();
    /**
     * Get all the SujetoDTO where Direccion is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SujetoDTO> findAllWhereDireccionIsNull();
    /**
     * Get all the SujetoDTO where Especialista is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SujetoDTO> findAllWhereEspecialistaIsNull();
    /**
     * Get all the SujetoDTO where Paciente is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<SujetoDTO> findAllWherePacienteIsNull();

    /**
     * Get the "id" sujeto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SujetoDTO> findOne(Long id);

    /**
     * Delete the "id" sujeto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
