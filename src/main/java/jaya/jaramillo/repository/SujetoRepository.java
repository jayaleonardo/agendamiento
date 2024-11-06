package jaya.jaramillo.repository;

import jaya.jaramillo.domain.Sujeto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sujeto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SujetoRepository extends JpaRepository<Sujeto, Long> {}
