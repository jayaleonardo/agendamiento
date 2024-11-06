package jaya.jaramillo.web.rest;

import java.util.List;
import jaya.jaramillo.service.EspecialistaService;
import jaya.jaramillo.service.dto.EspecialistaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorariosResource {

    private static final Logger LOG = LoggerFactory.getLogger(HorariosResource.class);
    private final EspecialistaService especialistaService;

    public HorariosResource(EspecialistaService especialistaService) {
        this.especialistaService = especialistaService;
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
}
