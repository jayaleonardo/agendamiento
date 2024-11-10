package jaya.jaramillo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import jaya.jaramillo.service.dto.ProgramacionDTO;
import jaya.jaramillo.service.dto.TurnoEspecialidadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.Programacion}.
 */
public interface ProgramacionService {
    /**
     * Save a programacion.
     *
     * @param programacionDTO the entity to save.
     * @return the persisted entity.
     */
    ProgramacionDTO save(ProgramacionDTO programacionDTO);

    /**
     * Updates a programacion.
     *
     * @param programacionDTO the entity to update.
     * @return the persisted entity.
     */
    ProgramacionDTO update(ProgramacionDTO programacionDTO);

    /**
     * Partially updates a programacion.
     *
     * @param programacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProgramacionDTO> partialUpdate(ProgramacionDTO programacionDTO);

    /**
     * Get all the programacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProgramacionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" programacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramacionDTO> findOne(Long id);

    /**
     * Delete the "id" programacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<TurnoEspecialidadDTO> buscarTurnos(Long especialistaId, LocalDate desde, LocalDate hasta);

    HorarioConsultaDTO crearProgramacion(
        LocalDate desde,
        LocalDate hasta,
        String horaInicio,
        String horaFin,
        String almuerzoDesde,
        String almuerzoHasta,
        Integer duracion,
        String diasSemana,
        Long especialistaId,
        Integer cantidad
    );
}
