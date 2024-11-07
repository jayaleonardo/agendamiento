package jaya.jaramillo.web.rest.peticion;

import java.time.LocalDate;

public class BuscarTurnoRequest {

    private Long especialistaId;
    private LocalDate desde;
    private LocalDate hasta;

    public Long getEspecialistaId() {
        return especialistaId;
    }

    public void setEspecialistaId(Long especialistaId) {
        this.especialistaId = especialistaId;
    }

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

    @Override
    public String toString() {
        return "BuscarTurnoRequest [especialistaId=" + especialistaId + ", desde=" + desde + ", hasta=" + hasta + "]";
    }
}
