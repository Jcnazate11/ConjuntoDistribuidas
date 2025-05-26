package ec.edu.espe.riesgocrediticio.dto;

import lombok.Data;

@Data
public class HistorialDTO {
    private Long id;
    private String clienteNombre;
    private String nivelRiesgo;
    private boolean aprobado;
}