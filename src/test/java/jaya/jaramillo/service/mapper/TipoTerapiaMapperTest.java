package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.TipoTerapiaAsserts.*;
import static jaya.jaramillo.domain.TipoTerapiaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoTerapiaMapperTest {

    private TipoTerapiaMapper tipoTerapiaMapper;

    @BeforeEach
    void setUp() {
        tipoTerapiaMapper = new TipoTerapiaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTipoTerapiaSample1();
        var actual = tipoTerapiaMapper.toEntity(tipoTerapiaMapper.toDto(expected));
        assertTipoTerapiaAllPropertiesEquals(expected, actual);
    }
}
