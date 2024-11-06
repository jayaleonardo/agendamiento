package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SujetoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sujeto getSujetoSample1() {
        return new Sujeto()
            .id(1L)
            .nombre("nombre1")
            .segundoNombre("segundoNombre1")
            .apellido("apellido1")
            .segundoApellido("segundoApellido1")
            .documentoIdentidad("documentoIdentidad1")
            .estado("estado1")
            .sexo("sexo1");
    }

    public static Sujeto getSujetoSample2() {
        return new Sujeto()
            .id(2L)
            .nombre("nombre2")
            .segundoNombre("segundoNombre2")
            .apellido("apellido2")
            .segundoApellido("segundoApellido2")
            .documentoIdentidad("documentoIdentidad2")
            .estado("estado2")
            .sexo("sexo2");
    }

    public static Sujeto getSujetoRandomSampleGenerator() {
        return new Sujeto()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .segundoNombre(UUID.randomUUID().toString())
            .apellido(UUID.randomUUID().toString())
            .segundoApellido(UUID.randomUUID().toString())
            .documentoIdentidad(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString())
            .sexo(UUID.randomUUID().toString());
    }
}
