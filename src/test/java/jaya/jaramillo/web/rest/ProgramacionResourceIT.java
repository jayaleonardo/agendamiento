package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.ProgramacionAsserts.*;
import static jaya.jaramillo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import jaya.jaramillo.IntegrationTest;
import jaya.jaramillo.domain.Programacion;
import jaya.jaramillo.repository.ProgramacionRepository;
import jaya.jaramillo.service.dto.ProgramacionDTO;
import jaya.jaramillo.service.mapper.ProgramacionMapper;
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
 * Integration tests for the {@link ProgramacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProgramacionResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_DESDE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DESDE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HASTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HASTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/programacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProgramacionRepository programacionRepository;

    @Autowired
    private ProgramacionMapper programacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramacionMockMvc;

    private Programacion programacion;

    private Programacion insertedProgramacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programacion createEntity() {
        return new Programacion().fecha(DEFAULT_FECHA).desde(DEFAULT_DESDE).hasta(DEFAULT_HASTA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programacion createUpdatedEntity() {
        return new Programacion().fecha(UPDATED_FECHA).desde(UPDATED_DESDE).hasta(UPDATED_HASTA);
    }

    @BeforeEach
    public void initTest() {
        programacion = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProgramacion != null) {
            programacionRepository.delete(insertedProgramacion);
            insertedProgramacion = null;
        }
    }

    @Test
    @Transactional
    void createProgramacion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Programacion
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);
        var returnedProgramacionDTO = om.readValue(
            restProgramacionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programacionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ProgramacionDTO.class
        );

        // Validate the Programacion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProgramacion = programacionMapper.toEntity(returnedProgramacionDTO);
        assertProgramacionUpdatableFieldsEquals(returnedProgramacion, getPersistedProgramacion(returnedProgramacion));

        insertedProgramacion = returnedProgramacion;
    }

    @Test
    @Transactional
    void createProgramacionWithExistingId() throws Exception {
        // Create the Programacion with an existing ID
        programacion.setId(1L);
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programacion.setFecha(null);

        // Create the Programacion, which fails.
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        restProgramacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDesdeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programacion.setDesde(null);

        // Create the Programacion, which fails.
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        restProgramacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHastaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        programacion.setHasta(null);

        // Create the Programacion, which fails.
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        restProgramacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProgramacions() throws Exception {
        // Initialize the database
        insertedProgramacion = programacionRepository.saveAndFlush(programacion);

        // Get all the programacionList
        restProgramacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].desde").value(hasItem(DEFAULT_DESDE.toString())))
            .andExpect(jsonPath("$.[*].hasta").value(hasItem(DEFAULT_HASTA.toString())));
    }

    @Test
    @Transactional
    void getProgramacion() throws Exception {
        // Initialize the database
        insertedProgramacion = programacionRepository.saveAndFlush(programacion);

        // Get the programacion
        restProgramacionMockMvc
            .perform(get(ENTITY_API_URL_ID, programacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.desde").value(DEFAULT_DESDE.toString()))
            .andExpect(jsonPath("$.hasta").value(DEFAULT_HASTA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProgramacion() throws Exception {
        // Get the programacion
        restProgramacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProgramacion() throws Exception {
        // Initialize the database
        insertedProgramacion = programacionRepository.saveAndFlush(programacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programacion
        Programacion updatedProgramacion = programacionRepository.findById(programacion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProgramacion are not directly saved in db
        em.detach(updatedProgramacion);
        updatedProgramacion.fecha(UPDATED_FECHA).desde(UPDATED_DESDE).hasta(UPDATED_HASTA);
        ProgramacionDTO programacionDTO = programacionMapper.toDto(updatedProgramacion);

        restProgramacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProgramacionToMatchAllProperties(updatedProgramacion);
    }

    @Test
    @Transactional
    void putNonExistingProgramacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programacion.setId(longCount.incrementAndGet());

        // Create the Programacion
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, programacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProgramacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programacion.setId(longCount.incrementAndGet());

        // Create the Programacion
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(programacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProgramacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programacion.setId(longCount.incrementAndGet());

        // Create the Programacion
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(programacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProgramacionWithPatch() throws Exception {
        // Initialize the database
        insertedProgramacion = programacionRepository.saveAndFlush(programacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programacion using partial update
        Programacion partialUpdatedProgramacion = new Programacion();
        partialUpdatedProgramacion.setId(programacion.getId());

        partialUpdatedProgramacion.fecha(UPDATED_FECHA).desde(UPDATED_DESDE).hasta(UPDATED_HASTA);

        restProgramacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramacion))
            )
            .andExpect(status().isOk());

        // Validate the Programacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramacionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProgramacion, programacion),
            getPersistedProgramacion(programacion)
        );
    }

    @Test
    @Transactional
    void fullUpdateProgramacionWithPatch() throws Exception {
        // Initialize the database
        insertedProgramacion = programacionRepository.saveAndFlush(programacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the programacion using partial update
        Programacion partialUpdatedProgramacion = new Programacion();
        partialUpdatedProgramacion.setId(programacion.getId());

        partialUpdatedProgramacion.fecha(UPDATED_FECHA).desde(UPDATED_DESDE).hasta(UPDATED_HASTA);

        restProgramacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProgramacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProgramacion))
            )
            .andExpect(status().isOk());

        // Validate the Programacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProgramacionUpdatableFieldsEquals(partialUpdatedProgramacion, getPersistedProgramacion(partialUpdatedProgramacion));
    }

    @Test
    @Transactional
    void patchNonExistingProgramacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programacion.setId(longCount.incrementAndGet());

        // Create the Programacion
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, programacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProgramacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programacion.setId(longCount.incrementAndGet());

        // Create the Programacion
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(programacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProgramacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        programacion.setId(longCount.incrementAndGet());

        // Create the Programacion
        ProgramacionDTO programacionDTO = programacionMapper.toDto(programacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProgramacionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(programacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Programacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProgramacion() throws Exception {
        // Initialize the database
        insertedProgramacion = programacionRepository.saveAndFlush(programacion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the programacion
        restProgramacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, programacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return programacionRepository.count();
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

    protected Programacion getPersistedProgramacion(Programacion programacion) {
        return programacionRepository.findById(programacion.getId()).orElseThrow();
    }

    protected void assertPersistedProgramacionToMatchAllProperties(Programacion expectedProgramacion) {
        assertProgramacionAllPropertiesEquals(expectedProgramacion, getPersistedProgramacion(expectedProgramacion));
    }

    protected void assertPersistedProgramacionToMatchUpdatableProperties(Programacion expectedProgramacion) {
        assertProgramacionAllUpdatablePropertiesEquals(expectedProgramacion, getPersistedProgramacion(expectedProgramacion));
    }
}
