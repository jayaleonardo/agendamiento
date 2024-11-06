package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.HorarioConsultaTestSamples.*;
import static jaya.jaramillo.domain.ProgramacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programacion.class);
        Programacion programacion1 = getProgramacionSample1();
        Programacion programacion2 = new Programacion();
        assertThat(programacion1).isNotEqualTo(programacion2);

        programacion2.setId(programacion1.getId());
        assertThat(programacion1).isEqualTo(programacion2);

        programacion2 = getProgramacionSample2();
        assertThat(programacion1).isNotEqualTo(programacion2);
    }

    @Test
    void horarioConsultaTest() {
        Programacion programacion = getProgramacionRandomSampleGenerator();
        HorarioConsulta horarioConsultaBack = getHorarioConsultaRandomSampleGenerator();

        programacion.setHorarioConsulta(horarioConsultaBack);
        assertThat(programacion.getHorarioConsulta()).isEqualTo(horarioConsultaBack);

        programacion.horarioConsulta(null);
        assertThat(programacion.getHorarioConsulta()).isNull();
    }
}
