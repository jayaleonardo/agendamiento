package jaya.jaramillo.service.impl;

import java.util.Optional;
import jaya.jaramillo.domain.HorarioConsulta;
import jaya.jaramillo.repository.HorarioConsultaRepository;
import jaya.jaramillo.service.HorarioConsultaService;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import jaya.jaramillo.service.mapper.HorarioConsultaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link jaya.jaramillo.domain.HorarioConsulta}.
 */
@Service
@Transactional
public class HorarioConsultaServiceImpl implements HorarioConsultaService {

    private static final Logger LOG = LoggerFactory.getLogger(HorarioConsultaServiceImpl.class);

    private final HorarioConsultaRepository horarioConsultaRepository;

    private final HorarioConsultaMapper horarioConsultaMapper;

    public HorarioConsultaServiceImpl(HorarioConsultaRepository horarioConsultaRepository, HorarioConsultaMapper horarioConsultaMapper) {
        this.horarioConsultaRepository = horarioConsultaRepository;
        this.horarioConsultaMapper = horarioConsultaMapper;
    }

    @Override
    public HorarioConsultaDTO save(HorarioConsultaDTO horarioConsultaDTO) {
        LOG.debug("Request to save HorarioConsulta : {}", horarioConsultaDTO);
        HorarioConsulta horarioConsulta = horarioConsultaMapper.toEntity(horarioConsultaDTO);
        horarioConsulta = horarioConsultaRepository.save(horarioConsulta);
        return horarioConsultaMapper.toDto(horarioConsulta);
    }

    @Override
    public HorarioConsultaDTO update(HorarioConsultaDTO horarioConsultaDTO) {
        LOG.debug("Request to update HorarioConsulta : {}", horarioConsultaDTO);
        HorarioConsulta horarioConsulta = horarioConsultaMapper.toEntity(horarioConsultaDTO);
        horarioConsulta = horarioConsultaRepository.save(horarioConsulta);
        return horarioConsultaMapper.toDto(horarioConsulta);
    }

    @Override
    public Optional<HorarioConsultaDTO> partialUpdate(HorarioConsultaDTO horarioConsultaDTO) {
        LOG.debug("Request to partially update HorarioConsulta : {}", horarioConsultaDTO);

        return horarioConsultaRepository
            .findById(horarioConsultaDTO.getId())
            .map(existingHorarioConsulta -> {
                horarioConsultaMapper.partialUpdate(existingHorarioConsulta, horarioConsultaDTO);

                return existingHorarioConsulta;
            })
            .map(horarioConsultaRepository::save)
            .map(horarioConsultaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HorarioConsultaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all HorarioConsultas");
        return horarioConsultaRepository.findAll(pageable).map(horarioConsultaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HorarioConsultaDTO> findOne(Long id) {
        LOG.debug("Request to get HorarioConsulta : {}", id);
        return horarioConsultaRepository.findById(id).map(horarioConsultaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete HorarioConsulta : {}", id);
        horarioConsultaRepository.deleteById(id);
    }
}
