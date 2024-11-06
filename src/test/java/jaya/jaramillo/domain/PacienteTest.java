package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.PacienteTestSamples.*;
import static jaya.jaramillo.domain.SujetoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PacienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paciente.class);
        Paciente paciente1 = getPacienteSample1();
        Paciente paciente2 = new Paciente();
        assertThat(paciente1).isNotEqualTo(paciente2);

        paciente2.setId(paciente1.getId());
        assertThat(paciente1).isEqualTo(paciente2);

        paciente2 = getPacienteSample2();
        assertThat(paciente1).isNotEqualTo(paciente2);
    }

    @Test
    void sujetoTest() {
        Paciente paciente = getPacienteRandomSampleGenerator();
        Sujeto sujetoBack = getSujetoRandomSampleGenerator();

        paciente.setSujeto(sujetoBack);
        assertThat(paciente.getSujeto()).isEqualTo(sujetoBack);

        paciente.sujeto(null);
        assertThat(paciente.getSujeto()).isNull();
    }
}
