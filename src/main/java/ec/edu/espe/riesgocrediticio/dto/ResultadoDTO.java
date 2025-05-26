package ec.edu.espe.riesgocrediticio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResultadoDTO {

    private String nivelRiesgo;
    private boolean aprobado;
    private double puntajeFinal;
    private double tasaInteres;
    private int plazoAprobado;
    private String mensaje;
    private LocalDateTime fechaEvaluacion;
}
