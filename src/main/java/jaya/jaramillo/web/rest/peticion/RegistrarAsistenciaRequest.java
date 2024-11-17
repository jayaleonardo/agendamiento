package jaya.jaramillo.web.rest.peticion;

public class RegistrarAsistenciaRequest {

    private Long citaId;
    private String tares;

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }

    public String getTares() {
        return tares;
    }

    public void setTares(String tares) {
        this.tares = tares;
    }
}
