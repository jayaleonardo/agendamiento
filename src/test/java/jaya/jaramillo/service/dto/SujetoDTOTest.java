package jaya.jaramillo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SujetoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SujetoDTO.class);
        SujetoDTO sujetoDTO1 = new SujetoDTO();
        sujetoDTO1.setId(1L);
        SujetoDTO sujetoDTO2 = new SujetoDTO();
        assertThat(sujetoDTO1).isNotEqualTo(sujetoDTO2);
        sujetoDTO2.setId(sujetoDTO1.getId());
        assertThat(sujetoDTO1).isEqualTo(sujetoDTO2);
        sujetoDTO2.setId(2L);
        assertThat(sujetoDTO1).isNotEqualTo(sujetoDTO2);
        sujetoDTO1.setId(null);
        assertThat(sujetoDTO1).isNotEqualTo(sujetoDTO2);
    }
}
