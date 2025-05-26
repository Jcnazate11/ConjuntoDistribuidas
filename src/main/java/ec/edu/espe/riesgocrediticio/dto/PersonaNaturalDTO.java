package ec.edu.espe.riesgocrediticio.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PersonaNaturalDTO {
    private String nombre;
    private int puntajeCrediticio;
    private List<DeudaDTO> deudasActuales;
    private BigDecimal montoSolicitado;
    private int plazoEnMeses;
    private int edad;
    private BigDecimal ingresoMensual;
}
