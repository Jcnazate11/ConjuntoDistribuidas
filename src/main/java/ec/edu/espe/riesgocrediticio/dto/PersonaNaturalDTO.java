package ec.edu.espe.riesgocrediticio.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaNaturalDTO {

    private String nombre;
    private int puntajeCrediticio;
    private List<DeudaDTO> deudasActuales;
    private BigDecimal montoSolicitado;
    private int plazoEnMeses;

    private int edad;
    private BigDecimal ingresoMensual;

    // Getters y Setters
}