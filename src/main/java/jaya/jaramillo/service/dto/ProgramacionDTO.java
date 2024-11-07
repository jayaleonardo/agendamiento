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
    @Schema(description = "fecha turno", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fecha;

    @NotNull
    @Schema(description = "desde hora inicio", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant desde;

    @NotNull
    @Schema(description = "hasta hora fin", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant hasta;

    private HorarioConsultaDTO horarioConsulta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Instant getDesde() {
        return desde;
    }

    public void setDesde(Instant desde) {
        this.desde = desde;
    }

    public Instant getHasta() {
        return hasta;
    }

    public void setHasta(Instant hasta) {
        this.hasta = hasta;
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
            ", fecha='" + getFecha() + "'" +
            ", desde='" + getDesde() + "'" +
            ", hasta='" + getHasta() + "'" +
            ", horarioConsulta=" + getHorarioConsulta() +
            "}";
    }
}
