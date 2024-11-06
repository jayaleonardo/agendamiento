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
 * The Programacion entity.
 */
@Entity
@Table(name = "programacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Programacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * fecha desde
     */
    @NotNull
    @Column(name = "fecha_desde", nullable = false)
    private LocalDate fechaDesde;

    /**
     * fecha hasta
     */
    @NotNull
    @Column(name = "fecha_hasta", nullable = false)
    private LocalDate fechaHasta;

    /**
     * duración en minutos
     */
    @NotNull
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

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
     * días de la semana
     */
    @Column(name = "dias_semana")
    private String diasSemana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "programacions", "especialista" }, allowSetters = true)
    private HorarioConsulta horarioConsulta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Programacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaDesde() {
        return this.fechaDesde;
    }

    public Programacion fechaDesde(LocalDate fechaDesde) {
        this.setFechaDesde(fechaDesde);
        return this;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return this.fechaHasta;
    }

    public Programacion fechaHasta(LocalDate fechaHasta) {
        this.setFechaHasta(fechaHasta);
        return this;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getDuracionMinutos() {
        return this.duracionMinutos;
    }

    public Programacion duracionMinutos(Integer duracionMinutos) {
        this.setDuracionMinutos(duracionMinutos);
        return this;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public Instant getDesdeHoraAlmuerzo() {
        return this.desdeHoraAlmuerzo;
    }

    public Programacion desdeHoraAlmuerzo(Instant desdeHoraAlmuerzo) {
        this.setDesdeHoraAlmuerzo(desdeHoraAlmuerzo);
        return this;
    }

    public void setDesdeHoraAlmuerzo(Instant desdeHoraAlmuerzo) {
        this.desdeHoraAlmuerzo = desdeHoraAlmuerzo;
    }

    public Instant getHastaHoraAlmuerzo() {
        return this.hastaHoraAlmuerzo;
    }

    public Programacion hastaHoraAlmuerzo(Instant hastaHoraAlmuerzo) {
        this.setHastaHoraAlmuerzo(hastaHoraAlmuerzo);
        return this;
    }

    public void setHastaHoraAlmuerzo(Instant hastaHoraAlmuerzo) {
        this.hastaHoraAlmuerzo = hastaHoraAlmuerzo;
    }

    public String getDiasSemana() {
        return this.diasSemana;
    }

    public Programacion diasSemana(String diasSemana) {
        this.setDiasSemana(diasSemana);
        return this;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public HorarioConsulta getHorarioConsulta() {
        return this.horarioConsulta;
    }

    public void setHorarioConsulta(HorarioConsulta horarioConsulta) {
        this.horarioConsulta = horarioConsulta;
    }

    public Programacion horarioConsulta(HorarioConsulta horarioConsulta) {
        this.setHorarioConsulta(horarioConsulta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programacion)) {
            return false;
        }
        return getId() != null && getId().equals(((Programacion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Programacion{" +
            "id=" + getId() +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
            ", duracionMinutos=" + getDuracionMinutos() +
            ", desdeHoraAlmuerzo='" + getDesdeHoraAlmuerzo() + "'" +
            ", hastaHoraAlmuerzo='" + getHastaHoraAlmuerzo() + "'" +
            ", diasSemana='" + getDiasSemana() + "'" +
            "}";
    }
}
