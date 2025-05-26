package ec.edu.espe.riesgocrediticio.controller;

import ec.edu.espe.riesgocrediticio.dto.*;
import ec.edu.espe.riesgocrediticio.modelo.*;
import ec.edu.espe.riesgocrediticio.repository.HistorialEvaluacionRepository;
import ec.edu.espe.riesgocrediticio.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/evaluar-riesgo")
public class EvaluacionController {

    @Autowired
    private EvaluadorFactory evaluadorFactory;

    @Autowired
    private HistorialEvaluacionRepository historialRepo;

    @PostMapping("/natural")
    public ResponseEntity<ResultadoDTO> evaluarPersonaNatural(@RequestBody PersonaNaturalDTO dto) {
        PersonaNatural cliente = convertir(dto);
        EvaluadorRiesgo evaluador = evaluadorFactory.obtenerEvaluador(cliente);
        ResultadoEvaluacion resultado = evaluador.evaluar(cliente);
        guardarHistorial(cliente, resultado);
        return ResponseEntity.ok(convertirResultado(resultado));
    }

    @PostMapping("/juridica")
    public ResponseEntity<ResultadoDTO> evaluarPersonaJuridica(@RequestBody PersonaJuridicaDTO dto) {
        PersonaJuridica cliente = convertir(dto);
        EvaluadorRiesgo evaluador = evaluadorFactory.obtenerEvaluador(cliente);
        ResultadoEvaluacion resultado = evaluador.evaluar(cliente);
        guardarHistorial(cliente, resultado);
        return ResponseEntity.ok(convertirResultado(resultado));
    }

    @GetMapping("/historial/{id}")
    public ResponseEntity<HistorialEvaluacion> consultarHistorial(@PathVariable Long id) {
        return historialRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    // 🛠 Conversores

    private PersonaNatural convertir(PersonaNaturalDTO dto) {
        PersonaNatural p = new PersonaNatural();
        p.setNombre(dto.getNombre());
        p.setEdad(dto.getEdad());
        p.setIngresoMensual(dto.getIngresoMensual());
        p.setPuntajeCrediticio(dto.getPuntajeCrediticio());
        p.setDeudasActuales(convertirDeudas(dto.getDeudasActuales()));
        p.setMontoSolicitado(dto.getMontoSolicitado());
        p.setPlazoEnMeses(dto.getPlazoEnMeses());
        return p;
    }

    private PersonaJuridica convertir(PersonaJuridicaDTO dto) {
        PersonaJuridica p = new PersonaJuridica();
        p.setNombre(dto.getNombre());
        p.setAntiguedadAnios(dto.getAntiguedadAnios());
        p.setIngresoAnual(dto.getIngresoAnual());
        p.setEmpleados(dto.getEmpleados());
        p.setPuntajeCrediticio(dto.getPuntajeCrediticio());
        p.setDeudasActuales(convertirDeudas(dto.getDeudasActuales()));
        p.setMontoSolicitado(dto.getMontoSolicitado());
        p.setPlazoEnMeses(dto.getPlazoEnMeses());
        return p;
    }

    private List<Deuda> convertirDeudas(List<DeudaDTO> deudaDTOs) {
        return deudaDTOs.stream().map(dto -> {
            Deuda deuda = new Deuda();
            deuda.setMonto(dto.getMonto());
            deuda.setPlazoMeses(dto.getPlazoMeses());
            return deuda;
        }).collect(Collectors.toList());
    }

    private ResultadoDTO convertirResultado(ResultadoEvaluacion e) {
        ResultadoDTO dto = new ResultadoDTO();
        dto.setNivelRiesgo(e.getNivelRiesgo());
        dto.setAprobado(e.isAprobado());
        dto.setPuntajeFinal(e.getPuntajeFinal());
        dto.setMensaje(e.getMensaje());
        dto.setTasaInteres(e.getTasaInteres());
        dto.setPlazoAprobado(e.getPlazoAprobado());
        return dto;
    }

    private void guardarHistorial(Cliente cliente, ResultadoEvaluacion resultado) {
        HistorialEvaluacion h = new HistorialEvaluacion();
        h.setClienteNombre(cliente.getNombre());
        h.setTipoCliente(cliente instanceof PersonaNatural ? "NATURAL" : "JURIDICA");
        h.setMontoSolicitado(cliente.getMontoSolicitado().doubleValue());
        h.setPlazoEnMeses(cliente.getPlazoEnMeses());
        h.setNivelRiesgo(resultado.getNivelRiesgo());
        h.setAprobado(resultado.isAprobado());
        h.setPuntajeFinal(resultado.getPuntajeFinal());
        h.setTasaInteres(resultado.getTasaInteres());
        h.setPlazoAprobado(resultado.getPlazoAprobado());
        h.setMensaje(resultado.getMensaje());
        h.setFechaConsulta(LocalDateTime.now());
        historialRepo.save(h);
    }
}
