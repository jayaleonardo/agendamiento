package jaya.jaramillo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HorarioConsultaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHorarioConsultaAllPropertiesEquals(HorarioConsulta expected, HorarioConsulta actual) {
        assertHorarioConsultaAutoGeneratedPropertiesEquals(expected, actual);
        assertHorarioConsultaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHorarioConsultaAllUpdatablePropertiesEquals(HorarioConsulta expected, HorarioConsulta actual) {
        assertHorarioConsultaUpdatableFieldsEquals(expected, actual);
        assertHorarioConsultaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHorarioConsultaAutoGeneratedPropertiesEquals(HorarioConsulta expected, HorarioConsulta actual) {
        assertThat(expected)
            .as("Verify HorarioConsulta auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHorarioConsultaUpdatableFieldsEquals(HorarioConsulta expected, HorarioConsulta actual) {
        assertThat(expected)
            .as("Verify HorarioConsulta relevant properties")
            .satisfies(e -> assertThat(e.getDesde()).as("check desde").isEqualTo(actual.getDesde()))
            .satisfies(e -> assertThat(e.getHasta()).as("check hasta").isEqualTo(actual.getHasta()))
            .satisfies(e -> assertThat(e.getHoraInicio()).as("check horaInicio").isEqualTo(actual.getHoraInicio()))
            .satisfies(e -> assertThat(e.getHoraFin()).as("check horaFin").isEqualTo(actual.getHoraFin()))
            .satisfies(e -> assertThat(e.getDuracionMinutos()).as("check duracionMinutos").isEqualTo(actual.getDuracionMinutos()))
            .satisfies(e -> assertThat(e.getDiaSemana()).as("check diaSemana").isEqualTo(actual.getDiaSemana()))
            .satisfies(e -> assertThat(e.getEsHorarioAtencion()).as("check esHorarioAtencion").isEqualTo(actual.getEsHorarioAtencion()))
            .satisfies(e -> assertThat(e.getEstado()).as("check estado").isEqualTo(actual.getEstado()))
            .satisfies(e -> assertThat(e.getDesdeHoraAlmuerzo()).as("check desdeHoraAlmuerzo").isEqualTo(actual.getDesdeHoraAlmuerzo()))
            .satisfies(e -> assertThat(e.getHastaHoraAlmuerzo()).as("check hastaHoraAlmuerzo").isEqualTo(actual.getHastaHoraAlmuerzo()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHorarioConsultaUpdatableRelationshipsEquals(HorarioConsulta expected, HorarioConsulta actual) {
        assertThat(expected)
            .as("Verify HorarioConsulta relationships")
            .satisfies(e -> assertThat(e.getEspecialista()).as("check especialista").isEqualTo(actual.getEspecialista()));
    }
}
