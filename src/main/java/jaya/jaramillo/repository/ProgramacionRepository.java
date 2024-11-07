package jaya.jaramillo.repository;

import java.time.LocalDate;
import java.util.List;
import jaya.jaramillo.domain.Programacion;
import jaya.jaramillo.service.dto.TurnoEspecialidadDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Programacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramacionRepository extends JpaRepository<Programacion, Long> {
    @Query(
        value = "SELECT  prog.id as programacionid, prog.fecha as fecha, prog.desde, " +
        "   prog.hasta, esp.especialidad, esp.nro_consultorio as nroconsultorio, " +
        "   suj.apellido||' '||suj.segundo_apellido||' '||suj.nombre||' '||suj.segundo_nombre as especialista " +
        "FROM programacion prog " +
        "   inner join horario_consulta hcon on hcon.id = prog.horario_consulta_id " +
        "   inner join especialista esp on esp.id = hcon.especialista_id " +
        "   inner join sujeto suj on suj.id = esp.sujeto_id " +
        "WHERE hcon.especialista_id=:especialistaId " +
        "   and prog.fecha between :desde and :hasta " +
        "ORDER BY prog.fecha, prog.desde",
        nativeQuery = true
    )
    List<TurnoEspecialidadDTO> obtenerTurnos(
        @Param("especialistaId") Long especialistaId,
        @Param("desde") LocalDate desde,
        @Param("hasta") LocalDate hasta
    );
}
