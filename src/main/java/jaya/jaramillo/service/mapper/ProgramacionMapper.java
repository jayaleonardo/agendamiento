package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.HorarioConsulta;
import jaya.jaramillo.domain.Programacion;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import jaya.jaramillo.service.dto.ProgramacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Programacion} and its DTO {@link ProgramacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProgramacionMapper extends EntityMapper<ProgramacionDTO, Programacion> {
    @Mapping(target = "horarioConsulta", source = "horarioConsulta", qualifiedByName = "horarioConsultaId")
    ProgramacionDTO toDto(Programacion s);

    @Named("horarioConsultaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HorarioConsultaDTO toDtoHorarioConsultaId(HorarioConsulta horarioConsulta);
}
