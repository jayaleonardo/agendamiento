package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProgramacionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Programacion getProgramacionSample1() {
        return new Programacion().id(1L).duracionMinutos(1).diasSemana("diasSemana1");
    }

    public static Programacion getProgramacionSample2() {
        return new Programacion().id(2L).duracionMinutos(2).diasSemana("diasSemana2");
    }

    public static Programacion getProgramacionRandomSampleGenerator() {
        return new Programacion()
            .id(longCount.incrementAndGet())
            .duracionMinutos(intCount.incrementAndGet())
            .diasSemana(UUID.randomUUID().toString());
    }
}
