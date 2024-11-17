package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CitaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Cita getCitaSample1() {
        return new Cita()
            .id(1L)
            .duracionMinutos(1)
            .estado("estado1")
            .tipoVisita("tipoVisita1")
            .canalAtencion("canalAtencion1")
            .observacion("observacion1")
            .recordatorio("recordatorio1")
            .motivoConsulta("motivoConsulta1")
            .detalleConsultaVirtual("detalleConsultaVirtual1")
            .informacionReserva("informacionReserva1")
            .tarea("tarea1");
    }

    public static Cita getCitaSample2() {
        return new Cita()
            .id(2L)
            .duracionMinutos(2)
            .estado("estado2")
            .tipoVisita("tipoVisita2")
            .canalAtencion("canalAtencion2")
            .observacion("observacion2")
            .recordatorio("recordatorio2")
            .motivoConsulta("motivoConsulta2")
            .detalleConsultaVirtual("detalleConsultaVirtual2")
            .informacionReserva("informacionReserva2")
            .tarea("tarea2");
    }

    public static Cita getCitaRandomSampleGenerator() {
        return new Cita()
            .id(longCount.incrementAndGet())
            .duracionMinutos(intCount.incrementAndGet())
            .estado(UUID.randomUUID().toString())
            .tipoVisita(UUID.randomUUID().toString())
            .canalAtencion(UUID.randomUUID().toString())
            .observacion(UUID.randomUUID().toString())
            .recordatorio(UUID.randomUUID().toString())
            .motivoConsulta(UUID.randomUUID().toString())
            .detalleConsultaVirtual(UUID.randomUUID().toString())
            .informacionReserva(UUID.randomUUID().toString())
            .tarea(UUID.randomUUID().toString());
    }
}
