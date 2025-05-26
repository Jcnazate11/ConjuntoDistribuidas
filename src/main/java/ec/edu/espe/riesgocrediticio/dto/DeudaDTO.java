package ec.edu.espe.riesgocrediticio.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeudaDTO {
    private BigDecimal monto;
    private int plazoMeses;
}
