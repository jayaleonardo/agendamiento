package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClinicaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Clinica getClinicaSample1() {
        return new Clinica().id(1L).nombre("nombre1").telefono("telefono1").correo("correo1").horarioAtencion("horarioAtencion1");
    }

    public static Clinica getClinicaSample2() {
        return new Clinica().id(2L).nombre("nombre2").telefono("telefono2").correo("correo2").horarioAtencion("horarioAtencion2");
    }

    public static Clinica getClinicaRandomSampleGenerator() {
        return new Clinica()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString())
            .horarioAtencion(UUID.randomUUID().toString());
    }
}
