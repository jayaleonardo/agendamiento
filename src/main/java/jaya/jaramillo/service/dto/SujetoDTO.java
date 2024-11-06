package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Sujeto} entity.
 */
@Schema(description = "The Sujeto entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SujetoDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "nombre del sujeto", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "segundo nombre del sujeto")
    private String segundoNombre;

    @NotNull
    @Schema(description = "apellido del sujeto", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellido;

    @Schema(description = "segundo apellido del sujeto")
    private String segundoApellido;

    @NotNull
    @Schema(description = "documento de identidad del sujeto", requiredMode = Schema.RequiredMode.REQUIRED)
    private String documentoIdentidad;

    @Schema(description = "estado del sujeto")
    private String estado;

    @Schema(description = "sexo del sujeto")
    private String sexo;

    @Schema(description = "fecha de nacimiento del sujeto")
    private LocalDate fechaNacimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SujetoDTO)) {
            return false;
        }

        SujetoDTO sujetoDTO = (SujetoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sujetoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SujetoDTO{" +
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
