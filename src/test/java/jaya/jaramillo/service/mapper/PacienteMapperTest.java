package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.PacienteAsserts.*;
import static jaya.jaramillo.domain.PacienteTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacienteMapperTest {

    private PacienteMapper pacienteMapper;

    @BeforeEach
    void setUp() {
        pacienteMapper = new PacienteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPacienteSample1();
        var actual = pacienteMapper.toEntity(pacienteMapper.toDto(expected));
        assertPacienteAllPropertiesEquals(expected, actual);
    }
}
