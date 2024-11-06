package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.EspecialistaAsserts.*;
import static jaya.jaramillo.domain.EspecialistaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EspecialistaMapperTest {

    private EspecialistaMapper especialistaMapper;

    @BeforeEach
    void setUp() {
        especialistaMapper = new EspecialistaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEspecialistaSample1();
        var actual = especialistaMapper.toEntity(especialistaMapper.toDto(expected));
        assertEspecialistaAllPropertiesEquals(expected, actual);
    }
}
