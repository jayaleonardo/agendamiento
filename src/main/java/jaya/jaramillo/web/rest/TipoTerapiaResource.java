package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.TipoTerapiaRepository;
import jaya.jaramillo.service.TipoTerapiaService;
import jaya.jaramillo.service.dto.TipoTerapiaDTO;
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
 * REST controller for managing {@link jaya.jaramillo.domain.TipoTerapia}.
 */
@RestController
@RequestMapping("/api/tipo-terapias")
public class TipoTerapiaResource {

    private static final Logger LOG = LoggerFactory.getLogger(TipoTerapiaResource.class);

    private static final String ENTITY_NAME = "tipoTerapia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoTerapiaService tipoTerapiaService;

    private final TipoTerapiaRepository tipoTerapiaRepository;

    public TipoTerapiaResource(TipoTerapiaService tipoTerapiaService, TipoTerapiaRepository tipoTerapiaRepository) {
        this.tipoTerapiaService = tipoTerapiaService;
        this.tipoTerapiaRepository = tipoTerapiaRepository;
    }

    /**
     * {@code POST  /tipo-terapias} : Create a new tipoTerapia.
     *
     * @param tipoTerapiaDTO the tipoTerapiaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoTerapiaDTO, or with status {@code 400 (Bad Request)} if the tipoTerapia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TipoTerapiaDTO> createTipoTerapia(@Valid @RequestBody TipoTerapiaDTO tipoTerapiaDTO) throws URISyntaxException {
        LOG.debug("REST request to save TipoTerapia : {}", tipoTerapiaDTO);
        if (tipoTerapiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoTerapia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tipoTerapiaDTO = tipoTerapiaService.save(tipoTerapiaDTO);
        return ResponseEntity.created(new URI("/api/tipo-terapias/" + tipoTerapiaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tipoTerapiaDTO.getId().toString()))
            .body(tipoTerapiaDTO);
    }

    /**
     * {@code PUT  /tipo-terapias/:id} : Updates an existing tipoTerapia.
     *
     * @param id the id of the tipoTerapiaDTO to save.
     * @param tipoTerapiaDTO the tipoTerapiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoTerapiaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoTerapiaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoTerapiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoTerapiaDTO> updateTipoTerapia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoTerapiaDTO tipoTerapiaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TipoTerapia : {}, {}", id, tipoTerapiaDTO);
        if (tipoTerapiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoTerapiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoTerapiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tipoTerapiaDTO = tipoTerapiaService.update(tipoTerapiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoTerapiaDTO.getId().toString()))
            .body(tipoTerapiaDTO);
    }

    /**
     * {@code PATCH  /tipo-terapias/:id} : Partial updates given fields of an existing tipoTerapia, field will ignore if it is null
     *
     * @param id the id of the tipoTerapiaDTO to save.
     * @param tipoTerapiaDTO the tipoTerapiaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoTerapiaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoTerapiaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoTerapiaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoTerapiaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoTerapiaDTO> partialUpdateTipoTerapia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoTerapiaDTO tipoTerapiaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TipoTerapia partially : {}, {}", id, tipoTerapiaDTO);
        if (tipoTerapiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoTerapiaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoTerapiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoTerapiaDTO> result = tipoTerapiaService.partialUpdate(tipoTerapiaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoTerapiaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-terapias} : get all the tipoTerapias.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoTerapias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TipoTerapiaDTO>> getAllTipoTerapias(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of TipoTerapias");
        Page<TipoTerapiaDTO> page = tipoTerapiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-terapias/:id} : get the "id" tipoTerapia.
     *
     * @param id the id of the tipoTerapiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoTerapiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoTerapiaDTO> getTipoTerapia(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TipoTerapia : {}", id);
        Optional<TipoTerapiaDTO> tipoTerapiaDTO = tipoTerapiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoTerapiaDTO);
    }

    /**
     * {@code DELETE  /tipo-terapias/:id} : delete the "id" tipoTerapia.
     *
     * @param id the id of the tipoTerapiaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoTerapia(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TipoTerapia : {}", id);
        tipoTerapiaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
