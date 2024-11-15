package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Cita;
import jaya.jaramillo.domain.Especialista;
import jaya.jaramillo.domain.Paciente;
import jaya.jaramillo.domain.Programacion;
import jaya.jaramillo.domain.TipoTerapia;
import jaya.jaramillo.service.dto.CitaDTO;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.PacienteDTO;
import jaya.jaramillo.service.dto.ProgramacionDTO;
import jaya.jaramillo.service.dto.TipoTerapiaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cita} and its DTO {@link CitaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CitaMapper extends EntityMapper<CitaDTO, Cita> {
    @Mapping(target = "especialista", source = "especialista", qualifiedByName = "especialistaId")
    @Mapping(target = "tipoTerapia", source = "tipoTerapia", qualifiedByName = "tipoTerapiaId")
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteId")
    @Mapping(target = "programacion", source = "programacion", qualifiedByName = "programacionId")
    CitaDTO toDto(Cita s);

    @Named("especialistaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EspecialistaDTO toDtoEspecialistaId(Especialista especialista);

    @Named("tipoTerapiaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoTerapiaDTO toDtoTipoTerapiaId(TipoTerapia tipoTerapia);

    @Named("pacienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PacienteDTO toDtoPacienteId(Paciente paciente);

    @Named("programacionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProgramacionDTO toDtoProgramacionId(Programacion programacion);
}
