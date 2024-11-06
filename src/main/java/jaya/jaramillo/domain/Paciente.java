package jaya.jaramillo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Paciente entity.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * número de historia clínica
     */
    @NotNull
    @Column(name = "nro_historia", nullable = false)
    private Integer nroHistoria;

    /**
     * nombre del representante
     */
    @Column(name = "nombre_representante")
    private String nombreRepresentante;

    /**
     * parentesco del representante
     */
    @Column(name = "parentesco_representante")
    private String parentescoRepresentante;

    /**
     * correo electrónico del representante
     */
    @Column(name = "correo_representante")
    private String correoRepresentante;

    /**
     * celular del representante
     */
    @Column(name = "celular_representante")
    private String celularRepresentante;

    /**
     * dirección del representante
     */
    @Column(name = "direccion_representante")
    private String direccionRepresentante;

    /**
     * Paciente relacionado con Sujeto
     */
    @JsonIgnoreProperties(value = { "contacto", "direccion", "especialista", "paciente" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Sujeto sujeto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paciente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroHistoria() {
        return this.nroHistoria;
    }

    public Paciente nroHistoria(Integer nroHistoria) {
        this.setNroHistoria(nroHistoria);
        return this;
    }

    public void setNroHistoria(Integer nroHistoria) {
        this.nroHistoria = nroHistoria;
    }

    public String getNombreRepresentante() {
        return this.nombreRepresentante;
    }

    public Paciente nombreRepresentante(String nombreRepresentante) {
        this.setNombreRepresentante(nombreRepresentante);
        return this;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getParentescoRepresentante() {
        return this.parentescoRepresentante;
    }

    public Paciente parentescoRepresentante(String parentescoRepresentante) {
        this.setParentescoRepresentante(parentescoRepresentante);
        return this;
    }

    public void setParentescoRepresentante(String parentescoRepresentante) {
        this.parentescoRepresentante = parentescoRepresentante;
    }

    public String getCorreoRepresentante() {
        return this.correoRepresentante;
    }

    public Paciente correoRepresentante(String correoRepresentante) {
        this.setCorreoRepresentante(correoRepresentante);
        return this;
    }

    public void setCorreoRepresentante(String correoRepresentante) {
        this.correoRepresentante = correoRepresentante;
    }

    public String getCelularRepresentante() {
        return this.celularRepresentante;
    }

    public Paciente celularRepresentante(String celularRepresentante) {
        this.setCelularRepresentante(celularRepresentante);
        return this;
    }

    public void setCelularRepresentante(String celularRepresentante) {
        this.celularRepresentante = celularRepresentante;
    }

    public String getDireccionRepresentante() {
        return this.direccionRepresentante;
    }

    public Paciente direccionRepresentante(String direccionRepresentante) {
        this.setDireccionRepresentante(direccionRepresentante);
        return this;
    }

    public void setDireccionRepresentante(String direccionRepresentante) {
        this.direccionRepresentante = direccionRepresentante;
    }

    public Sujeto getSujeto() {
        return this.sujeto;
    }

    public void setSujeto(Sujeto sujeto) {
        this.sujeto = sujeto;
    }

    public Paciente sujeto(Sujeto sujeto) {
        this.setSujeto(sujeto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }
        return getId() != null && getId().equals(((Paciente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + getId() +
            ", nroHistoria=" + getNroHistoria() +
            ", nombreRepresentante='" + getNombreRepresentante() + "'" +
            ", parentescoRepresentante='" + getParentescoRepresentante() + "'" +
            ", correoRepresentante='" + getCorreoRepresentante() + "'" +
            ", celularRepresentante='" + getCelularRepresentante() + "'" +
            ", direccionRepresentante='" + getDireccionRepresentante() + "'" +
            "}";
    }
}
