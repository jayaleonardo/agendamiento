package jaya.jaramillo.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import jaya.jaramillo.service.dto.CitaDTO;
import jaya.jaramillo.service.dto.CitaDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link jaya.jaramillo.domain.Cita}.
 */
public interface CitaService {
    /**
     * Save a cita.
     *
     * @param citaDTO the entity to save.
     * @return the persisted entity.
     */
    CitaDTO save(CitaDTO citaDTO);

    /**
     * Updates a cita.
     *
     * @param citaDTO the entity to update.
     * @return the persisted entity.
     */
    CitaDTO update(CitaDTO citaDTO);

    /**
     * Partially updates a cita.
     *
     * @param citaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CitaDTO> partialUpdate(CitaDTO citaDTO);

    /**
     * Get all the citas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CitaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cita.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CitaDTO> findOne(Long id);

    /**
     * Delete the "id" cita.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CitaDataDTO> buscarCita(
        LocalDate desde,
        LocalDate hasta,
        String especialidad,
        Long especialistaId,
        String estado,
        String criterio
    );

    CitaDTO guardarCita(
        LocalDate fechaCita,
        Instant horaInicio,
        Instant horaFin,
        String estado,
        String tipoVisita,
        String canalAtencion,
        String observacion,
        String motivoConsulta,
        String detalleConsultaVirtual,
        Boolean virtual,
        Long pacienteId,
        Long programacionId,
        Long citaId
    );

    CitaDTO registrarAsistencia(Long citaId, String tarea, String estado);

    CitaDTO guardarPrereserva(
        String nombre,
        String segundoNombre,
        String apellido,
        String segundoApellido,
        String celular,
        Long turnoId,
        Boolean virtual
    );
}
