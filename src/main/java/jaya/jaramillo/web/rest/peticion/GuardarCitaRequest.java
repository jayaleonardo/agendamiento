package jaya.jaramillo.web.rest.peticion;

import java.time.Instant;
import java.time.LocalDate;

public class GuardarCitaRequest {

    private LocalDate fechaCita;
    private String horaInicio;
    private String horaFin;
    private String estado;
    private String tipoVisita;
    private String canalAtencion;
    private String recordatorio;
    private String observacion;
    private String motivoConsulta;
    private String detalleConsultaVirtual;
    private Boolean virtual;
    private Long pacienteId;
    private Long programacionId;
    private Long citaId;

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horainicio) {
        this.horaInicio = horainicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
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

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProgramacionId() {
        return programacionId;
    }

    public void setProgramacionId(Long programacionId) {
        this.programacionId = programacionId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    @Override
    public String toString() {
        return (
            "GuardarCitaRequest [fechaCita=" +
            fechaCita +
            ", horainicio=" +
            horaInicio +
            ", horaFin=" +
            horaFin +
            ", estado=" +
            estado +
            ", tipoVisita=" +
            tipoVisita +
            ", canalAtencion=" +
            canalAtencion +
            ", observacion=" +
            observacion +
            ", motivoConsulta=" +
            motivoConsulta +
            ", detalleConsultaVirtual=" +
            detalleConsultaVirtual +
            ", virtual=" +
            virtual +
            ", pacienteId=" +
            pacienteId +
            ", programacionId=" +
            programacionId +
            "]"
        );
    }
}
