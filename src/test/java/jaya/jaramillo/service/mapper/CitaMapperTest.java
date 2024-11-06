package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.CitaAsserts.*;
import static jaya.jaramillo.domain.CitaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CitaMapperTest {

    private CitaMapper citaMapper;

    @BeforeEach
    void setUp() {
        citaMapper = new CitaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCitaSample1();
        var actual = citaMapper.toEntity(citaMapper.toDto(expected));
        assertCitaAllPropertiesEquals(expected, actual);
    }
}
