package jaya.jaramillo.repository;

import jaya.jaramillo.domain.Programacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Programacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramacionRepository extends JpaRepository<Programacion, Long> {}
