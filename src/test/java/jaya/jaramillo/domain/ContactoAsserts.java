package jaya.jaramillo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContactoAllPropertiesEquals(Contacto expected, Contacto actual) {
        assertContactoAutoGeneratedPropertiesEquals(expected, actual);
        assertContactoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContactoAllUpdatablePropertiesEquals(Contacto expected, Contacto actual) {
        assertContactoUpdatableFieldsEquals(expected, actual);
        assertContactoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContactoAutoGeneratedPropertiesEquals(Contacto expected, Contacto actual) {
        assertThat(expected)
            .as("Verify Contacto auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContactoUpdatableFieldsEquals(Contacto expected, Contacto actual) {
        assertThat(expected)
            .as("Verify Contacto relevant properties")
            .satisfies(e -> assertThat(e.getTelefono()).as("check telefono").isEqualTo(actual.getTelefono()))
            .satisfies(e -> assertThat(e.getCorreo()).as("check correo").isEqualTo(actual.getCorreo()))
            .satisfies(e -> assertThat(e.getCodigoPais()).as("check codigoPais").isEqualTo(actual.getCodigoPais()))
            .satisfies(e -> assertThat(e.getCelular()).as("check celular").isEqualTo(actual.getCelular()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertContactoUpdatableRelationshipsEquals(Contacto expected, Contacto actual) {
        assertThat(expected)
            .as("Verify Contacto relationships")
            .satisfies(e -> assertThat(e.getSujeto()).as("check sujeto").isEqualTo(actual.getSujeto()));
    }
}
