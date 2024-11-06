package jaya.jaramillo.repository;

import jaya.jaramillo.domain.Clinica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Clinica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {}
