package jaya.jaramillo.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link jaya.jaramillo.domain.Direccion} entity.
 */
@Schema(description = "The Direccion entity.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DireccionDTO implements Serializable {

    private Long id;

    @NotNull
    @Schema(description = "identificador de dirección", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idDireccion;

    @NotNull
    @Schema(description = "país de la dirección", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pais;

    @NotNull
    @Schema(description = "provincia de la dirección", requiredMode = Schema.RequiredMode.REQUIRED)
    private String provincia;

    @NotNull
    @Schema(description = "ciudad de la dirección", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ciudad;

    @NotNull
    @Schema(description = "calles de la dirección", requiredMode = Schema.RequiredMode.REQUIRED)
    private String calles;

    @Schema(description = "número de domicilio")
    private String nroDomicilio;

    @Schema(description = "Direccion relacionada con Sujeto")
    private SujetoDTO sujeto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Integer idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalles() {
        return calles;
    }

    public void setCalles(String calles) {
        this.calles = calles;
    }

    public String getNroDomicilio() {
        return nroDomicilio;
    }

    public void setNroDomicilio(String nroDomicilio) {
        this.nroDomicilio = nroDomicilio;
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
        if (!(o instanceof DireccionDTO)) {
            return false;
        }

        DireccionDTO direccionDTO = (DireccionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, direccionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DireccionDTO{" +
            "id=" + getId() +
            ", idDireccion=" + getIdDireccion() +
            ", pais='" + getPais() + "'" +
            ", provincia='" + getProvincia() + "'" +
            ", ciudad='" + getCiudad() + "'" +
            ", calles='" + getCalles() + "'" +
            ", nroDomicilio='" + getNroDomicilio() + "'" +
            ", sujeto=" + getSujeto() +
            "}";
    }
}
