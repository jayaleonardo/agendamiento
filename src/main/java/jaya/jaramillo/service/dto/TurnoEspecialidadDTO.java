package jaya.jaramillo.service.dto;

import java.time.Instant;
import java.time.LocalDate;

public interface TurnoEspecialidadDTO {
    Long getProgramacionid();
    LocalDate getFecha();
    Instant getDesde();
    Instant getHasta();
    String getEspecialidad();
    String getNroconsultorio();
    String getEspecialista();
}
