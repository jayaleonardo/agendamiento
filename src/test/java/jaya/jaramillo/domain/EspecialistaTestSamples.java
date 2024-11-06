package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EspecialistaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Especialista getEspecialistaSample1() {
        return new Especialista()
            .id(1L)
            .especialidad("especialidad1")
            .codigoDoctor("codigoDoctor1")
            .nroConsultorio("nroConsultorio1")
            .fotoUrl("fotoUrl1");
    }

    public static Especialista getEspecialistaSample2() {
        return new Especialista()
            .id(2L)
            .especialidad("especialidad2")
            .codigoDoctor("codigoDoctor2")
            .nroConsultorio("nroConsultorio2")
            .fotoUrl("fotoUrl2");
    }

    public static Especialista getEspecialistaRandomSampleGenerator() {
        return new Especialista()
            .id(longCount.incrementAndGet())
            .especialidad(UUID.randomUUID().toString())
            .codigoDoctor(UUID.randomUUID().toString())
            .nroConsultorio(UUID.randomUUID().toString())
            .fotoUrl(UUID.randomUUID().toString());
    }
}
