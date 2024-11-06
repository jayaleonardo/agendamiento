package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Sujeto;
import jaya.jaramillo.service.dto.SujetoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sujeto} and its DTO {@link SujetoDTO}.
 */
@Mapper(componentModel = "spring")
public interface SujetoMapper extends EntityMapper<SujetoDTO, Sujeto> {}
