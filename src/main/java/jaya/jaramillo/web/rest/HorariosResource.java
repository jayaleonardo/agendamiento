package jaya.jaramillo.web.rest;

import java.util.List;
import jaya.jaramillo.service.EspecialistaService;
import jaya.jaramillo.service.HorarioConsultaService;
import jaya.jaramillo.service.ProgramacionService;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.TurnoEspecialidadDTO;
import jaya.jaramillo.web.rest.peticion.BuscarTurnoRequest;
import jaya.jaramillo.web.rest.peticion.CrearProgramacionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorariosResource {

    private static final Logger LOG = LoggerFactory.getLogger(HorariosResource.class);
    private final EspecialistaService especialistaService;
    private final ProgramacionService programacionService;
    private final HorarioConsultaService horarioconsultaService;

    public HorariosResource(
        EspecialistaService especialistaService,
        ProgramacionService programacionService,
        HorarioConsultaService horarioconsultaService
    ) {
        this.especialistaService = especialistaService;
        this.programacionService = programacionService;
        this.horarioconsultaService = horarioconsultaService;
    }

    @PostMapping("/listarEspecialidades")
    public ResponseEntity<List<String>> listarEspecialidades() {
        LOG.debug("Rest listarEspecialidades");
        return ResponseEntity.ok().body(this.especialistaService.buscarEspecialidades());
    }

    @GetMapping("/especialistas-por-especialidad/{especialidad}")
    public ResponseEntity<List<EspecialistaDTO>> especialistaPorEspecialidad(@PathVariable(value = "especialidad") String especialidad) {
        LOG.debug("Rest especialistaPorEspecialidad {} ", especialidad);
        return ResponseEntity.ok().body(this.especialistaService.buscarPorEspecialidad(especialidad));
    }

    @PostMapping("/buscarTurnos")
    public ResponseEntity<List<TurnoEspecialidadDTO>> buscarTurnos(@RequestBody BuscarTurnoRequest request) {
        LOG.debug("Rest buscarTurnos");
        return ResponseEntity.ok()
            .body(this.programacionService.buscarTurnos(request.getEspecialistaId(), request.getDesde(), request.getHasta()));
    }

    @PostMapping("/crear-programacion")
    public ResponseEntity<List<TurnoEspecialidadDTO>> crearProgramacion(@RequestBody CrearProgramacionRequest request) {
        LOG.debug("Rest buscarTurnos");
        return ResponseEntity.ok().body(null);
    }
}
