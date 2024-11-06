package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.EspecialistaRepository;
import jaya.jaramillo.service.EspecialistaService;
import jaya.jaramillo.service.dto.EspecialistaDTO;
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
 * REST controller for managing {@link jaya.jaramillo.domain.Especialista}.
 */
@RestController
@RequestMapping("/api/especialistas")
public class EspecialistaResource {

    private static final Logger LOG = LoggerFactory.getLogger(EspecialistaResource.class);

    private static final String ENTITY_NAME = "especialista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspecialistaService especialistaService;

    private final EspecialistaRepository especialistaRepository;

    public EspecialistaResource(EspecialistaService especialistaService, EspecialistaRepository especialistaRepository) {
        this.especialistaService = especialistaService;
        this.especialistaRepository = especialistaRepository;
    }

    /**
     * {@code POST  /especialistas} : Create a new especialista.
     *
     * @param especialistaDTO the especialistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especialistaDTO, or with status {@code 400 (Bad Request)} if the especialista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EspecialistaDTO> createEspecialista(@Valid @RequestBody EspecialistaDTO especialistaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Especialista : {}", especialistaDTO);
        if (especialistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new especialista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        especialistaDTO = especialistaService.save(especialistaDTO);
        return ResponseEntity.created(new URI("/api/especialistas/" + especialistaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, especialistaDTO.getId().toString()))
            .body(especialistaDTO);
    }

    /**
     * {@code PUT  /especialistas/:id} : Updates an existing especialista.
     *
     * @param id the id of the especialistaDTO to save.
     * @param especialistaDTO the especialistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialistaDTO,
     * or with status {@code 400 (Bad Request)} if the especialistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especialistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EspecialistaDTO> updateEspecialista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EspecialistaDTO especialistaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Especialista : {}, {}", id, especialistaDTO);
        if (especialistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        especialistaDTO = especialistaService.update(especialistaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialistaDTO.getId().toString()))
            .body(especialistaDTO);
    }

    /**
     * {@code PATCH  /especialistas/:id} : Partial updates given fields of an existing especialista, field will ignore if it is null
     *
     * @param id the id of the especialistaDTO to save.
     * @param especialistaDTO the especialistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especialistaDTO,
     * or with status {@code 400 (Bad Request)} if the especialistaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the especialistaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the especialistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EspecialistaDTO> partialUpdateEspecialista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EspecialistaDTO especialistaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Especialista partially : {}, {}", id, especialistaDTO);
        if (especialistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especialistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especialistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EspecialistaDTO> result = especialistaService.partialUpdate(especialistaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, especialistaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /especialistas} : get all the especialistas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especialistas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EspecialistaDTO>> getAllEspecialistas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Especialistas");
        Page<EspecialistaDTO> page = especialistaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /especialistas/:id} : get the "id" especialista.
     *
     * @param id the id of the especialistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especialistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EspecialistaDTO> getEspecialista(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Especialista : {}", id);
        Optional<EspecialistaDTO> especialistaDTO = especialistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(especialistaDTO);
    }

    /**
     * {@code DELETE  /especialistas/:id} : delete the "id" especialista.
     *
     * @param id the id of the especialistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEspecialista(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Especialista : {}", id);
        especialistaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
