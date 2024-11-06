package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.ProgramacionAsserts.*;
import static jaya.jaramillo.domain.ProgramacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProgramacionMapperTest {

    private ProgramacionMapper programacionMapper;

    @BeforeEach
    void setUp() {
        programacionMapper = new ProgramacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProgramacionSample1();
        var actual = programacionMapper.toEntity(programacionMapper.toDto(expected));
        assertProgramacionAllPropertiesEquals(expected, actual);
    }
}
