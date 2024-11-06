package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.TipoTerapiaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoTerapiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTerapia.class);
        TipoTerapia tipoTerapia1 = getTipoTerapiaSample1();
        TipoTerapia tipoTerapia2 = new TipoTerapia();
        assertThat(tipoTerapia1).isNotEqualTo(tipoTerapia2);

        tipoTerapia2.setId(tipoTerapia1.getId());
        assertThat(tipoTerapia1).isEqualTo(tipoTerapia2);

        tipoTerapia2 = getTipoTerapiaSample2();
        assertThat(tipoTerapia1).isNotEqualTo(tipoTerapia2);
    }
}
