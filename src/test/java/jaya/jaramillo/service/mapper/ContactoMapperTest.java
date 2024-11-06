package jaya.jaramillo.service.mapper;

import static jaya.jaramillo.domain.ContactoAsserts.*;
import static jaya.jaramillo.domain.ContactoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactoMapperTest {

    private ContactoMapper contactoMapper;

    @BeforeEach
    void setUp() {
        contactoMapper = new ContactoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContactoSample1();
        var actual = contactoMapper.toEntity(contactoMapper.toDto(expected));
        assertContactoAllPropertiesEquals(expected, actual);
    }
}
