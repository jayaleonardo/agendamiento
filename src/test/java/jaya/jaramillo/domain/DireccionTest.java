package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.DireccionTestSamples.*;
import static jaya.jaramillo.domain.SujetoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DireccionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Direccion.class);
        Direccion direccion1 = getDireccionSample1();
        Direccion direccion2 = new Direccion();
        assertThat(direccion1).isNotEqualTo(direccion2);

        direccion2.setId(direccion1.getId());
        assertThat(direccion1).isEqualTo(direccion2);

        direccion2 = getDireccionSample2();
        assertThat(direccion1).isNotEqualTo(direccion2);
    }

    @Test
    void sujetoTest() {
        Direccion direccion = getDireccionRandomSampleGenerator();
        Sujeto sujetoBack = getSujetoRandomSampleGenerator();

        direccion.setSujeto(sujetoBack);
        assertThat(direccion.getSujeto()).isEqualTo(sujetoBack);

        direccion.sujeto(null);
        assertThat(direccion.getSujeto()).isNull();
    }
}
