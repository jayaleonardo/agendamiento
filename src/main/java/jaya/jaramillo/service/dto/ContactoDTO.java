package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Contacto} entity.
 */
@Schema(description = "The Contacto entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactoDTO implements Serializable {

    private Long id;

    @Schema(description = "teléfono de contacto")
    private String telefono;

    @NotNull
    @Schema(description = "correo de contacto", requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @NotNull
    @Schema(description = "código de país", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoPais;

    @Schema(description = "celular de contacto")
    private String celular;

    @Schema(description = "Contacto relacionado con Sujeto")
    private SujetoDTO sujeto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
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
        if (!(o instanceof ContactoDTO)) {
            return false;
        }

        ContactoDTO contactoDTO = (ContactoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contactoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactoDTO{" +
            "id=" + getId() +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", codigoPais='" + getCodigoPais() + "'" +
            ", celular='" + getCelular() + "'" +
            ", sujeto=" + getSujeto() +
            "}";
    }
}
