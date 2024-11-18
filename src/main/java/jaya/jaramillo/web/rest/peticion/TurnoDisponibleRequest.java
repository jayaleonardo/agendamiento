package jaya.jaramillo.web.rest.peticion;

import java.time.LocalDate;

public class TurnoDisponibleRequest {

    private LocalDate fecha;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
