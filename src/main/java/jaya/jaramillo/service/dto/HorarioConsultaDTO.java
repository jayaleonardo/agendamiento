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
    @Schema(description = "fecha desde del horario", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate desde;

    @NotNull
    @Schema(description = "fecha hasta del horario", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate hasta;

    @NotNull
    @Schema(description = "hora de inicio", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant horaInicio;

    @NotNull
    @Schema(description = "hora fin", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant horaFin;

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

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Instant horaFin) {
        this.horaFin = horaFin;
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
            ", desde='" + getDesde() + "'" +
            ", hasta='" + getHasta() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", horaFin='" + getHoraFin() + "'" +
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
