package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.ClinicaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClinicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clinica.class);
        Clinica clinica1 = getClinicaSample1();
        Clinica clinica2 = new Clinica();
        assertThat(clinica1).isNotEqualTo(clinica2);

        clinica2.setId(clinica1.getId());
        assertThat(clinica1).isEqualTo(clinica2);

        clinica2 = getClinicaSample2();
        assertThat(clinica1).isNotEqualTo(clinica2);
    }
}
