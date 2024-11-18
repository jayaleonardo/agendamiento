package jaya.jaramillo.web.rest.peticion;

public class PreReservaRequest {

    private String apellido;
    private String segundoApellido;
    private String nombre;
    private String segundoNombre;
    private String celular;
    private Boolean virtual;
    private Long turnoId;

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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
    }

    @Override
    public String toString() {
        return (
            "PreReservaRequest [apellido=" +
            apellido +
            ", segundoApellido=" +
            segundoApellido +
            ", nombre=" +
            nombre +
            ", segundoNombre=" +
            segundoNombre +
            ", celular=" +
            celular +
            ", virtual=" +
            virtual +
            ", turnoId=" +
            turnoId +
            "]"
        );
    }
}
