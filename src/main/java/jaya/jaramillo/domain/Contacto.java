package jaya.jaramillo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Contacto entity.
 */
@Entity
@Table(name = "contacto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * teléfono de contacto
     */
    @Column(name = "telefono")
    private String telefono;

    /**
     * correo de contacto
     */
    @NotNull
    @Column(name = "correo", nullable = false)
    private String correo;

    /**
     * código de país
     */
    @NotNull
    @Column(name = "codigo_pais", nullable = false)
    private String codigoPais;

    /**
     * celular de contacto
     */
    @Column(name = "celular")
    private String celular;

    /**
     * Contacto relacionado con Sujeto
     */
    @JsonIgnoreProperties(value = { "contacto", "direccion", "especialista", "paciente" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Sujeto sujeto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contacto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Contacto telefono(String telefono) {
        this.setTelefono(telefono);
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public Contacto correo(String correo) {
        this.setCorreo(correo);
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigoPais() {
        return this.codigoPais;
    }

    public Contacto codigoPais(String codigoPais) {
        this.setCodigoPais(codigoPais);
        return this;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getCelular() {
        return this.celular;
    }

    public Contacto celular(String celular) {
        this.setCelular(celular);
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Sujeto getSujeto() {
        return this.sujeto;
    }

    public void setSujeto(Sujeto sujeto) {
        this.sujeto = sujeto;
    }

    public Contacto sujeto(Sujeto sujeto) {
        this.setSujeto(sujeto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contacto)) {
            return false;
        }
        return getId() != null && getId().equals(((Contacto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contacto{" +
            "id=" + getId() +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", codigoPais='" + getCodigoPais() + "'" +
            ", celular='" + getCelular() + "'" +
            "}";
    }
}
