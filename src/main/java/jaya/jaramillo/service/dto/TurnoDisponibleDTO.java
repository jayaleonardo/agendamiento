package jaya.jaramillo.service.dto;

import java.time.LocalDate;

public interface TurnoDisponibleDTO {
    Long getId();
    LocalDate getFecha();
    String getDesde();
    String getHasta();
}
