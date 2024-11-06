package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContactoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Contacto getContactoSample1() {
        return new Contacto().id(1L).telefono("telefono1").correo("correo1").codigoPais("codigoPais1").celular("celular1");
    }

    public static Contacto getContactoSample2() {
        return new Contacto().id(2L).telefono("telefono2").correo("correo2").codigoPais("codigoPais2").celular("celular2");
    }

    public static Contacto getContactoRandomSampleGenerator() {
        return new Contacto()
            .id(longCount.incrementAndGet())
            .telefono(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString())
            .codigoPais(UUID.randomUUID().toString())
            .celular(UUID.randomUUID().toString());
    }
}
