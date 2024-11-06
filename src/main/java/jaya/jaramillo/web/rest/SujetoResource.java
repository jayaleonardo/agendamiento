package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.SujetoRepository;
import jaya.jaramillo.service.SujetoService;
import jaya.jaramillo.service.dto.SujetoDTO;
import jaya.jaramillo.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link jaya.jaramillo.domain.Sujeto}.
 */
@RestController
@RequestMapping("/api/sujetos")
public class SujetoResource {

    private static final Logger LOG = LoggerFactory.getLogger(SujetoResource.class);

    private static final String ENTITY_NAME = "sujeto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SujetoService sujetoService;

    private final SujetoRepository sujetoRepository;

    public SujetoResource(SujetoService sujetoService, SujetoRepository sujetoRepository) {
        this.sujetoService = sujetoService;
        this.sujetoRepository = sujetoRepository;
    }

    /**
     * {@code POST  /sujetos} : Create a new sujeto.
     *
     * @param sujetoDTO the sujetoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sujetoDTO, or with status {@code 400 (Bad Request)} if the sujeto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SujetoDTO> createSujeto(@Valid @RequestBody SujetoDTO sujetoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Sujeto : {}", sujetoDTO);
        if (sujetoDTO.getId() != null) {
            throw new BadRequestAlertException("A new sujeto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sujetoDTO = sujetoService.save(sujetoDTO);
        return ResponseEntity.created(new URI("/api/sujetos/" + sujetoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sujetoDTO.getId().toString()))
            .body(sujetoDTO);
    }

    /**
     * {@code PUT  /sujetos/:id} : Updates an existing sujeto.
     *
     * @param id the id of the sujetoDTO to save.
     * @param sujetoDTO the sujetoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sujetoDTO,
     * or with status {@code 400 (Bad Request)} if the sujetoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sujetoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SujetoDTO> updateSujeto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SujetoDTO sujetoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Sujeto : {}, {}", id, sujetoDTO);
        if (sujetoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sujetoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sujetoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sujetoDTO = sujetoService.update(sujetoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sujetoDTO.getId().toString()))
            .body(sujetoDTO);
    }

    /**
     * {@code PATCH  /sujetos/:id} : Partial updates given fields of an existing sujeto, field will ignore if it is null
     *
     * @param id the id of the sujetoDTO to save.
     * @param sujetoDTO the sujetoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sujetoDTO,
     * or with status {@code 400 (Bad Request)} if the sujetoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sujetoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sujetoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SujetoDTO> partialUpdateSujeto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SujetoDTO sujetoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Sujeto partially : {}, {}", id, sujetoDTO);
        if (sujetoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sujetoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sujetoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SujetoDTO> result = sujetoService.partialUpdate(sujetoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sujetoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sujetos} : get all the sujetos.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sujetos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SujetoDTO>> getAllSujetos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("contacto-is-null".equals(filter)) {
            LOG.debug("REST request to get all Sujetos where contacto is null");
            return new ResponseEntity<>(sujetoService.findAllWhereContactoIsNull(), HttpStatus.OK);
        }

        if ("direccion-is-null".equals(filter)) {
            LOG.debug("REST request to get all Sujetos where direccion is null");
            return new ResponseEntity<>(sujetoService.findAllWhereDireccionIsNull(), HttpStatus.OK);
        }

        if ("especialista-is-null".equals(filter)) {
            LOG.debug("REST request to get all Sujetos where especialista is null");
            return new ResponseEntity<>(sujetoService.findAllWhereEspecialistaIsNull(), HttpStatus.OK);
        }

        if ("paciente-is-null".equals(filter)) {
            LOG.debug("REST request to get all Sujetos where paciente is null");
            return new ResponseEntity<>(sujetoService.findAllWherePacienteIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of Sujetos");
        Page<SujetoDTO> page = sujetoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sujetos/:id} : get the "id" sujeto.
     *
     * @param id the id of the sujetoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sujetoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SujetoDTO> getSujeto(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Sujeto : {}", id);
        Optional<SujetoDTO> sujetoDTO = sujetoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sujetoDTO);
    }

    /**
     * {@code DELETE  /sujetos/:id} : delete the "id" sujeto.
     *
     * @param id the id of the sujetoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSujeto(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Sujeto : {}", id);
        sujetoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
