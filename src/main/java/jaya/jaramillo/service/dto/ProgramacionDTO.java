package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Programacion} entity.
 */
@Schema(description = "The Programacion entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgramacionDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "fecha desde", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaDesde;

    @NotNull
    @Schema(description = "fecha hasta", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaHasta;

    @NotNull
    @Schema(description = "duración en minutos", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer duracionMinutos;

    @Schema(description = "desde hora del almuerzo")
    private Instant desdeHoraAlmuerzo;

    @Schema(description = "hasta hora del almuerzo")
    private Instant hastaHoraAlmuerzo;

    @Schema(description = "días de la semana")
    private String diasSemana;

    private HorarioConsultaDTO horarioConsulta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
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

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public HorarioConsultaDTO getHorarioConsulta() {
        return horarioConsulta;
    }

    public void setHorarioConsulta(HorarioConsultaDTO horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramacionDTO)) {
            return false;
        }

        ProgramacionDTO programacionDTO = (ProgramacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, programacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramacionDTO{" +
            "id=" + getId() +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
            ", duracionMinutos=" + getDuracionMinutos() +
            ", desdeHoraAlmuerzo='" + getDesdeHoraAlmuerzo() + "'" +
            ", hastaHoraAlmuerzo='" + getHastaHoraAlmuerzo() + "'" +
            ", diasSemana='" + getDiasSemana() + "'" +
            ", horarioConsulta=" + getHorarioConsulta() +
            "}";
    }
}
