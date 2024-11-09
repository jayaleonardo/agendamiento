package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.HorarioConsultaAsserts.*;
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
import jaya.jaramillo.domain.HorarioConsulta;
import jaya.jaramillo.repository.HorarioConsultaRepository;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import jaya.jaramillo.service.mapper.HorarioConsultaMapper;
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
 * Integration tests for the {@link HorarioConsultaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HorarioConsultaResourceIT {

    private static final LocalDate DEFAULT_DESDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DESDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_HASTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HASTA = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DURACION_MINUTOS = 1;
    private static final Integer UPDATED_DURACION_MINUTOS = 2;

    private static final String DEFAULT_DIA_SEMANA = "AAAAAAAAAA";
    private static final String UPDATED_DIA_SEMANA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ES_HORARIO_ATENCION = false;
    private static final Boolean UPDATED_ES_HORARIO_ATENCION = true;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DESDE_HORA_ALMUERZO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DESDE_HORA_ALMUERZO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HASTA_HORA_ALMUERZO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HASTA_HORA_ALMUERZO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/horario-consultas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HorarioConsultaRepository horarioConsultaRepository;

    @Autowired
    private HorarioConsultaMapper horarioConsultaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHorarioConsultaMockMvc;

    private HorarioConsulta horarioConsulta;

    private HorarioConsulta insertedHorarioConsulta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioConsulta createEntity() {
        return new HorarioConsulta()
            .desde(DEFAULT_DESDE)
            .hasta(DEFAULT_HASTA)
            .horaInicio(DEFAULT_HORA_INICIO)
            .duracionMinutos(DEFAULT_DURACION_MINUTOS)
            .diaSemana(DEFAULT_DIA_SEMANA)
            .esHorarioAtencion(DEFAULT_ES_HORARIO_ATENCION)
            .estado(DEFAULT_ESTADO)
            .desdeHoraAlmuerzo(DEFAULT_DESDE_HORA_ALMUERZO)
            .hastaHoraAlmuerzo(DEFAULT_HASTA_HORA_ALMUERZO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HorarioConsulta createUpdatedEntity() {
        return new HorarioConsulta()
            .desde(UPDATED_DESDE)
            .hasta(UPDATED_HASTA)
            .horaInicio(UPDATED_HORA_INICIO)
            .duracionMinutos(UPDATED_DURACION_MINUTOS)
            .diaSemana(UPDATED_DIA_SEMANA)
            .esHorarioAtencion(UPDATED_ES_HORARIO_ATENCION)
            .estado(UPDATED_ESTADO)
            .desdeHoraAlmuerzo(UPDATED_DESDE_HORA_ALMUERZO)
            .hastaHoraAlmuerzo(UPDATED_HASTA_HORA_ALMUERZO);
    }

    @BeforeEach
    public void initTest() {
        horarioConsulta = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHorarioConsulta != null) {
            horarioConsultaRepository.delete(insertedHorarioConsulta);
            insertedHorarioConsulta = null;
        }
    }

    @Test
    @Transactional
    void createHorarioConsulta() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HorarioConsulta
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);
        var returnedHorarioConsultaDTO = om.readValue(
            restHorarioConsultaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horarioConsultaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HorarioConsultaDTO.class
        );

        // Validate the HorarioConsulta in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHorarioConsulta = horarioConsultaMapper.toEntity(returnedHorarioConsultaDTO);
        assertHorarioConsultaUpdatableFieldsEquals(returnedHorarioConsulta, getPersistedHorarioConsulta(returnedHorarioConsulta));

        insertedHorarioConsulta = returnedHorarioConsulta;
    }

    @Test
    @Transactional
    void createHorarioConsultaWithExistingId() throws Exception {
        // Create the HorarioConsulta with an existing ID
        horarioConsulta.setId(1L);
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHorarioConsultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horarioConsultaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDesdeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        horarioConsulta.setDesde(null);

        // Create the HorarioConsulta, which fails.
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        restHorarioConsultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horarioConsultaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHastaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        horarioConsulta.setHasta(null);

        // Create the HorarioConsulta, which fails.
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        restHorarioConsultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horarioConsultaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHoraInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        horarioConsulta.setHoraInicio(null);

        // Create the HorarioConsulta, which fails.
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        restHorarioConsultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horarioConsultaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDuracionMinutosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        horarioConsulta.setDuracionMinutos(null);

        // Create the HorarioConsulta, which fails.
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        restHorarioConsultaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horarioConsultaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHorarioConsultas() throws Exception {
        // Initialize the database
        insertedHorarioConsulta = horarioConsultaRepository.saveAndFlush(horarioConsulta);

        // Get all the horarioConsultaList
        restHorarioConsultaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(horarioConsulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].desde").value(hasItem(DEFAULT_DESDE.toString())))
            .andExpect(jsonPath("$.[*].hasta").value(hasItem(DEFAULT_HASTA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].duracionMinutos").value(hasItem(DEFAULT_DURACION_MINUTOS)))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA)))
            .andExpect(jsonPath("$.[*].esHorarioAtencion").value(hasItem(DEFAULT_ES_HORARIO_ATENCION.booleanValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].desdeHoraAlmuerzo").value(hasItem(DEFAULT_DESDE_HORA_ALMUERZO.toString())))
            .andExpect(jsonPath("$.[*].hastaHoraAlmuerzo").value(hasItem(DEFAULT_HASTA_HORA_ALMUERZO.toString())));
    }

    @Test
    @Transactional
    void getHorarioConsulta() throws Exception {
        // Initialize the database
        insertedHorarioConsulta = horarioConsultaRepository.saveAndFlush(horarioConsulta);

        // Get the horarioConsulta
        restHorarioConsultaMockMvc
            .perform(get(ENTITY_API_URL_ID, horarioConsulta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(horarioConsulta.getId().intValue()))
            .andExpect(jsonPath("$.desde").value(DEFAULT_DESDE.toString()))
            .andExpect(jsonPath("$.hasta").value(DEFAULT_HASTA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.duracionMinutos").value(DEFAULT_DURACION_MINUTOS))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA))
            .andExpect(jsonPath("$.esHorarioAtencion").value(DEFAULT_ES_HORARIO_ATENCION.booleanValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.desdeHoraAlmuerzo").value(DEFAULT_DESDE_HORA_ALMUERZO.toString()))
            .andExpect(jsonPath("$.hastaHoraAlmuerzo").value(DEFAULT_HASTA_HORA_ALMUERZO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHorarioConsulta() throws Exception {
        // Get the horarioConsulta
        restHorarioConsultaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHorarioConsulta() throws Exception {
        // Initialize the database
        insertedHorarioConsulta = horarioConsultaRepository.saveAndFlush(horarioConsulta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the horarioConsulta
        HorarioConsulta updatedHorarioConsulta = horarioConsultaRepository.findById(horarioConsulta.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHorarioConsulta are not directly saved in db
        em.detach(updatedHorarioConsulta);
        updatedHorarioConsulta
            .desde(UPDATED_DESDE)
            .hasta(UPDATED_HASTA)
            .horaInicio(UPDATED_HORA_INICIO)
            .duracionMinutos(UPDATED_DURACION_MINUTOS)
            .diaSemana(UPDATED_DIA_SEMANA)
            .esHorarioAtencion(UPDATED_ES_HORARIO_ATENCION)
            .estado(UPDATED_ESTADO)
            .desdeHoraAlmuerzo(UPDATED_DESDE_HORA_ALMUERZO)
            .hastaHoraAlmuerzo(UPDATED_HASTA_HORA_ALMUERZO);
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(updatedHorarioConsulta);

        restHorarioConsultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, horarioConsultaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(horarioConsultaDTO))
            )
            .andExpect(status().isOk());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHorarioConsultaToMatchAllProperties(updatedHorarioConsulta);
    }

    @Test
    @Transactional
    void putNonExistingHorarioConsulta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horarioConsulta.setId(longCount.incrementAndGet());

        // Create the HorarioConsulta
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioConsultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, horarioConsultaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(horarioConsultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHorarioConsulta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horarioConsulta.setId(longCount.incrementAndGet());

        // Create the HorarioConsulta
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioConsultaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(horarioConsultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHorarioConsulta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horarioConsulta.setId(longCount.incrementAndGet());

        // Create the HorarioConsulta
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioConsultaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(horarioConsultaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHorarioConsultaWithPatch() throws Exception {
        // Initialize the database
        insertedHorarioConsulta = horarioConsultaRepository.saveAndFlush(horarioConsulta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the horarioConsulta using partial update
        HorarioConsulta partialUpdatedHorarioConsulta = new HorarioConsulta();
        partialUpdatedHorarioConsulta.setId(horarioConsulta.getId());

        partialUpdatedHorarioConsulta
            .desde(UPDATED_DESDE)
            .hasta(UPDATED_HASTA)
            .esHorarioAtencion(UPDATED_ES_HORARIO_ATENCION)
            .estado(UPDATED_ESTADO);

        restHorarioConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHorarioConsulta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHorarioConsulta))
            )
            .andExpect(status().isOk());

        // Validate the HorarioConsulta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHorarioConsultaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHorarioConsulta, horarioConsulta),
            getPersistedHorarioConsulta(horarioConsulta)
        );
    }

    @Test
    @Transactional
    void fullUpdateHorarioConsultaWithPatch() throws Exception {
        // Initialize the database
        insertedHorarioConsulta = horarioConsultaRepository.saveAndFlush(horarioConsulta);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the horarioConsulta using partial update
        HorarioConsulta partialUpdatedHorarioConsulta = new HorarioConsulta();
        partialUpdatedHorarioConsulta.setId(horarioConsulta.getId());

        partialUpdatedHorarioConsulta
            .desde(UPDATED_DESDE)
            .hasta(UPDATED_HASTA)
            .horaInicio(UPDATED_HORA_INICIO)
            .duracionMinutos(UPDATED_DURACION_MINUTOS)
            .diaSemana(UPDATED_DIA_SEMANA)
            .esHorarioAtencion(UPDATED_ES_HORARIO_ATENCION)
            .estado(UPDATED_ESTADO)
            .desdeHoraAlmuerzo(UPDATED_DESDE_HORA_ALMUERZO)
            .hastaHoraAlmuerzo(UPDATED_HASTA_HORA_ALMUERZO);

        restHorarioConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHorarioConsulta.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHorarioConsulta))
            )
            .andExpect(status().isOk());

        // Validate the HorarioConsulta in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHorarioConsultaUpdatableFieldsEquals(
            partialUpdatedHorarioConsulta,
            getPersistedHorarioConsulta(partialUpdatedHorarioConsulta)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHorarioConsulta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horarioConsulta.setId(longCount.incrementAndGet());

        // Create the HorarioConsulta
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHorarioConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, horarioConsultaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(horarioConsultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHorarioConsulta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horarioConsulta.setId(longCount.incrementAndGet());

        // Create the HorarioConsulta
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioConsultaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(horarioConsultaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHorarioConsulta() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        horarioConsulta.setId(longCount.incrementAndGet());

        // Create the HorarioConsulta
        HorarioConsultaDTO horarioConsultaDTO = horarioConsultaMapper.toDto(horarioConsulta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHorarioConsultaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(horarioConsultaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HorarioConsulta in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHorarioConsulta() throws Exception {
        // Initialize the database
        insertedHorarioConsulta = horarioConsultaRepository.saveAndFlush(horarioConsulta);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the horarioConsulta
        restHorarioConsultaMockMvc
            .perform(delete(ENTITY_API_URL_ID, horarioConsulta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return horarioConsultaRepository.count();
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

    protected HorarioConsulta getPersistedHorarioConsulta(HorarioConsulta horarioConsulta) {
        return horarioConsultaRepository.findById(horarioConsulta.getId()).orElseThrow();
    }

    protected void assertPersistedHorarioConsultaToMatchAllProperties(HorarioConsulta expectedHorarioConsulta) {
        assertHorarioConsultaAllPropertiesEquals(expectedHorarioConsulta, getPersistedHorarioConsulta(expectedHorarioConsulta));
    }

    protected void assertPersistedHorarioConsultaToMatchUpdatableProperties(HorarioConsulta expectedHorarioConsulta) {
        assertHorarioConsultaAllUpdatablePropertiesEquals(expectedHorarioConsulta, getPersistedHorarioConsulta(expectedHorarioConsulta));
    }
}
