package jaya.jaramillo.web.rest;

import java.util.List;
import jaya.jaramillo.service.CitaService;
import jaya.jaramillo.service.EspecialistaService;
import jaya.jaramillo.service.HorarioConsultaService;
import jaya.jaramillo.service.PacienteService;
import jaya.jaramillo.service.ProgramacionService;
import jaya.jaramillo.service.dto.CitaDTO;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import jaya.jaramillo.service.dto.TurnoDisponibleDTO;
import jaya.jaramillo.web.rest.peticion.PreReservaRequest;
import jaya.jaramillo.web.rest.peticion.TurnoDisponibleRequest;
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
@RequestMapping("/api/public")
public class PublicResource {

    private static final Logger LOG = LoggerFactory.getLogger(PublicResource.class);
    private final EspecialistaService especialistaService;
    private final ProgramacionService programacionService;
    private final HorarioConsultaService horarioconsultaService;
    private final CitaService citaService;
    private final PacienteService pacienteService;

    public PublicResource(
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

    @GetMapping("/especialidades")
    public ResponseEntity<List<String>> listarEspecialidades() {
        LOG.debug("Rest listarEspecialidades");
        return ResponseEntity.ok().body(this.especialistaService.buscarEspecialidades());
    }

    @GetMapping("/especialistas-por-especialidad/{especialidad}")
    public ResponseEntity<List<EspecialistaDTO>> especialistaPorEspecialidad(@PathVariable(value = "especialidad") String especialidad) {
        LOG.debug("Rest especialistaPorEspecialidad {} ", especialidad);
        return ResponseEntity.ok().body(this.especialistaService.buscarPorEspecialidad(especialidad));
    }

    @GetMapping("/buscar-foto/{especialistaid}")
    public ResponseEntity<EspecialistaDTO> fotoEspecialista(@PathVariable(value = "especialistaid") Long especialista) {
        LOG.debug("Rest especialistaPorEspecialidad {} ", especialista);
        return ResponseEntity.ok().body(this.especialistaService.fotoEspecialista(especialista));
    }

    @PostMapping("/turnos-disponibles")
    public ResponseEntity<List<TurnoDisponibleDTO>> turnosDisponibles(@RequestBody TurnoDisponibleRequest request) {
        LOG.debug("Rest turnosDisponibles {} ", request);
        return ResponseEntity.ok().body(this.programacionService.turnosDisponibles(request.getFecha(), request.getEspecialistaId()));
    }

    @PostMapping("/guardar-prereserva")
    public ResponseEntity<CitaDTO> guardarPrereserva(@RequestBody PreReservaRequest request) {
        LOG.debug("Rest guardarPrereserva: {} ", request);
        return ResponseEntity.ok()
            .body(
                this.citaService.guardarPrereserva(
                        request.getNombre(),
                        request.getSegundoNombre(),
                        request.getApellido(),
                        request.getSegundoApellido(),
                        request.getCelular(),
                        request.getTurnoId(),
                        request.getVirtual()
                    )
            );
    }
}
