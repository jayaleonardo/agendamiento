package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Paciente;
import jaya.jaramillo.domain.Sujeto;
import jaya.jaramillo.service.dto.PacienteDTO;
import jaya.jaramillo.service.dto.SujetoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paciente} and its DTO {@link PacienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {
    @Mapping(target = "sujeto", source = "sujeto", qualifiedByName = "sujetoId")
    PacienteDTO toDto(Paciente s);

    @Named("sujetoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "apellido", source = "apellido")
    @Mapping(target = "segundoApellido", source = "segundoApellido")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "segundoNombre", source = "segundoNombre")
    SujetoDTO toDtoSujetoId(Sujeto sujeto);
}
