package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.CitaTestSamples.*;
import static jaya.jaramillo.domain.EspecialistaTestSamples.*;
import static jaya.jaramillo.domain.PacienteTestSamples.*;
import static jaya.jaramillo.domain.TipoTerapiaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cita.class);
        Cita cita1 = getCitaSample1();
        Cita cita2 = new Cita();
        assertThat(cita1).isNotEqualTo(cita2);

        cita2.setId(cita1.getId());
        assertThat(cita1).isEqualTo(cita2);

        cita2 = getCitaSample2();
        assertThat(cita1).isNotEqualTo(cita2);
    }

    @Test
    void especialistaTest() {
        Cita cita = getCitaRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        cita.setEspecialista(especialistaBack);
        assertThat(cita.getEspecialista()).isEqualTo(especialistaBack);

        cita.especialista(null);
        assertThat(cita.getEspecialista()).isNull();
    }

    @Test
    void tipoTerapiaTest() {
        Cita cita = getCitaRandomSampleGenerator();
        TipoTerapia tipoTerapiaBack = getTipoTerapiaRandomSampleGenerator();

        cita.setTipoTerapia(tipoTerapiaBack);
        assertThat(cita.getTipoTerapia()).isEqualTo(tipoTerapiaBack);

        cita.tipoTerapia(null);
        assertThat(cita.getTipoTerapia()).isNull();
    }

    @Test
    void pacienteTest() {
        Cita cita = getCitaRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        cita.setPaciente(pacienteBack);
        assertThat(cita.getPaciente()).isEqualTo(pacienteBack);

        cita.paciente(null);
        assertThat(cita.getPaciente()).isNull();
    }
}
