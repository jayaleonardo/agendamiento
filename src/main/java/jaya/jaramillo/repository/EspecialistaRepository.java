package jaya.jaramillo.repository;

import java.util.List;
import jaya.jaramillo.domain.Especialista;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Especialista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecialistaRepository extends JpaRepository<Especialista, Long> {
    @Query("Select distinct(e.especialidad) from Especialista e order by e.especialidad asc")
    List<String> obtenerEspecialidades();

    @Query("Select e from Especialista e where e.especialidad=:especialidad")
    List<Especialista> especialistasPorEspecialidad(@Param("especialidad") String especialidad);
}
