package jaya.jaramillo.web.rest.peticion;

import java.time.LocalDate;
import java.util.Arrays;

public class CrearProgramacionRequest {

    private LocalDate desde;
    private LocalDate hasta;
    private String horaInicio;
    private String horaFin;
    private String almuerzoInicio;
    private String almuerzoFin;
    private int[] diaSemana;

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

    public String getAlmuerzoInicio() {
        return almuerzoInicio;
    }

    public void setAlmuerzoInicio(String almuerzoInicio) {
        this.almuerzoInicio = almuerzoInicio;
    }

    public String getAlmuerzoFin() {
        return almuerzoFin;
    }

    public void setAlmuerzoFin(String almuerzoFin) {
        this.almuerzoFin = almuerzoFin;
    }

    public int[] getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int[] diaSemana) {
        this.diaSemana = diaSemana;
    }

    @Override
    public String toString() {
        return (
            "CrearProgramacionRequest [desde=" +
            desde +
            ", hasta=" +
            hasta +
            ", horaInicio=" +
            horaInicio +
            ", horaFin=" +
            horaFin +
            ", almuerzoInicio=" +
            almuerzoInicio +
            ", almuerzoFin=" +
            almuerzoFin +
            ", diaSemana=" +
            Arrays.toString(diaSemana) +
            "]"
        );
    }
}
