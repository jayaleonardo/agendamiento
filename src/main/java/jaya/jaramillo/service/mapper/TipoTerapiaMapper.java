package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.TipoTerapia;
import jaya.jaramillo.service.dto.TipoTerapiaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoTerapia} and its DTO {@link TipoTerapiaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoTerapiaMapper extends EntityMapper<TipoTerapiaDTO, TipoTerapia> {}
