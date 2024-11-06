package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.EspecialistaTestSamples.*;
import static jaya.jaramillo.domain.HorarioConsultaTestSamples.*;
import static jaya.jaramillo.domain.ProgramacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HorarioConsultaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioConsulta.class);
        HorarioConsulta horarioConsulta1 = getHorarioConsultaSample1();
        HorarioConsulta horarioConsulta2 = new HorarioConsulta();
        assertThat(horarioConsulta1).isNotEqualTo(horarioConsulta2);

        horarioConsulta2.setId(horarioConsulta1.getId());
        assertThat(horarioConsulta1).isEqualTo(horarioConsulta2);

        horarioConsulta2 = getHorarioConsultaSample2();
        assertThat(horarioConsulta1).isNotEqualTo(horarioConsulta2);
    }

    @Test
    void programacionTest() {
        HorarioConsulta horarioConsulta = getHorarioConsultaRandomSampleGenerator();
        Programacion programacionBack = getProgramacionRandomSampleGenerator();

        horarioConsulta.addProgramacion(programacionBack);
        assertThat(horarioConsulta.getProgramacions()).containsOnly(programacionBack);
        assertThat(programacionBack.getHorarioConsulta()).isEqualTo(horarioConsulta);

        horarioConsulta.removeProgramacion(programacionBack);
        assertThat(horarioConsulta.getProgramacions()).doesNotContain(programacionBack);
        assertThat(programacionBack.getHorarioConsulta()).isNull();

        horarioConsulta.programacions(new HashSet<>(Set.of(programacionBack)));
        assertThat(horarioConsulta.getProgramacions()).containsOnly(programacionBack);
        assertThat(programacionBack.getHorarioConsulta()).isEqualTo(horarioConsulta);

        horarioConsulta.setProgramacions(new HashSet<>());
        assertThat(horarioConsulta.getProgramacions()).doesNotContain(programacionBack);
        assertThat(programacionBack.getHorarioConsulta()).isNull();
    }

    @Test
    void especialistaTest() {
        HorarioConsulta horarioConsulta = getHorarioConsultaRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        horarioConsulta.setEspecialista(especialistaBack);
        assertThat(horarioConsulta.getEspecialista()).isEqualTo(especialistaBack);

        horarioConsulta.especialista(null);
        assertThat(horarioConsulta.getEspecialista()).isNull();
    }
}
