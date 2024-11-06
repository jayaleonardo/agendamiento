package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.ContactoAsserts.*;
import static jaya.jaramillo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import jaya.jaramillo.IntegrationTest;
import jaya.jaramillo.domain.Contacto;
import jaya.jaramillo.repository.ContactoRepository;
import jaya.jaramillo.service.dto.ContactoDTO;
import jaya.jaramillo.service.mapper.ContactoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContactoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactoResourceIT {

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contactos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private ContactoMapper contactoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactoMockMvc;

    private Contacto contacto;

    private Contacto insertedContacto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacto createEntity() {
        return new Contacto().telefono(DEFAULT_TELEFONO).correo(DEFAULT_CORREO).codigoPais(DEFAULT_CODIGO_PAIS).celular(DEFAULT_CELULAR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contacto createUpdatedEntity() {
        return new Contacto().telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO).codigoPais(UPDATED_CODIGO_PAIS).celular(UPDATED_CELULAR);
    }

    @BeforeEach
    public void initTest() {
        contacto = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedContacto != null) {
            contactoRepository.delete(insertedContacto);
            insertedContacto = null;
        }
    }

    @Test
    @Transactional
    void createContacto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);
        var returnedContactoDTO = om.readValue(
            restContactoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContactoDTO.class
        );

        // Validate the Contacto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContacto = contactoMapper.toEntity(returnedContactoDTO);
        assertContactoUpdatableFieldsEquals(returnedContacto, getPersistedContacto(returnedContacto));

        insertedContacto = returnedContacto;
    }

    @Test
    @Transactional
    void createContactoWithExistingId() throws Exception {
        // Create the Contacto with an existing ID
        contacto.setId(1L);
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCorreoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contacto.setCorreo(null);

        // Create the Contacto, which fails.
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        restContactoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoPaisIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        contacto.setCodigoPais(null);

        // Create the Contacto, which fails.
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        restContactoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactos() throws Exception {
        // Initialize the database
        insertedContacto = contactoRepository.saveAndFlush(contacto);

        // Get all the contactoList
        restContactoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contacto.getId().intValue())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].codigoPais").value(hasItem(DEFAULT_CODIGO_PAIS)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)));
    }

    @Test
    @Transactional
    void getContacto() throws Exception {
        // Initialize the database
        insertedContacto = contactoRepository.saveAndFlush(contacto);

        // Get the contacto
        restContactoMockMvc
            .perform(get(ENTITY_API_URL_ID, contacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contacto.getId().intValue()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.codigoPais").value(DEFAULT_CODIGO_PAIS))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR));
    }

    @Test
    @Transactional
    void getNonExistingContacto() throws Exception {
        // Get the contacto
        restContactoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContacto() throws Exception {
        // Initialize the database
        insertedContacto = contactoRepository.saveAndFlush(contacto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contacto
        Contacto updatedContacto = contactoRepository.findById(contacto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContacto are not directly saved in db
        em.detach(updatedContacto);
        updatedContacto.telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO).codigoPais(UPDATED_CODIGO_PAIS).celular(UPDATED_CELULAR);
        ContactoDTO contactoDTO = contactoMapper.toDto(updatedContacto);

        restContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contactoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContactoToMatchAllProperties(updatedContacto);
    }

    @Test
    @Transactional
    void putNonExistingContacto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contacto.setId(longCount.incrementAndGet());

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contactoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContacto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contacto.setId(longCount.incrementAndGet());

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contactoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContacto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contacto.setId(longCount.incrementAndGet());

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contactoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactoWithPatch() throws Exception {
        // Initialize the database
        insertedContacto = contactoRepository.saveAndFlush(contacto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contacto using partial update
        Contacto partialUpdatedContacto = new Contacto();
        partialUpdatedContacto.setId(contacto.getId());

        partialUpdatedContacto.telefono(UPDATED_TELEFONO);

        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContacto))
            )
            .andExpect(status().isOk());

        // Validate the Contacto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContactoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedContacto, contacto), getPersistedContacto(contacto));
    }

    @Test
    @Transactional
    void fullUpdateContactoWithPatch() throws Exception {
        // Initialize the database
        insertedContacto = contactoRepository.saveAndFlush(contacto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contacto using partial update
        Contacto partialUpdatedContacto = new Contacto();
        partialUpdatedContacto.setId(contacto.getId());

        partialUpdatedContacto.telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO).codigoPais(UPDATED_CODIGO_PAIS).celular(UPDATED_CELULAR);

        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContacto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContacto))
            )
            .andExpect(status().isOk());

        // Validate the Contacto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContactoUpdatableFieldsEquals(partialUpdatedContacto, getPersistedContacto(partialUpdatedContacto));
    }

    @Test
    @Transactional
    void patchNonExistingContacto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contacto.setId(longCount.incrementAndGet());

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contactoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContacto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contacto.setId(longCount.incrementAndGet());

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contactoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContacto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contacto.setId(longCount.incrementAndGet());

        // Create the Contacto
        ContactoDTO contactoDTO = contactoMapper.toDto(contacto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contactoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contacto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContacto() throws Exception {
        // Initialize the database
        insertedContacto = contactoRepository.saveAndFlush(contacto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contacto
        restContactoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contacto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contactoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Contacto getPersistedContacto(Contacto contacto) {
        return contactoRepository.findById(contacto.getId()).orElseThrow();
    }

    protected void assertPersistedContactoToMatchAllProperties(Contacto expectedContacto) {
        assertContactoAllPropertiesEquals(expectedContacto, getPersistedContacto(expectedContacto));
    }

    protected void assertPersistedContactoToMatchUpdatableProperties(Contacto expectedContacto) {
        assertContactoAllUpdatablePropertiesEquals(expectedContacto, getPersistedContacto(expectedContacto));
    }
}
