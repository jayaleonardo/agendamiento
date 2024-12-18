package jaya.jaramillo.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CitaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaAllPropertiesEquals(Cita expected, Cita actual) {
        assertCitaAutoGeneratedPropertiesEquals(expected, actual);
        assertCitaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaAllUpdatablePropertiesEquals(Cita expected, Cita actual) {
        assertCitaUpdatableFieldsEquals(expected, actual);
        assertCitaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaAutoGeneratedPropertiesEquals(Cita expected, Cita actual) {
        assertThat(expected)
            .as("Verify Cita auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaUpdatableFieldsEquals(Cita expected, Cita actual) {
        assertThat(expected)
            .as("Verify Cita relevant properties")
            .satisfies(e -> assertThat(e.getFechaCita()).as("check fechaCita").isEqualTo(actual.getFechaCita()))
            .satisfies(e -> assertThat(e.getHoraInicio()).as("check horaInicio").isEqualTo(actual.getHoraInicio()))
            .satisfies(e -> assertThat(e.getDuracionMinutos()).as("check duracionMinutos").isEqualTo(actual.getDuracionMinutos()))
            .satisfies(e -> assertThat(e.getEstado()).as("check estado").isEqualTo(actual.getEstado()))
            .satisfies(e -> assertThat(e.getTipoVisita()).as("check tipoVisita").isEqualTo(actual.getTipoVisita()))
            .satisfies(e -> assertThat(e.getCanalAtencion()).as("check canalAtencion").isEqualTo(actual.getCanalAtencion()))
            .satisfies(e -> assertThat(e.getObservacion()).as("check observacion").isEqualTo(actual.getObservacion()))
            .satisfies(e -> assertThat(e.getRecordatorio()).as("check recordatorio").isEqualTo(actual.getRecordatorio()))
            .satisfies(e -> assertThat(e.getMotivoConsulta()).as("check motivoConsulta").isEqualTo(actual.getMotivoConsulta()))
            .satisfies(e ->
                assertThat(e.getDetalleConsultaVirtual()).as("check detalleConsultaVirtual").isEqualTo(actual.getDetalleConsultaVirtual())
            )
            .satisfies(e -> assertThat(e.getVirtual()).as("check virtual").isEqualTo(actual.getVirtual()))
            .satisfies(e -> assertThat(e.getInformacionReserva()).as("check informacionReserva").isEqualTo(actual.getInformacionReserva()))
            .satisfies(e -> assertThat(e.getTarea()).as("check tarea").isEqualTo(actual.getTarea()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCitaUpdatableRelationshipsEquals(Cita expected, Cita actual) {
        assertThat(expected)
            .as("Verify Cita relationships")
            .satisfies(e -> assertThat(e.getEspecialista()).as("check especialista").isEqualTo(actual.getEspecialista()))
            .satisfies(e -> assertThat(e.getTipoTerapia()).as("check tipoTerapia").isEqualTo(actual.getTipoTerapia()))
            .satisfies(e -> assertThat(e.getPaciente()).as("check paciente").isEqualTo(actual.getPaciente()))
            .satisfies(e -> assertThat(e.getProgramacion()).as("check programacion").isEqualTo(actual.getProgramacion()));
    }
}
