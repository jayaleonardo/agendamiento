package jaya.jaramillo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoTerapiaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoTerapiaDTO.class);
        TipoTerapiaDTO tipoTerapiaDTO1 = new TipoTerapiaDTO();
        tipoTerapiaDTO1.setId(1L);
        TipoTerapiaDTO tipoTerapiaDTO2 = new TipoTerapiaDTO();
        assertThat(tipoTerapiaDTO1).isNotEqualTo(tipoTerapiaDTO2);
        tipoTerapiaDTO2.setId(tipoTerapiaDTO1.getId());
        assertThat(tipoTerapiaDTO1).isEqualTo(tipoTerapiaDTO2);
        tipoTerapiaDTO2.setId(2L);
        assertThat(tipoTerapiaDTO1).isNotEqualTo(tipoTerapiaDTO2);
        tipoTerapiaDTO1.setId(null);
        assertThat(tipoTerapiaDTO1).isNotEqualTo(tipoTerapiaDTO2);
    }
}
