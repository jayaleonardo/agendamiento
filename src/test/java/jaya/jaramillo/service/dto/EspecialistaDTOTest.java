package jaya.jaramillo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EspecialistaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EspecialistaDTO.class);
        EspecialistaDTO especialistaDTO1 = new EspecialistaDTO();
        especialistaDTO1.setId(1L);
        EspecialistaDTO especialistaDTO2 = new EspecialistaDTO();
        assertThat(especialistaDTO1).isNotEqualTo(especialistaDTO2);
        especialistaDTO2.setId(especialistaDTO1.getId());
        assertThat(especialistaDTO1).isEqualTo(especialistaDTO2);
        especialistaDTO2.setId(2L);
        assertThat(especialistaDTO1).isNotEqualTo(especialistaDTO2);
        especialistaDTO1.setId(null);
        assertThat(especialistaDTO1).isNotEqualTo(especialistaDTO2);
    }
}
