package jaya.jaramillo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoDTO.class);
        ContactoDTO contactoDTO1 = new ContactoDTO();
        contactoDTO1.setId(1L);
        ContactoDTO contactoDTO2 = new ContactoDTO();
        assertThat(contactoDTO1).isNotEqualTo(contactoDTO2);
        contactoDTO2.setId(contactoDTO1.getId());
        assertThat(contactoDTO1).isEqualTo(contactoDTO2);
        contactoDTO2.setId(2L);
        assertThat(contactoDTO1).isNotEqualTo(contactoDTO2);
        contactoDTO1.setId(null);
        assertThat(contactoDTO1).isNotEqualTo(contactoDTO2);
    }
}
