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
        value = "select prog.id, " +
        "   prog.fecha as fecha, " +
        "   to_char(prog.desde, 'HH24:MI') as horainicio, " +
        "   to_char(prog.hasta, 'HH24:MI') as horariofin, " +
        //"   to_char(c.hora_inicio + (c.duracion_minutos||' minutes')::interval, 'HH24:MI' ) as horariofin, " +
        "   c.duracion_minutos as duracion, " +
        "   esp.nro_consultorio  as consultorio,    " +
        "   suj.apellido||' '||suj.segundo_apellido ||' '||suj.nombre ||' '||suj.segundo_nombre as paciente, " +
        "   coalesce(c.estado, 'Disponible') as estado, " +
        "   psicologo.apellido||' '||psicologo.segundo_apellido ||' '||psicologo.nombre ||' '||psicologo.segundo_nombre as profesional, " +
        "   esp.especialidad, " +
        "   c.id as citaid, " +
        "   c.informacion_reserva as inforeserva " +
        "from Programacion prog " +
        "   join horario_consulta horario on horario.id = prog.horario_consulta_id " +
        "   join especialista esp on esp.id = horario.especialista_id " +
        "   join sujeto psicologo on psicologo.id = esp.sujeto_id " +
        "   left join cita c on c.programacion_id = prog.id " +
        "   left join paciente pac on pac.id = c.paciente_id " +
        "   left join sujeto suj on suj.id = pac.sujeto_id  " +
        "WHERE 1=1 " +
        "   and prog.fecha between :desde and :hasta " +
        "   and ( :especialidad is null or esp.especialidad =:especialidad) " +
        "   and ( :especialistaId is null or horario.especialista_id =:especialistaId) " +
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
