package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DireccionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Direccion getDireccionSample1() {
        return new Direccion()
            .id(1L)
            .idDireccion(1)
            .pais("pais1")
            .provincia("provincia1")
            .ciudad("ciudad1")
            .calles("calles1")
            .nroDomicilio("nroDomicilio1");
    }

    public static Direccion getDireccionSample2() {
        return new Direccion()
            .id(2L)
            .idDireccion(2)
            .pais("pais2")
            .provincia("provincia2")
            .ciudad("ciudad2")
            .calles("calles2")
            .nroDomicilio("nroDomicilio2");
    }

    public static Direccion getDireccionRandomSampleGenerator() {
        return new Direccion()
            .id(longCount.incrementAndGet())
            .idDireccion(intCount.incrementAndGet())
            .pais(UUID.randomUUID().toString())
            .provincia(UUID.randomUUID().toString())
            .ciudad(UUID.randomUUID().toString())
            .calles(UUID.randomUUID().toString())
            .nroDomicilio(UUID.randomUUID().toString());
    }
}
