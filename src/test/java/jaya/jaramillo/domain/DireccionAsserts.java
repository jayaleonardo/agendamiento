package jaya.jaramillo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DireccionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDireccionAllPropertiesEquals(Direccion expected, Direccion actual) {
        assertDireccionAutoGeneratedPropertiesEquals(expected, actual);
        assertDireccionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDireccionAllUpdatablePropertiesEquals(Direccion expected, Direccion actual) {
        assertDireccionUpdatableFieldsEquals(expected, actual);
        assertDireccionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDireccionAutoGeneratedPropertiesEquals(Direccion expected, Direccion actual) {
        assertThat(expected)
            .as("Verify Direccion auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDireccionUpdatableFieldsEquals(Direccion expected, Direccion actual) {
        assertThat(expected)
            .as("Verify Direccion relevant properties")
            .satisfies(e -> assertThat(e.getIdDireccion()).as("check idDireccion").isEqualTo(actual.getIdDireccion()))
            .satisfies(e -> assertThat(e.getPais()).as("check pais").isEqualTo(actual.getPais()))
            .satisfies(e -> assertThat(e.getProvincia()).as("check provincia").isEqualTo(actual.getProvincia()))
            .satisfies(e -> assertThat(e.getCiudad()).as("check ciudad").isEqualTo(actual.getCiudad()))
            .satisfies(e -> assertThat(e.getCalles()).as("check calles").isEqualTo(actual.getCalles()))
            .satisfies(e -> assertThat(e.getNroDomicilio()).as("check nroDomicilio").isEqualTo(actual.getNroDomicilio()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDireccionUpdatableRelationshipsEquals(Direccion expected, Direccion actual) {
        assertThat(expected)
            .as("Verify Direccion relationships")
            .satisfies(e -> assertThat(e.getSujeto()).as("check sujeto").isEqualTo(actual.getSujeto()));
    }
}
