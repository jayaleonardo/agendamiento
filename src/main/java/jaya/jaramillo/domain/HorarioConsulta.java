package jaya.jaramillo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The HorarioConsulta entity.
 */
@Entity
@Table(name = "horario_consulta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HorarioConsulta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * fecha desde del horario
     */
    @NotNull
    @Column(name = "desde", nullable = false)
    private LocalDate desde;

    /**
     * fecha hasta del horario
     */
    @NotNull
    @Column(name = "hasta", nullable = false)
    private LocalDate hasta;

    /**
     * hora de inicio
     */
    @NotNull
    @Column(name = "hora_inicio", nullable = false)
    private Instant horaInicio;

    /**
     * hora fin
     */
    @NotNull
    @Column(name = "hora_fin", nullable = false)
    private Instant horaFin;

    /**
     * duración en minutos
     */
    @NotNull
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    /**
     * día de la semana
     */
    @Column(name = "dia_semana")
    private String diaSemana;

    /**
     * indica si es horario de atención
     */
    @Column(name = "es_horario_atencion")
    private Boolean esHorarioAtencion;

    /**
     * estado del horario
     */
    @Column(name = "estado")
    private String estado;

    /**
     * desde hora del almuerzo
     */
    @Column(name = "desde_hora_almuerzo")
    private Instant desdeHoraAlmuerzo;

    /**
     * hasta hora del almuerzo
     */
    @Column(name = "hasta_hora_almuerzo")
    private Instant hastaHoraAlmuerzo;

    /**
     * HorarioConsulta relacionado con Programacion
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "horarioConsulta")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "horarioConsulta" }, allowSetters = true)
    private Set<Programacion> programacions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "sujeto", "horarioConsultas" }, allowSetters = true)
    private Especialista especialista;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HorarioConsulta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDesde() {
        return this.desde;
    }

    public HorarioConsulta desde(LocalDate desde) {
        this.setDesde(desde);
        return this;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return this.hasta;
    }

    public HorarioConsulta hasta(LocalDate hasta) {
        this.setHasta(hasta);
        return this;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }

    public Instant getHoraInicio() {
        return this.horaInicio;
    }

    public HorarioConsulta horaInicio(Instant horaInicio) {
        this.setHoraInicio(horaInicio);
        return this;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFin() {
        return this.horaFin;
    }

    public HorarioConsulta horaFin(Instant horaFin) {
        this.setHoraFin(horaFin);
        return this;
    }

    public void setHoraFin(Instant horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getDuracionMinutos() {
        return this.duracionMinutos;
    }

    public HorarioConsulta duracionMinutos(Integer duracionMinutos) {
        this.setDuracionMinutos(duracionMinutos);
        return this;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getDiaSemana() {
        return this.diaSemana;
    }

    public HorarioConsulta diaSemana(String diaSemana) {
        this.setDiaSemana(diaSemana);
        return this;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Boolean getEsHorarioAtencion() {
        return this.esHorarioAtencion;
    }

    public HorarioConsulta esHorarioAtencion(Boolean esHorarioAtencion) {
        this.setEsHorarioAtencion(esHorarioAtencion);
        return this;
    }

    public void setEsHorarioAtencion(Boolean esHorarioAtencion) {
        this.esHorarioAtencion = esHorarioAtencion;
    }

    public String getEstado() {
        return this.estado;
    }

    public HorarioConsulta estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Instant getDesdeHoraAlmuerzo() {
        return this.desdeHoraAlmuerzo;
    }

    public HorarioConsulta desdeHoraAlmuerzo(Instant desdeHoraAlmuerzo) {
        this.setDesdeHoraAlmuerzo(desdeHoraAlmuerzo);
        return this;
    }

    public void setDesdeHoraAlmuerzo(Instant desdeHoraAlmuerzo) {
        this.desdeHoraAlmuerzo = desdeHoraAlmuerzo;
    }

    public Instant getHastaHoraAlmuerzo() {
        return this.hastaHoraAlmuerzo;
    }

    public HorarioConsulta hastaHoraAlmuerzo(Instant hastaHoraAlmuerzo) {
        this.setHastaHoraAlmuerzo(hastaHoraAlmuerzo);
        return this;
    }

    public void setHastaHoraAlmuerzo(Instant hastaHoraAlmuerzo) {
        this.hastaHoraAlmuerzo = hastaHoraAlmuerzo;
    }

    public Set<Programacion> getProgramacions() {
        return this.programacions;
    }

    public void setProgramacions(Set<Programacion> programacions) {
        if (this.programacions != null) {
            this.programacions.forEach(i -> i.setHorarioConsulta(null));
        }
        if (programacions != null) {
            programacions.forEach(i -> i.setHorarioConsulta(this));
        }
        this.programacions = programacions;
    }

    public HorarioConsulta programacions(Set<Programacion> programacions) {
        this.setProgramacions(programacions);
        return this;
    }

    public HorarioConsulta addProgramacion(Programacion programacion) {
        this.programacions.add(programacion);
        programacion.setHorarioConsulta(this);
        return this;
    }

    public HorarioConsulta removeProgramacion(Programacion programacion) {
        this.programacions.remove(programacion);
        programacion.setHorarioConsulta(null);
        return this;
    }

    public Especialista getEspecialista() {
        return this.especialista;
    }

    public void setEspecialista(Especialista especialista) {
        this.especialista = especialista;
    }

    public HorarioConsulta especialista(Especialista especialista) {
        this.setEspecialista(especialista);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HorarioConsulta)) {
            return false;
        }
        return getId() != null && getId().equals(((HorarioConsulta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HorarioConsulta{" +
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
            "}";
    }
}
