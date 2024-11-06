package jaya.jaramillo.domain;

import static jaya.jaramillo.domain.ContactoTestSamples.*;
import static jaya.jaramillo.domain.DireccionTestSamples.*;
import static jaya.jaramillo.domain.EspecialistaTestSamples.*;
import static jaya.jaramillo.domain.PacienteTestSamples.*;
import static jaya.jaramillo.domain.SujetoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SujetoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sujeto.class);
        Sujeto sujeto1 = getSujetoSample1();
        Sujeto sujeto2 = new Sujeto();
        assertThat(sujeto1).isNotEqualTo(sujeto2);

        sujeto2.setId(sujeto1.getId());
        assertThat(sujeto1).isEqualTo(sujeto2);

        sujeto2 = getSujetoSample2();
        assertThat(sujeto1).isNotEqualTo(sujeto2);
    }

    @Test
    void contactoTest() {
        Sujeto sujeto = getSujetoRandomSampleGenerator();
        Contacto contactoBack = getContactoRandomSampleGenerator();

        sujeto.setContacto(contactoBack);
        assertThat(sujeto.getContacto()).isEqualTo(contactoBack);
        assertThat(contactoBack.getSujeto()).isEqualTo(sujeto);

        sujeto.contacto(null);
        assertThat(sujeto.getContacto()).isNull();
        assertThat(contactoBack.getSujeto()).isNull();
    }

    @Test
    void direccionTest() {
        Sujeto sujeto = getSujetoRandomSampleGenerator();
        Direccion direccionBack = getDireccionRandomSampleGenerator();

        sujeto.setDireccion(direccionBack);
        assertThat(sujeto.getDireccion()).isEqualTo(direccionBack);
        assertThat(direccionBack.getSujeto()).isEqualTo(sujeto);

        sujeto.direccion(null);
        assertThat(sujeto.getDireccion()).isNull();
        assertThat(direccionBack.getSujeto()).isNull();
    }

    @Test
    void especialistaTest() {
        Sujeto sujeto = getSujetoRandomSampleGenerator();
        Especialista especialistaBack = getEspecialistaRandomSampleGenerator();

        sujeto.setEspecialista(especialistaBack);
        assertThat(sujeto.getEspecialista()).isEqualTo(especialistaBack);
        assertThat(especialistaBack.getSujeto()).isEqualTo(sujeto);

        sujeto.especialista(null);
        assertThat(sujeto.getEspecialista()).isNull();
        assertThat(especialistaBack.getSujeto()).isNull();
    }

    @Test
    void pacienteTest() {
        Sujeto sujeto = getSujetoRandomSampleGenerator();
        Paciente pacienteBack = getPacienteRandomSampleGenerator();

        sujeto.setPaciente(pacienteBack);
        assertThat(sujeto.getPaciente()).isEqualTo(pacienteBack);
        assertThat(pacienteBack.getSujeto()).isEqualTo(sujeto);

        sujeto.paciente(null);
        assertThat(sujeto.getPaciente()).isNull();
        assertThat(pacienteBack.getSujeto()).isNull();
    }
}
