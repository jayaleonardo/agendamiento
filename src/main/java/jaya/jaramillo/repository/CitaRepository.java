package jaya.jaramillo.repository;

import java.time.LocalDate;
import java.util.List;
import jaya.jaramillo.domain.Cita;
import jaya.jaramillo.service.dto.CitaDataDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cita entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query(
        value = "select c.id, " +
        "   c.fecha_cita as fecha, " +
        "   to_char(c.hora_inicio, 'HH24:MI:SS') as horainicio, " +
        "   to_char(c.hora_inicio + (c.duracion_minutos||' minutes')::interval, 'HH24:MI:SS' ) as horariofin, " +
        "   c.duracion_minutos as duracion, " +
        "   esp.nro_consultorio  as consultorio,    " +
        "   suj.apellido||' '||suj.segundo_apellido ||' '||suj.nombre ||' '||suj.segundo_nombre as paciente, " +
        "   c.estado " +
        "from cita c " +
        "   join especialista esp on esp.id = c.especialista_id " +
        "   join paciente pac on pac.id = c.paciente_id " +
        "   join sujeto suj on suj.id = pac.sujeto_id  " +
        "WHERE 1=1 " +
        "   and c.fecha_cita between :desde and :hasta " +
        "   and ( :especialidad is null or esp.especialidad =:especialidad) " +
        "   and ( :especialistaId is null or c.especialista_id =:especialistaId) " +
        "   and ( :estado is null or c.estado=:estado) " +
        "   and ( :criterio is null or suj.documento_identidad=:criterio)",
        nativeQuery = true
    )
    List<CitaDataDTO> obtenerCitas(
        LocalDate desde,
        LocalDate hasta,
        String especialidad,
        Long especialistaId,
        String estado,
        String criterio
    );
}
