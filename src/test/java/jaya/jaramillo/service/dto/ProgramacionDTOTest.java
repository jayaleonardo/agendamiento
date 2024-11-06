package jaya.jaramillo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgramacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramacionDTO.class);
        ProgramacionDTO programacionDTO1 = new ProgramacionDTO();
        programacionDTO1.setId(1L);
        ProgramacionDTO programacionDTO2 = new ProgramacionDTO();
        assertThat(programacionDTO1).isNotEqualTo(programacionDTO2);
        programacionDTO2.setId(programacionDTO1.getId());
        assertThat(programacionDTO1).isEqualTo(programacionDTO2);
        programacionDTO2.setId(2L);
        assertThat(programacionDTO1).isNotEqualTo(programacionDTO2);
        programacionDTO1.setId(null);
        assertThat(programacionDTO1).isNotEqualTo(programacionDTO2);
    }
}
