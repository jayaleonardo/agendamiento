package jaya.jaramillo.service.mapper;

import jaya.jaramillo.domain.Clinica;
import jaya.jaramillo.service.dto.ClinicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Clinica} and its DTO {@link ClinicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClinicaMapper extends EntityMapper<ClinicaDTO, Clinica> {}
