package jaya.jaramillo.repository;

import jaya.jaramillo.domain.HorarioConsulta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HorarioConsulta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HorarioConsultaRepository extends JpaRepository<HorarioConsulta, Long> {}
