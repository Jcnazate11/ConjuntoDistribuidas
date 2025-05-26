package ec.edu.espe.riesgocrediticio.dto;

import lombok.Data;

@Data
public class ResultadoDTO {
    private String nivelRiesgo;
    private boolean aprobado;
    private double puntajeFinal;
    private String mensaje;
    private double tasaInteres;
    private int plazoAprobado;
}
