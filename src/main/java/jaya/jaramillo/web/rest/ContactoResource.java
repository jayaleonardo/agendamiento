package jaya.jaramillo.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import jaya.jaramillo.repository.ContactoRepository;
import jaya.jaramillo.service.ContactoService;
import jaya.jaramillo.service.dto.ContactoDTO;
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
 * REST controller for managing {@link jaya.jaramillo.domain.Contacto}.
 */
@RestController
@RequestMapping("/api/contactos")
public class ContactoResource {

    private static final Logger LOG = LoggerFactory.getLogger(ContactoResource.class);

    private static final String ENTITY_NAME = "contacto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoService contactoService;

    private final ContactoRepository contactoRepository;

    public ContactoResource(ContactoService contactoService, ContactoRepository contactoRepository) {
        this.contactoService = contactoService;
        this.contactoRepository = contactoRepository;
    }

    /**
     * {@code POST  /contactos} : Create a new contacto.
     *
     * @param contactoDTO the contactoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactoDTO, or with status {@code 400 (Bad Request)} if the contacto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContactoDTO> createContacto(@Valid @RequestBody ContactoDTO contactoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Contacto : {}", contactoDTO);
        if (contactoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contacto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contactoDTO = contactoService.save(contactoDTO);
        return ResponseEntity.created(new URI("/api/contactos/" + contactoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contactoDTO.getId().toString()))
            .body(contactoDTO);
    }

    /**
     * {@code PUT  /contactos/:id} : Updates an existing contacto.
     *
     * @param id the id of the contactoDTO to save.
     * @param contactoDTO the contactoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoDTO,
     * or with status {@code 400 (Bad Request)} if the contactoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContactoDTO> updateContacto(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactoDTO contactoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Contacto : {}, {}", id, contactoDTO);
        if (contactoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contactoDTO = contactoService.update(contactoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoDTO.getId().toString()))
            .body(contactoDTO);
    }

    /**
     * {@code PATCH  /contactos/:id} : Partial updates given fields of an existing contacto, field will ignore if it is null
     *
     * @param id the id of the contactoDTO to save.
     * @param contactoDTO the contactoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoDTO,
     * or with status {@code 400 (Bad Request)} if the contactoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contactoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactoDTO> partialUpdateContacto(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactoDTO contactoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Contacto partially : {}, {}", id, contactoDTO);
        if (contactoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactoDTO> result = contactoService.partialUpdate(contactoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contactos} : get all the contactos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContactoDTO>> getAllContactos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Contactos");
        Page<ContactoDTO> page = contactoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contactos/:id} : get the "id" contacto.
     *
     * @param id the id of the contactoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactoDTO> getContacto(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Contacto : {}", id);
        Optional<ContactoDTO> contactoDTO = contactoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoDTO);
    }

    /**
     * {@code DELETE  /contactos/:id} : delete the "id" contacto.
     *
     * @param id the id of the contactoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Contacto : {}", id);
        contactoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
