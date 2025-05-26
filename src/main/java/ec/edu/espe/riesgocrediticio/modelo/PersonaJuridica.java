package ec.edu.espe.riesgocrediticio.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class PersonaJuridica extends Cliente {

    @Column(nullable = false)
    private int antiguedadAnios;

    @Column(nullable = false)
    private BigDecimal ingresoAnual;

    @Column(nullable = false)
    private int empleados;

    @Override
    public double getIngresoReferencial() {
        return ingresoAnual.doubleValue() / 12.0;
    }

    @Override
    public boolean esAptoParaCredito() {
        return antiguedadAnios >= 1 && empleados > 0;
    }
}
