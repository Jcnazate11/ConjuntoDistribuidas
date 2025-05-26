package ec.edu.espe.riesgocrediticio.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class PersonaNatural extends Cliente {

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false)
    private BigDecimal ingresoMensual;

    @Override
    public double getIngresoReferencial() {
        return ingresoMensual.doubleValue();
    }

    @Override
    public boolean esAptoParaCredito() {
        return edad >= 18 && ingresoMensual.doubleValue() > 0;
    }
}
