package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.SujetoAsserts.*;
import static jaya.jaramillo.domain.SujetoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SujetoMapperTest {

    private SujetoMapper sujetoMapper;

    @BeforeEach
    void setUp() {
        sujetoMapper = new SujetoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSujetoSample1();
        var actual = sujetoMapper.toEntity(sujetoMapper.toDto(expected));
        assertSujetoAllPropertiesEquals(expected, actual);
    }
}
