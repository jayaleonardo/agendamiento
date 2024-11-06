package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Clinica} entity.
 */
@Schema(description = "The Clinica entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClinicaDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "nombre de la clínica", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotNull
    @Schema(description = "teléfono de la clínica", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefono;

    @NotNull
    @Schema(description = "correo de la clínica", requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @NotNull
    @Schema(description = "horario de atención", requiredMode = Schema.RequiredMode.REQUIRED)
    private String horarioAtencion;

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClinicaDTO)) {
            return false;
        }

        ClinicaDTO clinicaDTO = (ClinicaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clinicaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClinicaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", horarioAtencion='" + getHorarioAtencion() + "'" +
            "}";
    }
}
