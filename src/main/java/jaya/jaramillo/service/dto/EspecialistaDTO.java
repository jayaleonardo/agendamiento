package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Especialista} entity.
 */
@Schema(description = "The Especialista entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EspecialistaDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "especialidad del especialista", requiredMode = Schema.RequiredMode.REQUIRED)
    private String especialidad;

    @NotNull
    @Schema(description = "código del doctor", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoDoctor;

    @NotNull
    @Schema(description = "número de consultorio", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nroConsultorio;

    @Schema(description = "foto del especialista")
    private String fotoUrl;

    @Schema(description = "Especialista relacionado con Sujeto")
    private SujetoDTO sujeto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getCodigoDoctor() {
        return codigoDoctor;
    }

    public void setCodigoDoctor(String codigoDoctor) {
        this.codigoDoctor = codigoDoctor;
    }

    public String getNroConsultorio() {
        return nroConsultorio;
    }

    public void setNroConsultorio(String nroConsultorio) {
        this.nroConsultorio = nroConsultorio;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
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
        if (!(o instanceof EspecialistaDTO)) {
            return false;
        }

        EspecialistaDTO especialistaDTO = (EspecialistaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, especialistaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EspecialistaDTO{" +
            "id=" + getId() +
            ", especialidad='" + getEspecialidad() + "'" +
            ", codigoDoctor='" + getCodigoDoctor() + "'" +
            ", nroConsultorio='" + getNroConsultorio() + "'" +
            ", fotoUrl='" + getFotoUrl() + "'" +
            ", sujeto=" + getSujeto() +
            "}";
    }
}
