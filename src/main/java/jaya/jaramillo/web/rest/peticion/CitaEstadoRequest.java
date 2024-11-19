package jaya.jaramillo.web.rest.peticion;

public class CitaEstadoRequest {

    private Long citaId;
    private String estado;

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
