package jaya.jaramillo.web.rest;

import static jaya.jaramillo.domain.CitaAsserts.*;
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
import jaya.jaramillo.domain.Cita;
import jaya.jaramillo.repository.CitaRepository;
import jaya.jaramillo.service.dto.CitaDTO;
import jaya.jaramillo.service.mapper.CitaMapper;
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
 * Integration tests for the {@link CitaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitaResourceIT {

    private static final LocalDate DEFAULT_FECHA_CITA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CITA = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_HORA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HORA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DURACION_MINUTOS = 1;
    private static final Integer UPDATED_DURACION_MINUTOS = 2;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_VISITA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_VISITA = "BBBBBBBBBB";

    private static final String DEFAULT_CANAL_ATENCION = "AAAAAAAAAA";
    private static final String UPDATED_CANAL_ATENCION = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final String DEFAULT_RECORDATORIO = "AAAAAAAAAA";
    private static final String UPDATED_RECORDATORIO = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVO_CONSULTA = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_CONSULTA = "BBBBBBBBBB";

    private static final String DEFAULT_DETALLE_CONSULTA_VIRTUAL = "AAAAAAAAAA";
    private static final String UPDATED_DETALLE_CONSULTA_VIRTUAL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VIRTUAL = false;
    private static final Boolean UPDATED_VIRTUAL = true;

    private static final String DEFAULT_INFORMACION_RESERVA = "AAAAAAAAAA";
    private static final String UPDATED_INFORMACION_RESERVA = "BBBBBBBBBB";

    private static final String DEFAULT_TAREA = "AAAAAAAAAA";
    private static final String UPDATED_TAREA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/citas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitaMockMvc;

    private Cita cita;

    private Cita insertedCita;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createEntity() {
        return new Cita()
            .fechaCita(DEFAULT_FECHA_CITA)
            .horaInicio(DEFAULT_HORA_INICIO)
            .duracionMinutos(DEFAULT_DURACION_MINUTOS)
            .estado(DEFAULT_ESTADO)
            .tipoVisita(DEFAULT_TIPO_VISITA)
            .canalAtencion(DEFAULT_CANAL_ATENCION)
            .observacion(DEFAULT_OBSERVACION)
            .recordatorio(DEFAULT_RECORDATORIO)
            .motivoConsulta(DEFAULT_MOTIVO_CONSULTA)
            .detalleConsultaVirtual(DEFAULT_DETALLE_CONSULTA_VIRTUAL)
            .virtual(DEFAULT_VIRTUAL)
            .informacionReserva(DEFAULT_INFORMACION_RESERVA)
            .tarea(DEFAULT_TAREA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createUpdatedEntity() {
        return new Cita()
            .fechaCita(UPDATED_FECHA_CITA)
            .horaInicio(UPDATED_HORA_INICIO)
            .duracionMinutos(UPDATED_DURACION_MINUTOS)
            .estado(UPDATED_ESTADO)
            .tipoVisita(UPDATED_TIPO_VISITA)
            .canalAtencion(UPDATED_CANAL_ATENCION)
            .observacion(UPDATED_OBSERVACION)
            .recordatorio(UPDATED_RECORDATORIO)
            .motivoConsulta(UPDATED_MOTIVO_CONSULTA)
            .detalleConsultaVirtual(UPDATED_DETALLE_CONSULTA_VIRTUAL)
            .virtual(UPDATED_VIRTUAL)
            .informacionReserva(UPDATED_INFORMACION_RESERVA)
            .tarea(UPDATED_TAREA);
    }

    @BeforeEach
    public void initTest() {
        cita = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCita != null) {
            citaRepository.delete(insertedCita);
            insertedCita = null;
        }
    }

    @Test
    @Transactional
    void createCita() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);
        var returnedCitaDTO = om.readValue(
            restCitaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CitaDTO.class
        );

        // Validate the Cita in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCita = citaMapper.toEntity(returnedCitaDTO);
        assertCitaUpdatableFieldsEquals(returnedCita, getPersistedCita(returnedCita));

        insertedCita = returnedCita;
    }

    @Test
    @Transactional
    void createCitaWithExistingId() throws Exception {
        // Create the Cita with an existing ID
        cita.setId(1L);
        CitaDTO citaDTO = citaMapper.toDto(cita);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaCitaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cita.setFechaCita(null);

        // Create the Cita, which fails.
        CitaDTO citaDTO = citaMapper.toDto(cita);

        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHoraInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cita.setHoraInicio(null);

        // Create the Cita, which fails.
        CitaDTO citaDTO = citaMapper.toDto(cita);

        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDuracionMinutosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cita.setDuracionMinutos(null);

        // Create the Cita, which fails.
        CitaDTO citaDTO = citaMapper.toDto(cita);

        restCitaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCitas() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        // Get all the citaList
        restCitaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cita.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCita").value(hasItem(DEFAULT_FECHA_CITA.toString())))
            .andExpect(jsonPath("$.[*].horaInicio").value(hasItem(DEFAULT_HORA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].duracionMinutos").value(hasItem(DEFAULT_DURACION_MINUTOS)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].tipoVisita").value(hasItem(DEFAULT_TIPO_VISITA)))
            .andExpect(jsonPath("$.[*].canalAtencion").value(hasItem(DEFAULT_CANAL_ATENCION)))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION)))
            .andExpect(jsonPath("$.[*].recordatorio").value(hasItem(DEFAULT_RECORDATORIO)))
            .andExpect(jsonPath("$.[*].motivoConsulta").value(hasItem(DEFAULT_MOTIVO_CONSULTA)))
            .andExpect(jsonPath("$.[*].detalleConsultaVirtual").value(hasItem(DEFAULT_DETALLE_CONSULTA_VIRTUAL)))
            .andExpect(jsonPath("$.[*].virtual").value(hasItem(DEFAULT_VIRTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].informacionReserva").value(hasItem(DEFAULT_INFORMACION_RESERVA)))
            .andExpect(jsonPath("$.[*].tarea").value(hasItem(DEFAULT_TAREA)));
    }

    @Test
    @Transactional
    void getCita() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        // Get the cita
        restCitaMockMvc
            .perform(get(ENTITY_API_URL_ID, cita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cita.getId().intValue()))
            .andExpect(jsonPath("$.fechaCita").value(DEFAULT_FECHA_CITA.toString()))
            .andExpect(jsonPath("$.horaInicio").value(DEFAULT_HORA_INICIO.toString()))
            .andExpect(jsonPath("$.duracionMinutos").value(DEFAULT_DURACION_MINUTOS))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.tipoVisita").value(DEFAULT_TIPO_VISITA))
            .andExpect(jsonPath("$.canalAtencion").value(DEFAULT_CANAL_ATENCION))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION))
            .andExpect(jsonPath("$.recordatorio").value(DEFAULT_RECORDATORIO))
            .andExpect(jsonPath("$.motivoConsulta").value(DEFAULT_MOTIVO_CONSULTA))
            .andExpect(jsonPath("$.detalleConsultaVirtual").value(DEFAULT_DETALLE_CONSULTA_VIRTUAL))
            .andExpect(jsonPath("$.virtual").value(DEFAULT_VIRTUAL.booleanValue()))
            .andExpect(jsonPath("$.informacionReserva").value(DEFAULT_INFORMACION_RESERVA))
            .andExpect(jsonPath("$.tarea").value(DEFAULT_TAREA));
    }

    @Test
    @Transactional
    void getNonExistingCita() throws Exception {
        // Get the cita
        restCitaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCita() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cita
        Cita updatedCita = citaRepository.findById(cita.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCita are not directly saved in db
        em.detach(updatedCita);
        updatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .horaInicio(UPDATED_HORA_INICIO)
            .duracionMinutos(UPDATED_DURACION_MINUTOS)
            .estado(UPDATED_ESTADO)
            .tipoVisita(UPDATED_TIPO_VISITA)
            .canalAtencion(UPDATED_CANAL_ATENCION)
            .observacion(UPDATED_OBSERVACION)
            .recordatorio(UPDATED_RECORDATORIO)
            .motivoConsulta(UPDATED_MOTIVO_CONSULTA)
            .detalleConsultaVirtual(UPDATED_DETALLE_CONSULTA_VIRTUAL)
            .virtual(UPDATED_VIRTUAL)
            .informacionReserva(UPDATED_INFORMACION_RESERVA)
            .tarea(UPDATED_TAREA);
        CitaDTO citaDTO = citaMapper.toDto(updatedCita);

        restCitaMockMvc
            .perform(put(ENTITY_API_URL_ID, citaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isOk());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCitaToMatchAllProperties(updatedCita);
    }

    @Test
    @Transactional
    void putNonExistingCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(put(ENTITY_API_URL_ID, citaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .horaInicio(UPDATED_HORA_INICIO)
            .duracionMinutos(UPDATED_DURACION_MINUTOS)
            .tipoVisita(UPDATED_TIPO_VISITA)
            .observacion(UPDATED_OBSERVACION)
            .recordatorio(UPDATED_RECORDATORIO)
            .motivoConsulta(UPDATED_MOTIVO_CONSULTA)
            .detalleConsultaVirtual(UPDATED_DETALLE_CONSULTA_VIRTUAL)
            .informacionReserva(UPDATED_INFORMACION_RESERVA)
            .tarea(UPDATED_TAREA);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCitaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCita, cita), getPersistedCita(cita));
    }

    @Test
    @Transactional
    void fullUpdateCitaWithPatch() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cita using partial update
        Cita partialUpdatedCita = new Cita();
        partialUpdatedCita.setId(cita.getId());

        partialUpdatedCita
            .fechaCita(UPDATED_FECHA_CITA)
            .horaInicio(UPDATED_HORA_INICIO)
            .duracionMinutos(UPDATED_DURACION_MINUTOS)
            .estado(UPDATED_ESTADO)
            .tipoVisita(UPDATED_TIPO_VISITA)
            .canalAtencion(UPDATED_CANAL_ATENCION)
            .observacion(UPDATED_OBSERVACION)
            .recordatorio(UPDATED_RECORDATORIO)
            .motivoConsulta(UPDATED_MOTIVO_CONSULTA)
            .detalleConsultaVirtual(UPDATED_DETALLE_CONSULTA_VIRTUAL)
            .virtual(UPDATED_VIRTUAL)
            .informacionReserva(UPDATED_INFORMACION_RESERVA)
            .tarea(UPDATED_TAREA);

        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCita.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCita))
            )
            .andExpect(status().isOk());

        // Validate the Cita in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCitaUpdatableFieldsEquals(partialUpdatedCita, getPersistedCita(partialUpdatedCita));
    }

    @Test
    @Transactional
    void patchNonExistingCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citaDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(citaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCita() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cita.setId(longCount.incrementAndGet());

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(citaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cita in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCita() throws Exception {
        // Initialize the database
        insertedCita = citaRepository.saveAndFlush(cita);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cita
        restCitaMockMvc
            .perform(delete(ENTITY_API_URL_ID, cita.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return citaRepository.count();
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

    protected Cita getPersistedCita(Cita cita) {
        return citaRepository.findById(cita.getId()).orElseThrow();
    }

    protected void assertPersistedCitaToMatchAllProperties(Cita expectedCita) {
        assertCitaAllPropertiesEquals(expectedCita, getPersistedCita(expectedCita));
    }

    protected void assertPersistedCitaToMatchUpdatableProperties(Cita expectedCita) {
        assertCitaAllUpdatablePropertiesEquals(expectedCita, getPersistedCita(expectedCita));
    }
}
