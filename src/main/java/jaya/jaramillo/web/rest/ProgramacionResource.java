package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.ProgramacionRepository;
import jaya.jaramillo.service.ProgramacionService;
import jaya.jaramillo.service.dto.ProgramacionDTO;
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
 * REST controller for managing {@link jaya.jaramillo.domain.Programacion}.
 */
@RestController
@RequestMapping("/api/programacions")
public class ProgramacionResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProgramacionResource.class);

    private static final String ENTITY_NAME = "programacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramacionService programacionService;

    private final ProgramacionRepository programacionRepository;

    public ProgramacionResource(ProgramacionService programacionService, ProgramacionRepository programacionRepository) {
        this.programacionService = programacionService;
        this.programacionRepository = programacionRepository;
    }

    /**
     * {@code POST  /programacions} : Create a new programacion.
     *
     * @param programacionDTO the programacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programacionDTO, or with status {@code 400 (Bad Request)} if the programacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProgramacionDTO> createProgramacion(@Valid @RequestBody ProgramacionDTO programacionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Programacion : {}", programacionDTO);
        if (programacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new programacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        programacionDTO = programacionService.save(programacionDTO);
        return ResponseEntity.created(new URI("/api/programacions/" + programacionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, programacionDTO.getId().toString()))
            .body(programacionDTO);
    }

    /**
     * {@code PUT  /programacions/:id} : Updates an existing programacion.
     *
     * @param id the id of the programacionDTO to save.
     * @param programacionDTO the programacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programacionDTO,
     * or with status {@code 400 (Bad Request)} if the programacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProgramacionDTO> updateProgramacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProgramacionDTO programacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Programacion : {}, {}", id, programacionDTO);
        if (programacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        programacionDTO = programacionService.update(programacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programacionDTO.getId().toString()))
            .body(programacionDTO);
    }

    /**
     * {@code PATCH  /programacions/:id} : Partial updates given fields of an existing programacion, field will ignore if it is null
     *
     * @param id the id of the programacionDTO to save.
     * @param programacionDTO the programacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programacionDTO,
     * or with status {@code 400 (Bad Request)} if the programacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the programacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the programacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProgramacionDTO> partialUpdateProgramacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProgramacionDTO programacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Programacion partially : {}, {}", id, programacionDTO);
        if (programacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProgramacionDTO> result = programacionService.partialUpdate(programacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /programacions} : get all the programacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programacions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProgramacionDTO>> getAllProgramacions(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Programacions");
        Page<ProgramacionDTO> page = programacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /programacions/:id} : get the "id" programacion.
     *
     * @param id the id of the programacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgramacionDTO> getProgramacion(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Programacion : {}", id);
        Optional<ProgramacionDTO> programacionDTO = programacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programacionDTO);
    }

    /**
     * {@code DELETE  /programacions/:id} : delete the "id" programacion.
     *
     * @param id the id of the programacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramacion(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Programacion : {}", id);
        programacionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
