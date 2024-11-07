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
     * fecha turno
     */
    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * desde hora inicio
     */
    @NotNull
    @Column(name = "desde", nullable = false)
    private Instant desde;

    /**
     * hasta hora fin
     */
    @NotNull
    @Column(name = "hasta", nullable = false)
    private Instant hasta;

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

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Programacion fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Instant getDesde() {
        return this.desde;
    }

    public Programacion desde(Instant desde) {
        this.setDesde(desde);
        return this;
    }

    public void setDesde(Instant desde) {
        this.desde = desde;
    }

    public Instant getHasta() {
        return this.hasta;
    }

    public Programacion hasta(Instant hasta) {
        this.setHasta(hasta);
        return this;
    }

    public void setHasta(Instant hasta) {
        this.hasta = hasta;
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
            ", fecha='" + getFecha() + "'" +
            ", desde='" + getDesde() + "'" +
            ", hasta='" + getHasta() + "'" +
            "}";
    }
}
