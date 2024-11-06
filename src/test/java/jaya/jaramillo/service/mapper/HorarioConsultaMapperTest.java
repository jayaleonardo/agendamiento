package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.HorarioConsultaAsserts.*;
import static jaya.jaramillo.domain.HorarioConsultaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HorarioConsultaMapperTest {

    private HorarioConsultaMapper horarioConsultaMapper;

    @BeforeEach
    void setUp() {
        horarioConsultaMapper = new HorarioConsultaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHorarioConsultaSample1();
        var actual = horarioConsultaMapper.toEntity(horarioConsultaMapper.toDto(expected));
        assertHorarioConsultaAllPropertiesEquals(expected, actual);
    }
}
