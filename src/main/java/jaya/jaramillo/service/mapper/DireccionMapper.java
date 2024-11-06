package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Direccion;
import jaya.jaramillo.domain.Sujeto;
import jaya.jaramillo.service.dto.DireccionDTO;
import jaya.jaramillo.service.dto.SujetoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Direccion} and its DTO {@link DireccionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DireccionMapper extends EntityMapper<DireccionDTO, Direccion> {
    @Mapping(target = "sujeto", source = "sujeto", qualifiedByName = "sujetoId")
    DireccionDTO toDto(Direccion s);

    @Named("sujetoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SujetoDTO toDtoSujetoId(Sujeto sujeto);
}
