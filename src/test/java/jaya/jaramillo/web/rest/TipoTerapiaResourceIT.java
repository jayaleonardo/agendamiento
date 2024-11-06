package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.TipoTerapiaAsserts.*;
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
import jaya.jaramillo.domain.TipoTerapia;
import jaya.jaramillo.repository.TipoTerapiaRepository;
import jaya.jaramillo.service.dto.TipoTerapiaDTO;
import jaya.jaramillo.service.mapper.TipoTerapiaMapper;
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
 * Integration tests for the {@link TipoTerapiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoTerapiaResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-terapias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TipoTerapiaRepository tipoTerapiaRepository;

    @Autowired
    private TipoTerapiaMapper tipoTerapiaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoTerapiaMockMvc;

    private TipoTerapia tipoTerapia;

    private TipoTerapia insertedTipoTerapia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTerapia createEntity() {
        return new TipoTerapia().descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoTerapia createUpdatedEntity() {
        return new TipoTerapia().descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        tipoTerapia = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTipoTerapia != null) {
            tipoTerapiaRepository.delete(insertedTipoTerapia);
            insertedTipoTerapia = null;
        }
    }

    @Test
    @Transactional
    void createTipoTerapia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TipoTerapia
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);
        var returnedTipoTerapiaDTO = om.readValue(
            restTipoTerapiaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoTerapiaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TipoTerapiaDTO.class
        );

        // Validate the TipoTerapia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTipoTerapia = tipoTerapiaMapper.toEntity(returnedTipoTerapiaDTO);
        assertTipoTerapiaUpdatableFieldsEquals(returnedTipoTerapia, getPersistedTipoTerapia(returnedTipoTerapia));

        insertedTipoTerapia = returnedTipoTerapia;
    }

    @Test
    @Transactional
    void createTipoTerapiaWithExistingId() throws Exception {
        // Create the TipoTerapia with an existing ID
        tipoTerapia.setId(1L);
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoTerapiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoTerapiaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tipoTerapia.setDescripcion(null);

        // Create the TipoTerapia, which fails.
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        restTipoTerapiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoTerapiaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTipoTerapias() throws Exception {
        // Initialize the database
        insertedTipoTerapia = tipoTerapiaRepository.saveAndFlush(tipoTerapia);

        // Get all the tipoTerapiaList
        restTipoTerapiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoTerapia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getTipoTerapia() throws Exception {
        // Initialize the database
        insertedTipoTerapia = tipoTerapiaRepository.saveAndFlush(tipoTerapia);

        // Get the tipoTerapia
        restTipoTerapiaMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoTerapia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoTerapia.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingTipoTerapia() throws Exception {
        // Get the tipoTerapia
        restTipoTerapiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoTerapia() throws Exception {
        // Initialize the database
        insertedTipoTerapia = tipoTerapiaRepository.saveAndFlush(tipoTerapia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoTerapia
        TipoTerapia updatedTipoTerapia = tipoTerapiaRepository.findById(tipoTerapia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTipoTerapia are not directly saved in db
        em.detach(updatedTipoTerapia);
        updatedTipoTerapia.descripcion(UPDATED_DESCRIPCION);
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(updatedTipoTerapia);

        restTipoTerapiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoTerapiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoTerapiaDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTipoTerapiaToMatchAllProperties(updatedTipoTerapia);
    }

    @Test
    @Transactional
    void putNonExistingTipoTerapia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoTerapia.setId(longCount.incrementAndGet());

        // Create the TipoTerapia
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoTerapiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoTerapiaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoTerapiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoTerapia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoTerapia.setId(longCount.incrementAndGet());

        // Create the TipoTerapia
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTerapiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tipoTerapiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoTerapia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoTerapia.setId(longCount.incrementAndGet());

        // Create the TipoTerapia
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTerapiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tipoTerapiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoTerapiaWithPatch() throws Exception {
        // Initialize the database
        insertedTipoTerapia = tipoTerapiaRepository.saveAndFlush(tipoTerapia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoTerapia using partial update
        TipoTerapia partialUpdatedTipoTerapia = new TipoTerapia();
        partialUpdatedTipoTerapia.setId(tipoTerapia.getId());

        restTipoTerapiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoTerapia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTipoTerapia))
            )
            .andExpect(status().isOk());

        // Validate the TipoTerapia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTipoTerapiaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTipoTerapia, tipoTerapia),
            getPersistedTipoTerapia(tipoTerapia)
        );
    }

    @Test
    @Transactional
    void fullUpdateTipoTerapiaWithPatch() throws Exception {
        // Initialize the database
        insertedTipoTerapia = tipoTerapiaRepository.saveAndFlush(tipoTerapia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tipoTerapia using partial update
        TipoTerapia partialUpdatedTipoTerapia = new TipoTerapia();
        partialUpdatedTipoTerapia.setId(tipoTerapia.getId());

        partialUpdatedTipoTerapia.descripcion(UPDATED_DESCRIPCION);

        restTipoTerapiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoTerapia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTipoTerapia))
            )
            .andExpect(status().isOk());

        // Validate the TipoTerapia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTipoTerapiaUpdatableFieldsEquals(partialUpdatedTipoTerapia, getPersistedTipoTerapia(partialUpdatedTipoTerapia));
    }

    @Test
    @Transactional
    void patchNonExistingTipoTerapia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoTerapia.setId(longCount.incrementAndGet());

        // Create the TipoTerapia
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoTerapiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoTerapiaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tipoTerapiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoTerapia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoTerapia.setId(longCount.incrementAndGet());

        // Create the TipoTerapia
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTerapiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tipoTerapiaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoTerapia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tipoTerapia.setId(longCount.incrementAndGet());

        // Create the TipoTerapia
        TipoTerapiaDTO tipoTerapiaDTO = tipoTerapiaMapper.toDto(tipoTerapia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoTerapiaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tipoTerapiaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoTerapia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoTerapia() throws Exception {
        // Initialize the database
        insertedTipoTerapia = tipoTerapiaRepository.saveAndFlush(tipoTerapia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tipoTerapia
        restTipoTerapiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoTerapia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tipoTerapiaRepository.count();
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

    protected TipoTerapia getPersistedTipoTerapia(TipoTerapia tipoTerapia) {
        return tipoTerapiaRepository.findById(tipoTerapia.getId()).orElseThrow();
    }

    protected void assertPersistedTipoTerapiaToMatchAllProperties(TipoTerapia expectedTipoTerapia) {
        assertTipoTerapiaAllPropertiesEquals(expectedTipoTerapia, getPersistedTipoTerapia(expectedTipoTerapia));
    }

    protected void assertPersistedTipoTerapiaToMatchUpdatableProperties(TipoTerapia expectedTipoTerapia) {
        assertTipoTerapiaAllUpdatablePropertiesEquals(expectedTipoTerapia, getPersistedTipoTerapia(expectedTipoTerapia));
    }
}
