package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PacienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Paciente getPacienteSample1() {
        return new Paciente()
            .id(1L)
            .nroHistoria(1)
            .nombreRepresentante("nombreRepresentante1")
            .parentescoRepresentante("parentescoRepresentante1")
            .correoRepresentante("correoRepresentante1")
            .celularRepresentante("celularRepresentante1")
            .direccionRepresentante("direccionRepresentante1");
    }

    public static Paciente getPacienteSample2() {
        return new Paciente()
            .id(2L)
            .nroHistoria(2)
            .nombreRepresentante("nombreRepresentante2")
            .parentescoRepresentante("parentescoRepresentante2")
            .correoRepresentante("correoRepresentante2")
            .celularRepresentante("celularRepresentante2")
            .direccionRepresentante("direccionRepresentante2");
    }

    public static Paciente getPacienteRandomSampleGenerator() {
        return new Paciente()
            .id(longCount.incrementAndGet())
            .nroHistoria(intCount.incrementAndGet())
            .nombreRepresentante(UUID.randomUUID().toString())
            .parentescoRepresentante(UUID.randomUUID().toString())
            .correoRepresentante(UUID.randomUUID().toString())
            .celularRepresentante(UUID.randomUUID().toString())
            .direccionRepresentante(UUID.randomUUID().toString());
    }
}
