package ec.edu.espe.riesgocrediticio.modelo;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@Entity

public class PersonaNatural extends Cliente {

    @Column(name = "ingresoMensual", nullable = false)
    private int edad;
    @Column(name = "ingresoMensual", nullable = false)
    private BigDecimal ingresoMensual;

    // Getters y Setters
}
