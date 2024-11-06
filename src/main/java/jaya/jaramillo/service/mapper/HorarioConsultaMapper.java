package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Especialista;
import jaya.jaramillo.domain.HorarioConsulta;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HorarioConsulta} and its DTO {@link HorarioConsultaDTO}.
 */
@Mapper(componentModel = "spring")
public interface HorarioConsultaMapper extends EntityMapper<HorarioConsultaDTO, HorarioConsulta> {
    @Mapping(target = "especialista", source = "especialista", qualifiedByName = "especialistaId")
    HorarioConsultaDTO toDto(HorarioConsulta s);

    @Named("especialistaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialistaDTO toDtoEspecialistaId(Especialista especialista);
}
