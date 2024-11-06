package jaya.jaramillo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Sujeto entity.
 */
@Entity
@Table(name = "sujeto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sujeto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * nombre del sujeto
     */
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * segundo nombre del sujeto
     */
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    /**
     * apellido del sujeto
     */
    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    /**
     * segundo apellido del sujeto
     */
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    /**
     * documento de identidad del sujeto
     */
    @NotNull
    @Column(name = "documento_identidad", nullable = false)
    private String documentoIdentidad;

    /**
     * estado del sujeto
     */
    @Column(name = "estado")
    private String estado;

    /**
     * sexo del sujeto
     */
    @Column(name = "sexo")
    private String sexo;

    /**
     * fecha de nacimiento del sujeto
     */
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @JsonIgnoreProperties(value = { "sujeto" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sujeto")
    private Contacto contacto;

    @JsonIgnoreProperties(value = { "sujeto" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sujeto")
    private Direccion direccion;

    @JsonIgnoreProperties(value = { "sujeto", "horarioConsultas" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sujeto")
    private Especialista especialista;

    @JsonIgnoreProperties(value = { "sujeto" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "sujeto")
    private Paciente paciente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sujeto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Sujeto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundoNombre() {
        return this.segundoNombre;
    }

    public Sujeto segundoNombre(String segundoNombre) {
        this.setSegundoNombre(segundoNombre);
        return this;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public Sujeto apellido(String apellido) {
        this.setApellido(apellido);
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public Sujeto segundoApellido(String segundoApellido) {
        this.setSegundoApellido(segundoApellido);
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDocumentoIdentidad() {
        return this.documentoIdentidad;
    }

    public Sujeto documentoIdentidad(String documentoIdentidad) {
        this.setDocumentoIdentidad(documentoIdentidad);
        return this;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getEstado() {
        return this.estado;
    }

    public Sujeto estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSexo() {
        return this.sexo;
    }

    public Sujeto sexo(String sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public Sujeto fechaNacimiento(LocalDate fechaNacimiento) {
        this.setFechaNacimiento(fechaNacimiento);
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Contacto getContacto() {
        return this.contacto;
    }

    public void setContacto(Contacto contacto) {
        if (this.contacto != null) {
            this.contacto.setSujeto(null);
        }
        if (contacto != null) {
            contacto.setSujeto(this);
        }
        this.contacto = contacto;
    }

    public Sujeto contacto(Contacto contacto) {
        this.setContacto(contacto);
        return this;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public void setDireccion(Direccion direccion) {
        if (this.direccion != null) {
            this.direccion.setSujeto(null);
        }
        if (direccion != null) {
            direccion.setSujeto(this);
        }
        this.direccion = direccion;
    }

    public Sujeto direccion(Direccion direccion) {
        this.setDireccion(direccion);
        return this;
    }

    public Especialista getEspecialista() {
        return this.especialista;
    }

    public void setEspecialista(Especialista especialista) {
        if (this.especialista != null) {
            this.especialista.setSujeto(null);
        }
        if (especialista != null) {
            especialista.setSujeto(this);
        }
        this.especialista = especialista;
    }

    public Sujeto especialista(Especialista especialista) {
        this.setEspecialista(especialista);
        return this;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (this.paciente != null) {
            this.paciente.setSujeto(null);
        }
        if (paciente != null) {
            paciente.setSujeto(this);
        }
        this.paciente = paciente;
    }

    public Sujeto paciente(Paciente paciente) {
        this.setPaciente(paciente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sujeto)) {
            return false;
        }
        return getId() != null && getId().equals(((Sujeto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sujeto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", documentoIdentidad='" + getDocumentoIdentidad() + "'" +
            ", estado='" + getEstado() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            "}";
    }
}
