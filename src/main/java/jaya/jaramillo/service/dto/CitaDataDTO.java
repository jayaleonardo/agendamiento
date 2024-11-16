package jaya.jaramillo.service.dto;

import java.time.LocalDate;

public interface CitaDataDTO {
    Long getId();
    LocalDate getFecha();
    String getHorainicio();
    String getHorarioFin();
    Integer getDuracion();
    String getConsultorio();
    String getPaciente();
    String getEstado();
    String getProfesional();
    String getEspecialidad();
    String getCitaid();
    String getInforeserva();
}
