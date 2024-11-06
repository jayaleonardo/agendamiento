package jaya.jaramillo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Direccion entity.
 */
@Entity
@Table(name = "direccion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * identificador de dirección
     */
    @NotNull
    @Column(name = "id_direccion", nullable = false)
    private Integer idDireccion;

    /**
     * país de la dirección
     */
    @NotNull
    @Column(name = "pais", nullable = false)
    private String pais;

    /**
     * provincia de la dirección
     */
    @NotNull
    @Column(name = "provincia", nullable = false)
    private String provincia;

    /**
     * ciudad de la dirección
     */
    @NotNull
    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    /**
     * calles de la dirección
     */
    @NotNull
    @Column(name = "calles", nullable = false)
    private String calles;

    /**
     * número de domicilio
     */
    @Column(name = "nro_domicilio")
    private String nroDomicilio;

    /**
     * Direccion relacionada con Sujeto
     */
    @JsonIgnoreProperties(value = { "contacto", "direccion", "especialista", "paciente" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Sujeto sujeto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Direccion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDireccion() {
        return this.idDireccion;
    }

    public Direccion idDireccion(Integer idDireccion) {
        this.setIdDireccion(idDireccion);
        return this;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getPais() {
        return this.pais;
    }

    public Direccion pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public Direccion provincia(String provincia) {
        this.setProvincia(provincia);
        return this;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Direccion ciudad(String ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalles() {
        return this.calles;
    }

    public Direccion calles(String calles) {
        this.setCalles(calles);
        return this;
    }

    public void setCalles(String calles) {
        this.calles = calles;
    }

    public String getNroDomicilio() {
        return this.nroDomicilio;
    }

    public Direccion nroDomicilio(String nroDomicilio) {
        this.setNroDomicilio(nroDomicilio);
        return this;
    }

    public void setNroDomicilio(String nroDomicilio) {
        this.nroDomicilio = nroDomicilio;
    }

    public Sujeto getSujeto() {
        return this.sujeto;
    }

    public void setSujeto(Sujeto sujeto) {
        this.sujeto = sujeto;
    }

    public Direccion sujeto(Sujeto sujeto) {
        this.setSujeto(sujeto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direccion)) {
            return false;
        }
        return getId() != null && getId().equals(((Direccion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Direccion{" +
            "id=" + getId() +
            ", idDireccion=" + getIdDireccion() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", calles='" + getCalles() + "'" +
            ", nroDomicilio='" + getNroDomicilio() + "'" +
            "}";
    }
}
