package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Contacto;
import jaya.jaramillo.domain.Sujeto;
import jaya.jaramillo.service.dto.ContactoDTO;
import jaya.jaramillo.service.dto.SujetoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contacto} and its DTO {@link ContactoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactoMapper extends EntityMapper<ContactoDTO, Contacto> {
    @Mapping(target = "sujeto", source = "sujeto", qualifiedByName = "sujetoId")
    ContactoDTO toDto(Contacto s);

    @Named("sujetoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SujetoDTO toDtoSujetoId(Sujeto sujeto);
}
