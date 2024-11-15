package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Cita} entity.
 */
@Schema(description = "The Cita entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CitaDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "fecha de la cita", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaCita;

    @NotNull
    @Schema(description = "hora de inicio", requiredMode = Schema.RequiredMode.REQUIRED)
    private Instant horaInicio;

    @NotNull
    @Schema(description = "duración en minutos", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer duracionMinutos;

    @Schema(description = "estado")
    private String estado;

    @Schema(description = "tipo de visita")
    private String tipoVisita;

    @Schema(description = "canal de atención")
    private String canalAtencion;

    @Schema(description = "observación")
    private String observacion;

    @Schema(description = "recordatorio")
    private String recordatorio;

    @Schema(description = "motivo de consulta")
    private String motivoConsulta;

    @Schema(description = "detalle de consulta virtual")
    private String detalleConsultaVirtual;

    @Schema(description = "indica si es virtual")
    private Boolean virtual;

    @Schema(description = "información de reserva")
    private String informacionReserva;

    @Schema(description = "Cita relacionadas con Especialista")
    private EspecialistaDTO especialista;

    @Schema(description = "Cita relacionadas con TipoTerapia")
    private TipoTerapiaDTO tipoTerapia;

    @Schema(description = "Cita relacionadas con Paciente")
    private PacienteDTO paciente;

    @Schema(description = "Cita relacionadas con Programacion")
    private ProgramacionDTO programacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getCanalAtencion() {
        return canalAtencion;
    }

    public void setCanalAtencion(String canalAtencion) {
        this.canalAtencion = canalAtencion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getDetalleConsultaVirtual() {
        return detalleConsultaVirtual;
    }

    public void setDetalleConsultaVirtual(String detalleConsultaVirtual) {
        this.detalleConsultaVirtual = detalleConsultaVirtual;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public String getInformacionReserva() {
        return informacionReserva;
    }

    public void setInformacionReserva(String informacionReserva) {
        this.informacionReserva = informacionReserva;
    }

    public EspecialistaDTO getEspecialista() {
        return especialista;
    }

    public void setEspecialista(EspecialistaDTO especialista) {
        this.especialista = especialista;
    }

    public TipoTerapiaDTO getTipoTerapia() {
        return tipoTerapia;
    }

    public void setTipoTerapia(TipoTerapiaDTO tipoTerapia) {
        this.tipoTerapia = tipoTerapia;
    }

    public PacienteDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteDTO paciente) {
        this.paciente = paciente;
    }

    public ProgramacionDTO getProgramacion() {
        return programacion;
    }

    public void setProgramacion(ProgramacionDTO programacion) {
        this.programacion = programacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CitaDTO)) {
            return false;
        }

        CitaDTO citaDTO = (CitaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, citaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CitaDTO{" +
            "id=" + getId() +
            ", fechaCita='" + getFechaCita() + "'" +
            ", horaInicio='" + getHoraInicio() + "'" +
            ", duracionMinutos=" + getDuracionMinutos() +
            ", estado='" + getEstado() + "'" +
            ", tipoVisita='" + getTipoVisita() + "'" +
            ", canalAtencion='" + getCanalAtencion() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", recordatorio='" + getRecordatorio() + "'" +
            ", motivoConsulta='" + getMotivoConsulta() + "'" +
            ", detalleConsultaVirtual='" + getDetalleConsultaVirtual() + "'" +
            ", virtual='" + getVirtual() + "'" +
            ", informacionReserva='" + getInformacionReserva() + "'" +
            ", especialista=" + getEspecialista() +
            ", tipoTerapia=" + getTipoTerapia() +
            ", paciente=" + getPaciente() +
            ", programacion=" + getProgramacion() +
            "}";
    }
}
