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
    public ResponseEntity<?> evaluarPersonaNatural(@RequestBody PersonaNaturalDTO dto) {
        // Validaciones manuales
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }
        if (dto.getEdad() < 18) {
            return ResponseEntity.badRequest().body("La edad debe ser mayor o igual a 18.");
        }
        if (dto.getIngresoMensual() == null || dto.getIngresoMensual().doubleValue() <= 0) {
            return ResponseEntity.badRequest().body("El ingreso mensual debe ser mayor a 0.");
        }
        if (dto.getMontoSolicitado() == null || dto.getMontoSolicitado().doubleValue() <= 0) {
            return ResponseEntity.badRequest().body("El monto solicitado debe ser mayor a 0.");
        }
        if (dto.getDeudasActuales() == null || dto.getDeudasActuales().isEmpty()) {
            return ResponseEntity.badRequest().body("Debe registrar al menos una deuda.");
        }
        boolean deudaInvalida = dto.getDeudasActuales().stream()
            .anyMatch(d -> d.getMonto() == null || d.getMonto().doubleValue() <= 0 || d.getPlazoMeses() <= 0);
        if (deudaInvalida) {
            return ResponseEntity.badRequest().body("Todas las deudas deben tener monto y plazo válidos.");
        }

        // Lógica principal
        PersonaNatural cliente = convertir(dto);
        EvaluadorRiesgo evaluador = evaluadorFactory.obtenerEvaluador(cliente);
        ResultadoEvaluacion resultado = evaluador.evaluar(cliente);
        guardarHistorial(cliente, resultado);
        return ResponseEntity.ok(convertirResultado(resultado));
    }


    @PostMapping("/juridica")
    public ResponseEntity<?> evaluarPersonaJuridica(@RequestBody PersonaJuridicaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }
        if (dto.getIngresoAnual() == null || dto.getIngresoAnual().doubleValue() <= 0) {
            return ResponseEntity.badRequest().body("El ingreso anual debe ser mayor a 0.");
        }
        if (dto.getEmpleados() <= 0) {
            return ResponseEntity.badRequest().body("Debe tener al menos un empleado.");
        }
        if (dto.getAntiguedadAnios() < 1) {
            return ResponseEntity.badRequest().body("La antigüedad debe ser de al menos 1 año.");
        }
        if (dto.getMontoSolicitado() == null || dto.getMontoSolicitado().doubleValue() <= 0) {
            return ResponseEntity.badRequest().body("El monto solicitado debe ser mayor a 0.");
        }
        if (dto.getDeudasActuales() == null || dto.getDeudasActuales().isEmpty()) {
            return ResponseEntity.badRequest().body("Debe registrar al menos una deuda.");
        }
        boolean deudaInvalida = dto.getDeudasActuales().stream()
            .anyMatch(d -> d.getMonto() == null || d.getMonto().doubleValue() <= 0 || d.getPlazoMeses() <= 0);
        if (deudaInvalida) {
            return ResponseEntity.badRequest().body("Todas las deudas deben tener monto y plazo válidos.");
        }

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
    @GetMapping("/historial/dto")
    public ResponseEntity<List<HistorialDTO>> consultarHistorialDTOs() {
        List<HistorialDTO> dtos = historialRepo.findAll()
            .stream()
            .map(h -> {
                HistorialDTO dto = new HistorialDTO();
                dto.setId(h.getId());
                dto.setClienteNombre(h.getClienteNombre());
                dto.setNivelRiesgo(h.getNivelRiesgo());
                dto.setAprobado(h.isAprobado());
                return dto;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

}
