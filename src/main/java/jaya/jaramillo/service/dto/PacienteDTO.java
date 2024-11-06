package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Paciente} entity.
 */
@Schema(description = "The Paciente entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PacienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "número de historia clínica", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer nroHistoria;

    @Schema(description = "nombre del representante")
    private String nombreRepresentante;

    @Schema(description = "parentesco del representante")
    private String parentescoRepresentante;

    @Schema(description = "correo electrónico del representante")
    private String correoRepresentante;

    @Schema(description = "celular del representante")
    private String celularRepresentante;

    @Schema(description = "dirección del representante")
    private String direccionRepresentante;

    @Schema(description = "Paciente relacionado con Sujeto")
    private SujetoDTO sujeto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNroHistoria() {
        return nroHistoria;
    }

    public void setNroHistoria(Integer nroHistoria) {
        this.nroHistoria = nroHistoria;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public void setNombreRepresentante(String nombreRepresentante) {
        this.nombreRepresentante = nombreRepresentante;
    }

    public String getParentescoRepresentante() {
        return parentescoRepresentante;
    }

    public void setParentescoRepresentante(String parentescoRepresentante) {
        this.parentescoRepresentante = parentescoRepresentante;
    }

    public String getCorreoRepresentante() {
        return correoRepresentante;
    }

    public void setCorreoRepresentante(String correoRepresentante) {
        this.correoRepresentante = correoRepresentante;
    }

    public String getCelularRepresentante() {
        return celularRepresentante;
    }

    public void setCelularRepresentante(String celularRepresentante) {
        this.celularRepresentante = celularRepresentante;
    }

    public String getDireccionRepresentante() {
        return direccionRepresentante;
    }

    public void setDireccionRepresentante(String direccionRepresentante) {
        this.direccionRepresentante = direccionRepresentante;
    }

    public SujetoDTO getSujeto() {
        return sujeto;
    }

    public void setSujeto(SujetoDTO sujeto) {
        this.sujeto = sujeto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PacienteDTO)) {
            return false;
        }

        PacienteDTO pacienteDTO = (PacienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pacienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PacienteDTO{" +
            "id=" + getId() +
            ", nroHistoria=" + getNroHistoria() +
            ", nombreRepresentante='" + getNombreRepresentante() + "'" +
            ", parentescoRepresentante='" + getParentescoRepresentante() + "'" +
            ", correoRepresentante='" + getCorreoRepresentante() + "'" +
            ", celularRepresentante='" + getCelularRepresentante() + "'" +
            ", direccionRepresentante='" + getDireccionRepresentante() + "'" +
            ", sujeto=" + getSujeto() +
            "}";
    }
}
