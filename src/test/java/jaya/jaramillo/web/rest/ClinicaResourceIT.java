package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.ClinicaAsserts.*;
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
import jaya.jaramillo.domain.Clinica;
import jaya.jaramillo.repository.ClinicaRepository;
import jaya.jaramillo.service.dto.ClinicaDTO;
import jaya.jaramillo.service.mapper.ClinicaMapper;
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
 * Integration tests for the {@link ClinicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClinicaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_HORARIO_ATENCION = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO_ATENCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clinicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private ClinicaMapper clinicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClinicaMockMvc;

    private Clinica clinica;

    private Clinica insertedClinica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinica createEntity() {
        return new Clinica()
            .nombre(DEFAULT_NOMBRE)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .horarioAtencion(DEFAULT_HORARIO_ATENCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinica createUpdatedEntity() {
        return new Clinica()
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .horarioAtencion(UPDATED_HORARIO_ATENCION);
    }

    @BeforeEach
    public void initTest() {
        clinica = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedClinica != null) {
            clinicaRepository.delete(insertedClinica);
            insertedClinica = null;
        }
    }

    @Test
    @Transactional
    void createClinica() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);
        var returnedClinicaDTO = om.readValue(
            restClinicaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ClinicaDTO.class
        );

        // Validate the Clinica in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedClinica = clinicaMapper.toEntity(returnedClinicaDTO);
        assertClinicaUpdatableFieldsEquals(returnedClinica, getPersistedClinica(returnedClinica));

        insertedClinica = returnedClinica;
    }

    @Test
    @Transactional
    void createClinicaWithExistingId() throws Exception {
        // Create the Clinica with an existing ID
        clinica.setId(1L);
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        clinica.setNombre(null);

        // Create the Clinica, which fails.
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        restClinicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        clinica.setTelefono(null);

        // Create the Clinica, which fails.
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        restClinicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCorreoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        clinica.setCorreo(null);

        // Create the Clinica, which fails.
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        restClinicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHorarioAtencionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        clinica.setHorarioAtencion(null);

        // Create the Clinica, which fails.
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        restClinicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClinicas() throws Exception {
        // Initialize the database
        insertedClinica = clinicaRepository.saveAndFlush(clinica);

        // Get all the clinicaList
        restClinicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].horarioAtencion").value(hasItem(DEFAULT_HORARIO_ATENCION)));
    }

    @Test
    @Transactional
    void getClinica() throws Exception {
        // Initialize the database
        insertedClinica = clinicaRepository.saveAndFlush(clinica);

        // Get the clinica
        restClinicaMockMvc
            .perform(get(ENTITY_API_URL_ID, clinica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clinica.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.horarioAtencion").value(DEFAULT_HORARIO_ATENCION));
    }

    @Test
    @Transactional
    void getNonExistingClinica() throws Exception {
        // Get the clinica
        restClinicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClinica() throws Exception {
        // Initialize the database
        insertedClinica = clinicaRepository.saveAndFlush(clinica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the clinica
        Clinica updatedClinica = clinicaRepository.findById(clinica.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClinica are not directly saved in db
        em.detach(updatedClinica);
        updatedClinica.nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO).correo(UPDATED_CORREO).horarioAtencion(UPDATED_HORARIO_ATENCION);
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(updatedClinica);

        restClinicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clinicaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClinicaToMatchAllProperties(updatedClinica);
    }

    @Test
    @Transactional
    void putNonExistingClinica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clinica.setId(longCount.incrementAndGet());

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clinicaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClinica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clinica.setId(longCount.incrementAndGet());

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(clinicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClinica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clinica.setId(longCount.incrementAndGet());

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clinicaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClinicaWithPatch() throws Exception {
        // Initialize the database
        insertedClinica = clinicaRepository.saveAndFlush(clinica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the clinica using partial update
        Clinica partialUpdatedClinica = new Clinica();
        partialUpdatedClinica.setId(clinica.getId());

        partialUpdatedClinica.nombre(UPDATED_NOMBRE).telefono(UPDATED_TELEFONO);

        restClinicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClinica.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClinica))
            )
            .andExpect(status().isOk());

        // Validate the Clinica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClinicaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClinica, clinica), getPersistedClinica(clinica));
    }

    @Test
    @Transactional
    void fullUpdateClinicaWithPatch() throws Exception {
        // Initialize the database
        insertedClinica = clinicaRepository.saveAndFlush(clinica);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the clinica using partial update
        Clinica partialUpdatedClinica = new Clinica();
        partialUpdatedClinica.setId(clinica.getId());

        partialUpdatedClinica
            .nombre(UPDATED_NOMBRE)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .horarioAtencion(UPDATED_HORARIO_ATENCION);

        restClinicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClinica.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClinica))
            )
            .andExpect(status().isOk());

        // Validate the Clinica in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClinicaUpdatableFieldsEquals(partialUpdatedClinica, getPersistedClinica(partialUpdatedClinica));
    }

    @Test
    @Transactional
    void patchNonExistingClinica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clinica.setId(longCount.incrementAndGet());

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clinicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(clinicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClinica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clinica.setId(longCount.incrementAndGet());

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(clinicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClinica() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        clinica.setId(longCount.incrementAndGet());

        // Create the Clinica
        ClinicaDTO clinicaDTO = clinicaMapper.toDto(clinica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(clinicaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clinica in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClinica() throws Exception {
        // Initialize the database
        insertedClinica = clinicaRepository.saveAndFlush(clinica);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the clinica
        restClinicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, clinica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return clinicaRepository.count();
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

    protected Clinica getPersistedClinica(Clinica clinica) {
        return clinicaRepository.findById(clinica.getId()).orElseThrow();
    }

    protected void assertPersistedClinicaToMatchAllProperties(Clinica expectedClinica) {
        assertClinicaAllPropertiesEquals(expectedClinica, getPersistedClinica(expectedClinica));
    }

    protected void assertPersistedClinicaToMatchUpdatableProperties(Clinica expectedClinica) {
        assertClinicaAllUpdatablePropertiesEquals(expectedClinica, getPersistedClinica(expectedClinica));
    }
}
