package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.PacienteRepository;
import jaya.jaramillo.service.PacienteService;
import jaya.jaramillo.service.dto.PacienteDTO;
import jaya.jaramillo.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link jaya.jaramillo.domain.Paciente}.
 */
@RestController
@RequestMapping("/api/pacientes")
public class PacienteResource {

    private static final Logger LOG = LoggerFactory.getLogger(PacienteResource.class);

    private static final String ENTITY_NAME = "paciente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PacienteService pacienteService;

    private final PacienteRepository pacienteRepository;

    public PacienteResource(PacienteService pacienteService, PacienteRepository pacienteRepository) {
        this.pacienteService = pacienteService;
        this.pacienteRepository = pacienteRepository;
    }

    /**
     * {@code POST  /pacientes} : Create a new paciente.
     *
     * @param pacienteDTO the pacienteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pacienteDTO, or with status {@code 400 (Bad Request)} if the paciente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PacienteDTO> createPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) throws URISyntaxException {
        LOG.debug("REST request to save Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new paciente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pacienteDTO = pacienteService.save(pacienteDTO);
        return ResponseEntity.created(new URI("/api/pacientes/" + pacienteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pacienteDTO.getId().toString()))
            .body(pacienteDTO);
    }

    /**
     * {@code PUT  /pacientes/:id} : Updates an existing paciente.
     *
     * @param id the id of the pacienteDTO to save.
     * @param pacienteDTO the pacienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacienteDTO,
     * or with status {@code 400 (Bad Request)} if the pacienteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pacienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> updatePaciente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PacienteDTO pacienteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Paciente : {}, {}", id, pacienteDTO);
        if (pacienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pacienteDTO = pacienteService.update(pacienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacienteDTO.getId().toString()))
            .body(pacienteDTO);
    }

    /**
     * {@code PATCH  /pacientes/:id} : Partial updates given fields of an existing paciente, field will ignore if it is null
     *
     * @param id the id of the pacienteDTO to save.
     * @param pacienteDTO the pacienteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pacienteDTO,
     * or with status {@code 400 (Bad Request)} if the pacienteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pacienteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pacienteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PacienteDTO> partialUpdatePaciente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PacienteDTO pacienteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Paciente partially : {}, {}", id, pacienteDTO);
        if (pacienteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pacienteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pacienteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PacienteDTO> result = pacienteService.partialUpdate(pacienteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pacienteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pacientes} : get all the pacientes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pacientes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PacienteDTO>> getAllPacientes(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Pacientes");
        Page<PacienteDTO> page = pacienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pacientes/:id} : get the "id" paciente.
     *
     * @param id the id of the pacienteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pacienteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Paciente : {}", id);
        Optional<PacienteDTO> pacienteDTO = pacienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pacienteDTO);
    }

    /**
     * {@code DELETE  /pacientes/:id} : delete the "id" paciente.
     *
     * @param id the id of the pacienteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Paciente : {}", id);
        pacienteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
