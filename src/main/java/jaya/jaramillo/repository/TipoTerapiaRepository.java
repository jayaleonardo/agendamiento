package jaya.jaramillo.repository;

import jaya.jaramillo.domain.TipoTerapia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoTerapia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoTerapiaRepository extends JpaRepository<TipoTerapia, Long> {}
