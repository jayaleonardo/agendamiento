package jaya.jaramillo.web.rest.peticion;

import java.time.LocalDate;
import java.util.Arrays;

public class CrearProgramacionRequest {

    private LocalDate desde;
    private LocalDate hasta;
    private String horaInicio;
    private String horaFin;
    private String almuerzoDesde;
    private String almuerzoHasta;
    private String diasSemana;
    private Integer cantidad;
    private Long especialistaId;
    private Integer duracion;

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getAlmuerzoDesde() {
        return almuerzoDesde;
    }

    public void setAlmuerzoDesde(String almuerzoDesde) {
        this.almuerzoDesde = almuerzoDesde;
    }

    public String getAlmuerzoHasta() {
        return almuerzoHasta;
    }

    public void setAlmuerzoHasta(String almuerzoHasta) {
        this.almuerzoHasta = almuerzoHasta;
    }

    public String getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(String diasSemana) {
        this.diasSemana = diasSemana;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(Long especialistaId) {
        this.especialistaId = especialistaId;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
}
