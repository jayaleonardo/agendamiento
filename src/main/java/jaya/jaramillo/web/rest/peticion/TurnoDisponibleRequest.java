package jaya.jaramillo.web.rest.peticion;

import java.time.LocalDate;

public class TurnoDisponibleRequest {

    private LocalDate fecha;
    private Long especialistaId;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(Long especialistaId) {
        this.especialistaId = especialistaId;
    }
}
