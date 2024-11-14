package jaya.jaramillo.web.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.text.DateFormatter;
import jaya.jaramillo.service.CitaService;
import jaya.jaramillo.service.EspecialistaService;
import jaya.jaramillo.service.HorarioConsultaService;
import jaya.jaramillo.service.PacienteService;
import jaya.jaramillo.service.ProgramacionService;
import jaya.jaramillo.service.dto.CitaDataDTO;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.HorarioConsultaDTO;
import jaya.jaramillo.service.dto.PacienteDTO;
import jaya.jaramillo.service.dto.TurnoEspecialidadDTO;
import jaya.jaramillo.service.impl.PacienteServiceImpl;
import jaya.jaramillo.web.rest.peticion.BuscarTurnoRequest;
import jaya.jaramillo.web.rest.peticion.ConsultarCitasRequest;
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
    private final CitaService citaService;
    private final PacienteService pacienteService;

    public HorariosResource(
        EspecialistaService especialistaService,
        ProgramacionService programacionService,
        HorarioConsultaService horarioconsultaService,
        CitaService citaService,
        PacienteService pacienteService
    ) {
        this.especialistaService = especialistaService;
        this.programacionService = programacionService;
        this.horarioconsultaService = horarioconsultaService;
        this.citaService = citaService;
        this.pacienteService = pacienteService;
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
    public ResponseEntity<HorarioConsultaDTO> crearProgramacion(@RequestBody CrearProgramacionRequest request) {
        LOG.debug("Rest crearProgramacion {}", request);
        return ResponseEntity.ok()
            .body(
                this.programacionService.crearProgramacion(
                        request.getDesde(),
                        request.getHasta(),
                        request.getHoraInicio(),
                        request.getHoraFin(),
                        request.getAlmuerzoDesde(),
                        request.getAlmuerzoHasta(),
                        request.getDuracion(),
                        request.getDiasSemana(),
                        request.getEspecialistaId(),
                        request.getCantidad()
                    )
            );
    }

    @PostMapping("/consultarCitas")
    public ResponseEntity<List<CitaDataDTO>> consultarCitas(@RequestBody ConsultarCitasRequest request) {
        LOG.debug("Rest consultarCitas {}", request);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate desdeConvertida = LocalDate.parse(request.getDesde(), pattern);
        LocalDate hastaConvertida = LocalDate.parse(request.getHasta(), pattern);

        return ResponseEntity.ok()
            .body(
                this.citaService.buscarCita(
                        desdeConvertida,
                        hastaConvertida,
                        request.getEspecialidad(),
                        request.getEspecialistaId(),
                        request.getEstado(),
                        request.getCriterio()
                    )
            );
    }

    @PostMapping("/todos-pacientes")
    public ResponseEntity<List<PacienteDTO>> todosPacientes() {
        LOG.debug("Rest todosPacientes ");

        return ResponseEntity.ok().body(this.pacienteService.obtenerTodos());
    }
}
