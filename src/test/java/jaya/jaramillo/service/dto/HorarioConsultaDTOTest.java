package jaya.jaramillo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jaya.jaramillo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HorarioConsultaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HorarioConsultaDTO.class);
        HorarioConsultaDTO horarioConsultaDTO1 = new HorarioConsultaDTO();
        horarioConsultaDTO1.setId(1L);
        HorarioConsultaDTO horarioConsultaDTO2 = new HorarioConsultaDTO();
        assertThat(horarioConsultaDTO1).isNotEqualTo(horarioConsultaDTO2);
        horarioConsultaDTO2.setId(horarioConsultaDTO1.getId());
        assertThat(horarioConsultaDTO1).isEqualTo(horarioConsultaDTO2);
        horarioConsultaDTO2.setId(2L);
        assertThat(horarioConsultaDTO1).isNotEqualTo(horarioConsultaDTO2);
        horarioConsultaDTO1.setId(null);
        assertThat(horarioConsultaDTO1).isNotEqualTo(horarioConsultaDTO2);
    }
}
