package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Especialista;
import jaya.jaramillo.domain.Sujeto;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.SujetoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Especialista} and its DTO {@link EspecialistaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EspecialistaMapper extends EntityMapper<EspecialistaDTO, Especialista> {
    @Mapping(target = "sujeto", source = "sujeto", qualifiedByName = "sujetoId")
    EspecialistaDTO toDto(Especialista s);

    @Named("sujetoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SujetoDTO toDtoSujetoId(Sujeto sujeto);
}
