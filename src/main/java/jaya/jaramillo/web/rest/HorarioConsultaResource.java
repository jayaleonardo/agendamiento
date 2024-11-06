package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.HorarioConsultaRepository;
import jaya.jaramillo.service.HorarioConsultaService;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
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
 * REST controller for managing {@link jaya.jaramillo.domain.HorarioConsulta}.
 */
@RestController
@RequestMapping("/api/horario-consultas")
public class HorarioConsultaResource {

    private static final Logger LOG = LoggerFactory.getLogger(HorarioConsultaResource.class);

    private static final String ENTITY_NAME = "horarioConsulta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HorarioConsultaService horarioConsultaService;

    private final HorarioConsultaRepository horarioConsultaRepository;

    public HorarioConsultaResource(HorarioConsultaService horarioConsultaService, HorarioConsultaRepository horarioConsultaRepository) {
        this.horarioConsultaService = horarioConsultaService;
        this.horarioConsultaRepository = horarioConsultaRepository;
    }

    /**
     * {@code POST  /horario-consultas} : Create a new horarioConsulta.
     *
     * @param horarioConsultaDTO the horarioConsultaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new horarioConsultaDTO, or with status {@code 400 (Bad Request)} if the horarioConsulta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HorarioConsultaDTO> createHorarioConsulta(@Valid @RequestBody HorarioConsultaDTO horarioConsultaDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save HorarioConsulta : {}", horarioConsultaDTO);
        if (horarioConsultaDTO.getId() != null) {
            throw new BadRequestAlertException("A new horarioConsulta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        horarioConsultaDTO = horarioConsultaService.save(horarioConsultaDTO);
        return ResponseEntity.created(new URI("/api/horario-consultas/" + horarioConsultaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, horarioConsultaDTO.getId().toString()))
            .body(horarioConsultaDTO);
    }

    /**
     * {@code PUT  /horario-consultas/:id} : Updates an existing horarioConsulta.
     *
     * @param id the id of the horarioConsultaDTO to save.
     * @param horarioConsultaDTO the horarioConsultaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horarioConsultaDTO,
     * or with status {@code 400 (Bad Request)} if the horarioConsultaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the horarioConsultaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HorarioConsultaDTO> updateHorarioConsulta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HorarioConsultaDTO horarioConsultaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update HorarioConsulta : {}, {}", id, horarioConsultaDTO);
        if (horarioConsultaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, horarioConsultaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!horarioConsultaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        horarioConsultaDTO = horarioConsultaService.update(horarioConsultaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, horarioConsultaDTO.getId().toString()))
            .body(horarioConsultaDTO);
    }

    /**
     * {@code PATCH  /horario-consultas/:id} : Partial updates given fields of an existing horarioConsulta, field will ignore if it is null
     *
     * @param id the id of the horarioConsultaDTO to save.
     * @param horarioConsultaDTO the horarioConsultaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated horarioConsultaDTO,
     * or with status {@code 400 (Bad Request)} if the horarioConsultaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the horarioConsultaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the horarioConsultaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HorarioConsultaDTO> partialUpdateHorarioConsulta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HorarioConsultaDTO horarioConsultaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HorarioConsulta partially : {}, {}", id, horarioConsultaDTO);
        if (horarioConsultaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, horarioConsultaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!horarioConsultaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HorarioConsultaDTO> result = horarioConsultaService.partialUpdate(horarioConsultaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, horarioConsultaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /horario-consultas} : get all the horarioConsultas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of horarioConsultas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HorarioConsultaDTO>> getAllHorarioConsultas(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of HorarioConsultas");
        Page<HorarioConsultaDTO> page = horarioConsultaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /horario-consultas/:id} : get the "id" horarioConsulta.
     *
     * @param id the id of the horarioConsultaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the horarioConsultaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HorarioConsultaDTO> getHorarioConsulta(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HorarioConsulta : {}", id);
        Optional<HorarioConsultaDTO> horarioConsultaDTO = horarioConsultaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(horarioConsultaDTO);
    }

    /**
     * {@code DELETE  /horario-consultas/:id} : delete the "id" horarioConsulta.
     *
     * @param id the id of the horarioConsultaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorarioConsulta(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HorarioConsulta : {}", id);
        horarioConsultaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
