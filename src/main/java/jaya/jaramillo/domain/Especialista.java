package jaya.jaramillo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Especialista entity.
 */
@Entity
@Table(name = "especialista")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Especialista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * especialidad del especialista
     */
    @NotNull
    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    /**
     * código del doctor
     */
    @NotNull
    @Column(name = "codigo_doctor", nullable = false)
    private String codigoDoctor;

    /**
     * número de consultorio
     */
    @NotNull
    @Column(name = "nro_consultorio", nullable = false)
    private String nroConsultorio;

    /**
     * foto del especialista
     */
    @Column(name = "foto_url")
    private String fotoUrl;

    /**
     * Especialista relacionado con Sujeto
     */
    @JsonIgnoreProperties(value = { "contacto", "direccion", "especialista", "paciente" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Sujeto sujeto;

    /**
     * Especialista y sus horarios de consulta
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "especialista")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "programacions", "especialista" }, allowSetters = true)
    private Set<HorarioConsulta> horarioConsultas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Especialista id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public Especialista especialidad(String especialidad) {
        this.setEspecialidad(especialidad);
        return this;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodigoDoctor() {
        return this.codigoDoctor;
    }

    public Especialista codigoDoctor(String codigoDoctor) {
        this.setCodigoDoctor(codigoDoctor);
        return this;
    }

    public void setCodigoDoctor(String codigoDoctor) {
        this.codigoDoctor = codigoDoctor;
    }

    public String getNroConsultorio() {
        return this.nroConsultorio;
    }

    public Especialista nroConsultorio(String nroConsultorio) {
        this.setNroConsultorio(nroConsultorio);
        return this;
    }

    public void setNroConsultorio(String nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public String getFotoUrl() {
        return this.fotoUrl;
    }

    public Especialista fotoUrl(String fotoUrl) {
        this.setFotoUrl(fotoUrl);
        return this;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Sujeto getSujeto() {
        return this.sujeto;
    }

    public void setSujeto(Sujeto sujeto) {
        this.sujeto = sujeto;
    }

    public Especialista sujeto(Sujeto sujeto) {
        this.setSujeto(sujeto);
        return this;
    }

    public Set<HorarioConsulta> getHorarioConsultas() {
        return this.horarioConsultas;
    }

    public void setHorarioConsultas(Set<HorarioConsulta> horarioConsultas) {
        if (this.horarioConsultas != null) {
            this.horarioConsultas.forEach(i -> i.setEspecialista(null));
        }
        if (horarioConsultas != null) {
            horarioConsultas.forEach(i -> i.setEspecialista(this));
        }
        this.horarioConsultas = horarioConsultas;
    }

    public Especialista horarioConsultas(Set<HorarioConsulta> horarioConsultas) {
        this.setHorarioConsultas(horarioConsultas);
        return this;
    }

    public Especialista addHorarioConsulta(HorarioConsulta horarioConsulta) {
        this.horarioConsultas.add(horarioConsulta);
        horarioConsulta.setEspecialista(this);
        return this;
    }

    public Especialista removeHorarioConsulta(HorarioConsulta horarioConsulta) {
        this.horarioConsultas.remove(horarioConsulta);
        horarioConsulta.setEspecialista(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Especialista)) {
            return false;
        }
        return getId() != null && getId().equals(((Especialista) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Especialista{" +
            "id=" + getId() +
            ", especialidad='" + getEspecialidad() + "'" +
            ", codigoDoctor='" + getCodigoDoctor() + "'" +
            ", nroConsultorio='" + getNroConsultorio() + "'" +
            ", fotoUrl='" + getFotoUrl() + "'" +
            "}";
    }
}
