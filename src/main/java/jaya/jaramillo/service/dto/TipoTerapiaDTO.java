package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.TipoTerapia} entity.
 */
@Schema(description = "The TipoTerapia entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoTerapiaDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "descripción del tipo de terapia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoTerapiaDTO)) {
            return false;
        }

        TipoTerapiaDTO tipoTerapiaDTO = (TipoTerapiaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoTerapiaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoTerapiaDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
