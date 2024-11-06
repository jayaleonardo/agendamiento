package jaya.jaramillo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Cita entity.
 */
@Entity
@Table(name = "cita")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * fecha de la cita
     */
    @NotNull
    @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    /**
     * hora de inicio
     */
    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private Instant horaInicio;

    /**
     * duración en minutos
     */
    @NotNull
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    /**
     * estado
     */
    @Column(name = "estado")
    private String estado;

    /**
     * tipo de visita
     */
    @Column(name = "tipo_visita")
    private String tipoVisita;

    /**
     * canal de atención
     */
    @Column(name = "canal_atencion")
    private String canalAtencion;

    /**
     * observación
     */
    @Column(name = "observacion")
    private String observacion;

    /**
     * recordatorio
     */
    @Column(name = "recordatorio")
    private String recordatorio;

    /**
     * motivo de consulta
     */
    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    /**
     * detalle de consulta virtual
     */
    @Column(name = "detalle_consulta_virtual")
    private String detalleConsultaVirtual;

    /**
     * indica si es virtual
     */
    @Column(name = "virtual")
    private Boolean virtual;

    /**
     * información de reserva
     */
    @Column(name = "informacion_reserva")
    private String informacionReserva;

    /**
     * Cita relacionadas con Especialista
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sujeto", "horarioConsultas" }, allowSetters = true)
    private Especialista especialista;

    /**
     * Cita relacionadas con TipoTerapia
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoTerapia tipoTerapia;

    /**
     * Cita relacionadas con Paciente
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sujeto" }, allowSetters = true)
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cita id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCita() {
        return this.fechaCita;
    }

    public Cita fechaCita(LocalDate fechaCita) {
        this.setFechaCita(fechaCita);
        return this;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Instant getHoraInicio() {
        return this.horaInicio;
    }

    public Cita horaInicio(Instant horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getDuracionMinutos() {
        return this.duracionMinutos;
    }

    public Cita duracionMinutos(Integer duracionMinutos) {
        this.setDuracionMinutos(duracionMinutos);
        return this;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getEstado() {
        return this.estado;
    }

    public Cita estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoVisita() {
        return this.tipoVisita;
    }

    public Cita tipoVisita(String tipoVisita) {
        this.setTipoVisita(tipoVisita);
        return this;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getCanalAtencion() {
        return this.canalAtencion;
    }

    public Cita canalAtencion(String canalAtencion) {
        this.setCanalAtencion(canalAtencion);
        return this;
    }

    public void setCanalAtencion(String canalAtencion) {
        this.canalAtencion = canalAtencion;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public Cita observacion(String observacion) {
        this.setObservacion(observacion);
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getRecordatorio() {
        return this.recordatorio;
    }

    public Cita recordatorio(String recordatorio) {
        this.setRecordatorio(recordatorio);
        return this;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getMotivoConsulta() {
        return this.motivoConsulta;
    }

    public Cita motivoConsulta(String motivoConsulta) {
        this.setMotivoConsulta(motivoConsulta);
        return this;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getDetalleConsultaVirtual() {
        return this.detalleConsultaVirtual;
    }

    public Cita detalleConsultaVirtual(String detalleConsultaVirtual) {
        this.setDetalleConsultaVirtual(detalleConsultaVirtual);
        return this;
    }

    public void setDetalleConsultaVirtual(String detalleConsultaVirtual) {
        this.detalleConsultaVirtual = detalleConsultaVirtual;
    }

    public Boolean getVirtual() {
        return this.virtual;
    }

    public Cita virtual(Boolean virtual) {
        this.setVirtual(virtual);
        return this;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public String getInformacionReserva() {
        return this.informacionReserva;
    }

    public Cita informacionReserva(String informacionReserva) {
        this.setInformacionReserva(informacionReserva);
        return this;
    }

    public void setInformacionReserva(String informacionReserva) {
        this.informacionReserva = informacionReserva;
    }

    public Especialista getEspecialista() {
        return this.especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public Cita especialista(Especialista especialista) {
        this.setEspecialista(especialista);
        return this;
    }

    public TipoTerapia getTipoTerapia() {
        return this.tipoTerapia;
    }

    public void setTipoTerapia(TipoTerapia tipoTerapia) {
        this.tipoTerapia = tipoTerapia;
    }

    public Cita tipoTerapia(TipoTerapia tipoTerapia) {
        this.setTipoTerapia(tipoTerapia);
        return this;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Cita paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cita)) {
            return false;
        }
        return getId() != null && getId().equals(((Cita) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cita{" +
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
            "}";
    }
}
