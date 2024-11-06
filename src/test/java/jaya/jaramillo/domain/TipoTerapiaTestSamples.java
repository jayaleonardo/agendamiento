package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TipoTerapiaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TipoTerapia getTipoTerapiaSample1() {
        return new TipoTerapia().id(1L).descripcion("descripcion1");
    }

    public static TipoTerapia getTipoTerapiaSample2() {
        return new TipoTerapia().id(2L).descripcion("descripcion2");
    }

    public static TipoTerapia getTipoTerapiaRandomSampleGenerator() {
        return new TipoTerapia().id(longCount.incrementAndGet()).descripcion(UUID.randomUUID().toString());
    }
}
