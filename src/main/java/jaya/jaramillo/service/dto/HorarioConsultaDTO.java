package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.HorarioConsulta} entity.
 */
@Schema(description = "The HorarioConsulta entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HorarioConsultaDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "fecha del horario", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaHorario;

    @NotNull
    @Schema(description = "hora de inicio", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant horaInicio;

    @NotNull
    @Schema(description = "duración en minutos", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer duracionMinutos;

    @Schema(description = "día de la semana")
    private String diaSemana;

    @Schema(description = "indica si es horario de atención")
    private Boolean esHorarioAtencion;

    @Schema(description = "estado del horario")
    private String estado;

    @Schema(description = "desde hora del almuerzo")
    private Instant desdeHoraAlmuerzo;

    @Schema(description = "hasta hora del almuerzo")
    private Instant hastaHoraAlmuerzo;

    private EspecialistaDTO especialista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaHorario() {
        return fechaHorario;
    }

    public void setFechaHorario(LocalDate fechaHorario) {
        this.fechaHorario = fechaHorario;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Boolean getEsHorarioAtencion() {
        return esHorarioAtencion;
    }

    public void setEsHorarioAtencion(Boolean esHorarioAtencion) {
        this.esHorarioAtencion = esHorarioAtencion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getDesdeHoraAlmuerzo() {
        return desdeHoraAlmuerzo;
    }

    public void setDesdeHoraAlmuerzo(Instant desdeHoraAlmuerzo) {
        this.desdeHoraAlmuerzo = desdeHoraAlmuerzo;
    }

    public Instant getHastaHoraAlmuerzo() {
        return hastaHoraAlmuerzo;
    }

    public void setHastaHoraAlmuerzo(Instant hastaHoraAlmuerzo) {
        this.hastaHoraAlmuerzo = hastaHoraAlmuerzo;
    }

    public EspecialistaDTO getEspecialista() {
        return especialista;
    }

    public void setEspecialista(EspecialistaDTO especialista) {
        this.especialista = especialista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorarioConsultaDTO)) {
            return false;
        }

        HorarioConsultaDTO horarioConsultaDTO = (HorarioConsultaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, horarioConsultaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioConsultaDTO{" +
            "id=" + getId() +
            ", fechaHorario='" + getFechaHorario() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", duracionMinutos=" + getDuracionMinutos() +
            ", diaSemana='" + getDiaSemana() + "'" +
            ", esHorarioAtencion='" + getEsHorarioAtencion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", desdeHoraAlmuerzo='" + getDesdeHoraAlmuerzo() + "'" +
            ", hastaHoraAlmuerzo='" + getHastaHoraAlmuerzo() + "'" +
            ", especialista=" + getEspecialista() +
            "}";
    }
}
