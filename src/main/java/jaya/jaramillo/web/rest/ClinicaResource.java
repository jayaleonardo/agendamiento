package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.ClinicaRepository;
import jaya.jaramillo.service.ClinicaService;
import jaya.jaramillo.service.dto.ClinicaDTO;
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
 * REST controller for managing {@link jaya.jaramillo.domain.Clinica}.
 */
@RestController
@RequestMapping("/api/clinicas")
public class ClinicaResource {

    private static final Logger LOG = LoggerFactory.getLogger(ClinicaResource.class);

    private static final String ENTITY_NAME = "clinica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClinicaService clinicaService;

    private final ClinicaRepository clinicaRepository;

    public ClinicaResource(ClinicaService clinicaService, ClinicaRepository clinicaRepository) {
        this.clinicaService = clinicaService;
        this.clinicaRepository = clinicaRepository;
    }

    /**
     * {@code POST  /clinicas} : Create a new clinica.
     *
     * @param clinicaDTO the clinicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clinicaDTO, or with status {@code 400 (Bad Request)} if the clinica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ClinicaDTO> createClinica(@Valid @RequestBody ClinicaDTO clinicaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Clinica : {}", clinicaDTO);
        if (clinicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new clinica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        clinicaDTO = clinicaService.save(clinicaDTO);
        return ResponseEntity.created(new URI("/api/clinicas/" + clinicaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, clinicaDTO.getId().toString()))
            .body(clinicaDTO);
    }

    /**
     * {@code PUT  /clinicas/:id} : Updates an existing clinica.
     *
     * @param id the id of the clinicaDTO to save.
     * @param clinicaDTO the clinicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clinicaDTO,
     * or with status {@code 400 (Bad Request)} if the clinicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clinicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClinicaDTO> updateClinica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClinicaDTO clinicaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Clinica : {}, {}", id, clinicaDTO);
        if (clinicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clinicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clinicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        clinicaDTO = clinicaService.update(clinicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clinicaDTO.getId().toString()))
            .body(clinicaDTO);
    }

    /**
     * {@code PATCH  /clinicas/:id} : Partial updates given fields of an existing clinica, field will ignore if it is null
     *
     * @param id the id of the clinicaDTO to save.
     * @param clinicaDTO the clinicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clinicaDTO,
     * or with status {@code 400 (Bad Request)} if the clinicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clinicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clinicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClinicaDTO> partialUpdateClinica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClinicaDTO clinicaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Clinica partially : {}, {}", id, clinicaDTO);
        if (clinicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clinicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clinicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClinicaDTO> result = clinicaService.partialUpdate(clinicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clinicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /clinicas} : get all the clinicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clinicas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ClinicaDTO>> getAllClinicas(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Clinicas");
        Page<ClinicaDTO> page = clinicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clinicas/:id} : get the "id" clinica.
     *
     * @param id the id of the clinicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clinicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClinicaDTO> getClinica(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Clinica : {}", id);
        Optional<ClinicaDTO> clinicaDTO = clinicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clinicaDTO);
    }

    /**
     * {@code DELETE  /clinicas/:id} : delete the "id" clinica.
     *
     * @param id the id of the clinicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinica(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Clinica : {}", id);
        clinicaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
