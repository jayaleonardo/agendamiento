package jaya.jaramillo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HorarioConsultaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static HorarioConsulta getHorarioConsultaSample1() {
        return new HorarioConsulta().id(1L).duracionMinutos(1).diaSemana("diaSemana1").estado("estado1");
    }

    public static HorarioConsulta getHorarioConsultaSample2() {
        return new HorarioConsulta().id(2L).duracionMinutos(2).diaSemana("diaSemana2").estado("estado2");
    }

    public static HorarioConsulta getHorarioConsultaRandomSampleGenerator() {
        return new HorarioConsulta()
            .id(longCount.incrementAndGet())
            .duracionMinutos(intCount.incrementAndGet())
            .diaSemana(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString());
    }
}
