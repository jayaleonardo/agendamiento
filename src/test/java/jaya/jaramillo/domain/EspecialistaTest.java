package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.EspecialistaTestSamples.*;
import static jaya.jaramillo.domain.HorarioConsultaTestSamples.*;
import static jaya.jaramillo.domain.SujetoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspecialistaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Especialista.class);
        Especialista especialista1 = getEspecialistaSample1();
        Especialista especialista2 = new Especialista();
        assertThat(especialista1).isNotEqualTo(especialista2);

        especialista2.setId(especialista1.getId());
        assertThat(especialista1).isEqualTo(especialista2);

        especialista2 = getEspecialistaSample2();
        assertThat(especialista1).isNotEqualTo(especialista2);
    }

    @Test
    void sujetoTest() {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        Sujeto sujetoBack = getSujetoRandomSampleGenerator();

        especialista.setSujeto(sujetoBack);
        assertThat(especialista.getSujeto()).isEqualTo(sujetoBack);

        especialista.sujeto(null);
        assertThat(especialista.getSujeto()).isNull();
    }

    @Test
    void horarioConsultaTest() {
        Especialista especialista = getEspecialistaRandomSampleGenerator();
        HorarioConsulta horarioConsultaBack = getHorarioConsultaRandomSampleGenerator();

        especialista.addHorarioConsulta(horarioConsultaBack);
        assertThat(especialista.getHorarioConsultas()).containsOnly(horarioConsultaBack);
        assertThat(horarioConsultaBack.getEspecialista()).isEqualTo(especialista);

        especialista.removeHorarioConsulta(horarioConsultaBack);
        assertThat(especialista.getHorarioConsultas()).doesNotContain(horarioConsultaBack);
        assertThat(horarioConsultaBack.getEspecialista()).isNull();

        especialista.horarioConsultas(new HashSet<>(Set.of(horarioConsultaBack)));
        assertThat(especialista.getHorarioConsultas()).containsOnly(horarioConsultaBack);
        assertThat(horarioConsultaBack.getEspecialista()).isEqualTo(especialista);

        especialista.setHorarioConsultas(new HashSet<>());
        assertThat(especialista.getHorarioConsultas()).doesNotContain(horarioConsultaBack);
        assertThat(horarioConsultaBack.getEspecialista()).isNull();
    }
}
