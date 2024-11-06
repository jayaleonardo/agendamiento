package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.EspecialistaAsserts.*;
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
import jaya.jaramillo.domain.Especialista;
import jaya.jaramillo.repository.EspecialistaRepository;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.mapper.EspecialistaMapper;
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
 * Integration tests for the {@link EspecialistaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspecialistaResourceIT {

    private static final String DEFAULT_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_DOCTOR = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DOCTOR = "BBBBBBBBBB";

    private static final String DEFAULT_NRO_CONSULTORIO = "AAAAAAAAAA";
    private static final String UPDATED_NRO_CONSULTORIO = "BBBBBBBBBB";

    private static final String DEFAULT_FOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_FOTO_URL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/especialistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EspecialistaRepository especialistaRepository;

    @Autowired
    private EspecialistaMapper especialistaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspecialistaMockMvc;

    private Especialista especialista;

    private Especialista insertedEspecialista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialista createEntity() {
        return new Especialista()
            .especialidad(DEFAULT_ESPECIALIDAD)
            .codigoDoctor(DEFAULT_CODIGO_DOCTOR)
            .nroConsultorio(DEFAULT_NRO_CONSULTORIO)
            .fotoUrl(DEFAULT_FOTO_URL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especialista createUpdatedEntity() {
        return new Especialista()
            .especialidad(UPDATED_ESPECIALIDAD)
            .codigoDoctor(UPDATED_CODIGO_DOCTOR)
            .nroConsultorio(UPDATED_NRO_CONSULTORIO)
            .fotoUrl(UPDATED_FOTO_URL);
    }

    @BeforeEach
    public void initTest() {
        especialista = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEspecialista != null) {
            especialistaRepository.delete(insertedEspecialista);
            insertedEspecialista = null;
        }
    }

    @Test
    @Transactional
    void createEspecialista() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Especialista
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);
        var returnedEspecialistaDTO = om.readValue(
            restEspecialistaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialistaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EspecialistaDTO.class
        );

        // Validate the Especialista in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEspecialista = especialistaMapper.toEntity(returnedEspecialistaDTO);
        assertEspecialistaUpdatableFieldsEquals(returnedEspecialista, getPersistedEspecialista(returnedEspecialista));

        insertedEspecialista = returnedEspecialista;
    }

    @Test
    @Transactional
    void createEspecialistaWithExistingId() throws Exception {
        // Create the Especialista with an existing ID
        especialista.setId(1L);
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecialistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEspecialidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        especialista.setEspecialidad(null);

        // Create the Especialista, which fails.
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        restEspecialistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialistaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodigoDoctorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        especialista.setCodigoDoctor(null);

        // Create the Especialista, which fails.
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        restEspecialistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialistaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNroConsultorioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        especialista.setNroConsultorio(null);

        // Create the Especialista, which fails.
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        restEspecialistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialistaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEspecialistas() throws Exception {
        // Initialize the database
        insertedEspecialista = especialistaRepository.saveAndFlush(especialista);

        // Get all the especialistaList
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especialista.getId().intValue())))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD)))
            .andExpect(jsonPath("$.[*].codigoDoctor").value(hasItem(DEFAULT_CODIGO_DOCTOR)))
            .andExpect(jsonPath("$.[*].nroConsultorio").value(hasItem(DEFAULT_NRO_CONSULTORIO)))
            .andExpect(jsonPath("$.[*].fotoUrl").value(hasItem(DEFAULT_FOTO_URL)));
    }

    @Test
    @Transactional
    void getEspecialista() throws Exception {
        // Initialize the database
        insertedEspecialista = especialistaRepository.saveAndFlush(especialista);

        // Get the especialista
        restEspecialistaMockMvc
            .perform(get(ENTITY_API_URL_ID, especialista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(especialista.getId().intValue()))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD))
            .andExpect(jsonPath("$.codigoDoctor").value(DEFAULT_CODIGO_DOCTOR))
            .andExpect(jsonPath("$.nroConsultorio").value(DEFAULT_NRO_CONSULTORIO))
            .andExpect(jsonPath("$.fotoUrl").value(DEFAULT_FOTO_URL));
    }

    @Test
    @Transactional
    void getNonExistingEspecialista() throws Exception {
        // Get the especialista
        restEspecialistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEspecialista() throws Exception {
        // Initialize the database
        insertedEspecialista = especialistaRepository.saveAndFlush(especialista);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the especialista
        Especialista updatedEspecialista = especialistaRepository.findById(especialista.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEspecialista are not directly saved in db
        em.detach(updatedEspecialista);
        updatedEspecialista
            .especialidad(UPDATED_ESPECIALIDAD)
            .codigoDoctor(UPDATED_CODIGO_DOCTOR)
            .nroConsultorio(UPDATED_NRO_CONSULTORIO)
            .fotoUrl(UPDATED_FOTO_URL);
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(updatedEspecialista);

        restEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(especialistaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEspecialistaToMatchAllProperties(updatedEspecialista);
    }

    @Test
    @Transactional
    void putNonExistingEspecialista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especialistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(especialistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspecialista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(especialistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspecialista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(especialistaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspecialistaWithPatch() throws Exception {
        // Initialize the database
        insertedEspecialista = especialistaRepository.saveAndFlush(especialista);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the especialista using partial update
        Especialista partialUpdatedEspecialista = new Especialista();
        partialUpdatedEspecialista.setId(especialista.getId());

        partialUpdatedEspecialista
            .especialidad(UPDATED_ESPECIALIDAD)
            .codigoDoctor(UPDATED_CODIGO_DOCTOR)
            .nroConsultorio(UPDATED_NRO_CONSULTORIO);

        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialista.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEspecialista))
            )
            .andExpect(status().isOk());

        // Validate the Especialista in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEspecialistaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEspecialista, especialista),
            getPersistedEspecialista(especialista)
        );
    }

    @Test
    @Transactional
    void fullUpdateEspecialistaWithPatch() throws Exception {
        // Initialize the database
        insertedEspecialista = especialistaRepository.saveAndFlush(especialista);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the especialista using partial update
        Especialista partialUpdatedEspecialista = new Especialista();
        partialUpdatedEspecialista.setId(especialista.getId());

        partialUpdatedEspecialista
            .especialidad(UPDATED_ESPECIALIDAD)
            .codigoDoctor(UPDATED_CODIGO_DOCTOR)
            .nroConsultorio(UPDATED_NRO_CONSULTORIO)
            .fotoUrl(UPDATED_FOTO_URL);

        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecialista.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEspecialista))
            )
            .andExpect(status().isOk());

        // Validate the Especialista in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEspecialistaUpdatableFieldsEquals(partialUpdatedEspecialista, getPersistedEspecialista(partialUpdatedEspecialista));
    }

    @Test
    @Transactional
    void patchNonExistingEspecialista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, especialistaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(especialistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspecialista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(especialistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspecialista() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        especialista.setId(longCount.incrementAndGet());

        // Create the Especialista
        EspecialistaDTO especialistaDTO = especialistaMapper.toDto(especialista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecialistaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(especialistaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especialista in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspecialista() throws Exception {
        // Initialize the database
        insertedEspecialista = especialistaRepository.saveAndFlush(especialista);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the especialista
        restEspecialistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, especialista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return especialistaRepository.count();
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

    protected Especialista getPersistedEspecialista(Especialista especialista) {
        return especialistaRepository.findById(especialista.getId()).orElseThrow();
    }

    protected void assertPersistedEspecialistaToMatchAllProperties(Especialista expectedEspecialista) {
        assertEspecialistaAllPropertiesEquals(expectedEspecialista, getPersistedEspecialista(expectedEspecialista));
    }

    protected void assertPersistedEspecialistaToMatchUpdatableProperties(Especialista expectedEspecialista) {
        assertEspecialistaAllUpdatablePropertiesEquals(expectedEspecialista, getPersistedEspecialista(expectedEspecialista));
    }
}
