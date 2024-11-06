package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.ContactoTestSamples.*;
import static jaya.jaramillo.domain.SujetoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contacto.class);
        Contacto contacto1 = getContactoSample1();
        Contacto contacto2 = new Contacto();
        assertThat(contacto1).isNotEqualTo(contacto2);

        contacto2.setId(contacto1.getId());
        assertThat(contacto1).isEqualTo(contacto2);

        contacto2 = getContactoSample2();
        assertThat(contacto1).isNotEqualTo(contacto2);
    }

    @Test
    void sujetoTest() {
        Contacto contacto = getContactoRandomSampleGenerator();
        Sujeto sujetoBack = getSujetoRandomSampleGenerator();

        contacto.setSujeto(sujetoBack);
        assertThat(contacto.getSujeto()).isEqualTo(sujetoBack);

        contacto.sujeto(null);
        assertThat(contacto.getSujeto()).isNull();
    }
}
