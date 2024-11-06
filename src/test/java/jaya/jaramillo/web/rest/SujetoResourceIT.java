package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.SujetoAsserts.*;
import static jaya.jaramillo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import jaya.jaramillo.IntegrationTest;
import jaya.jaramillo.domain.Sujeto;
import jaya.jaramillo.repository.SujetoRepository;
import jaya.jaramillo.service.dto.SujetoDTO;
import jaya.jaramillo.service.mapper.SujetoMapper;
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
 * Integration tests for the {@link SujetoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SujetoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO_IDENTIDAD = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO_IDENTIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/sujetos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SujetoRepository sujetoRepository;

    @Autowired
    private SujetoMapper sujetoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSujetoMockMvc;

    private Sujeto sujeto;

    private Sujeto insertedSujeto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sujeto createEntity() {
        return new Sujeto()
            .nombre(DEFAULT_NOMBRE)
            .segundoNombre(DEFAULT_SEGUNDO_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .documentoIdentidad(DEFAULT_DOCUMENTO_IDENTIDAD)
            .estado(DEFAULT_ESTADO)
            .sexo(DEFAULT_SEXO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sujeto createUpdatedEntity() {
        return new Sujeto()
            .nombre(UPDATED_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .documentoIdentidad(UPDATED_DOCUMENTO_IDENTIDAD)
            .estado(UPDATED_ESTADO)
            .sexo(UPDATED_SEXO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
    }

    @BeforeEach
    public void initTest() {
        sujeto = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSujeto != null) {
            sujetoRepository.delete(insertedSujeto);
            insertedSujeto = null;
        }
    }

    @Test
    @Transactional
    void createSujeto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sujeto
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);
        var returnedSujetoDTO = om.readValue(
            restSujetoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SujetoDTO.class
        );

        // Validate the Sujeto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSujeto = sujetoMapper.toEntity(returnedSujetoDTO);
        assertSujetoUpdatableFieldsEquals(returnedSujeto, getPersistedSujeto(returnedSujeto));

        insertedSujeto = returnedSujeto;
    }

    @Test
    @Transactional
    void createSujetoWithExistingId() throws Exception {
        // Create the Sujeto with an existing ID
        sujeto.setId(1L);
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSujetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sujeto.setNombre(null);

        // Create the Sujeto, which fails.
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        restSujetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sujeto.setApellido(null);

        // Create the Sujeto, which fails.
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        restSujetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDocumentoIdentidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sujeto.setDocumentoIdentidad(null);

        // Create the Sujeto, which fails.
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        restSujetoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSujetos() throws Exception {
        // Initialize the database
        insertedSujeto = sujetoRepository.saveAndFlush(sujeto);

        // Get all the sujetoList
        restSujetoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sujeto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].segundoNombre").value(hasItem(DEFAULT_SEGUNDO_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].documentoIdentidad").value(hasItem(DEFAULT_DOCUMENTO_IDENTIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())));
    }

    @Test
    @Transactional
    void getSujeto() throws Exception {
        // Initialize the database
        insertedSujeto = sujetoRepository.saveAndFlush(sujeto);

        // Get the sujeto
        restSujetoMockMvc
            .perform(get(ENTITY_API_URL_ID, sujeto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sujeto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.segundoNombre").value(DEFAULT_SEGUNDO_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.documentoIdentidad").value(DEFAULT_DOCUMENTO_IDENTIDAD))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSujeto() throws Exception {
        // Get the sujeto
        restSujetoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSujeto() throws Exception {
        // Initialize the database
        insertedSujeto = sujetoRepository.saveAndFlush(sujeto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sujeto
        Sujeto updatedSujeto = sujetoRepository.findById(sujeto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSujeto are not directly saved in db
        em.detach(updatedSujeto);
        updatedSujeto
            .nombre(UPDATED_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .documentoIdentidad(UPDATED_DOCUMENTO_IDENTIDAD)
            .estado(UPDATED_ESTADO)
            .sexo(UPDATED_SEXO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        SujetoDTO sujetoDTO = sujetoMapper.toDto(updatedSujeto);

        restSujetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sujetoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSujetoToMatchAllProperties(updatedSujeto);
    }

    @Test
    @Transactional
    void putNonExistingSujeto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sujeto.setId(longCount.incrementAndGet());

        // Create the Sujeto
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSujetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sujetoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSujeto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sujeto.setId(longCount.incrementAndGet());

        // Create the Sujeto
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSujetoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sujetoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSujeto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sujeto.setId(longCount.incrementAndGet());

        // Create the Sujeto
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSujetoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sujetoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSujetoWithPatch() throws Exception {
        // Initialize the database
        insertedSujeto = sujetoRepository.saveAndFlush(sujeto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sujeto using partial update
        Sujeto partialUpdatedSujeto = new Sujeto();
        partialUpdatedSujeto.setId(sujeto.getId());

        partialUpdatedSujeto.documentoIdentidad(UPDATED_DOCUMENTO_IDENTIDAD).sexo(UPDATED_SEXO);

        restSujetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSujeto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSujeto))
            )
            .andExpect(status().isOk());

        // Validate the Sujeto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSujetoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSujeto, sujeto), getPersistedSujeto(sujeto));
    }

    @Test
    @Transactional
    void fullUpdateSujetoWithPatch() throws Exception {
        // Initialize the database
        insertedSujeto = sujetoRepository.saveAndFlush(sujeto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sujeto using partial update
        Sujeto partialUpdatedSujeto = new Sujeto();
        partialUpdatedSujeto.setId(sujeto.getId());

        partialUpdatedSujeto
            .nombre(UPDATED_NOMBRE)
            .segundoNombre(UPDATED_SEGUNDO_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .documentoIdentidad(UPDATED_DOCUMENTO_IDENTIDAD)
            .estado(UPDATED_ESTADO)
            .sexo(UPDATED_SEXO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);

        restSujetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSujeto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSujeto))
            )
            .andExpect(status().isOk());

        // Validate the Sujeto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSujetoUpdatableFieldsEquals(partialUpdatedSujeto, getPersistedSujeto(partialUpdatedSujeto));
    }

    @Test
    @Transactional
    void patchNonExistingSujeto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sujeto.setId(longCount.incrementAndGet());

        // Create the Sujeto
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSujetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sujetoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sujetoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSujeto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sujeto.setId(longCount.incrementAndGet());

        // Create the Sujeto
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSujetoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sujetoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSujeto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sujeto.setId(longCount.incrementAndGet());

        // Create the Sujeto
        SujetoDTO sujetoDTO = sujetoMapper.toDto(sujeto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSujetoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sujetoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sujeto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSujeto() throws Exception {
        // Initialize the database
        insertedSujeto = sujetoRepository.saveAndFlush(sujeto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sujeto
        restSujetoMockMvc
            .perform(delete(ENTITY_API_URL_ID, sujeto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sujetoRepository.count();
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

    protected Sujeto getPersistedSujeto(Sujeto sujeto) {
        return sujetoRepository.findById(sujeto.getId()).orElseThrow();
    }

    protected void assertPersistedSujetoToMatchAllProperties(Sujeto expectedSujeto) {
        assertSujetoAllPropertiesEquals(expectedSujeto, getPersistedSujeto(expectedSujeto));
    }

    protected void assertPersistedSujetoToMatchUpdatableProperties(Sujeto expectedSujeto) {
        assertSujetoAllUpdatablePropertiesEquals(expectedSujeto, getPersistedSujeto(expectedSujeto));
    }
}
