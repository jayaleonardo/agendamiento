package jaya.jaramillo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ProgramacionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Programacion getProgramacionSample1() {
        return new Programacion().id(1L);
    }

    public static Programacion getProgramacionSample2() {
        return new Programacion().id(2L);
    }

    public static Programacion getProgramacionRandomSampleGenerator() {
        return new Programacion().id(longCount.incrementAndGet());
    }
}
