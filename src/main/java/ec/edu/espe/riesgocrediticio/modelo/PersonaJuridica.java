package ec.edu.espe.riesgocrediticio.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@Entity
public class PersonaJuridica extends Cliente {

    @Column(name = "antiguedadAnios",nullable = false)
    private int antiguedadAnios;
    @Column(name = "ingresoAnual",nullable = false)
    private BigDecimal ingresoAnual;
    @Column(name = "empleados",nullable = false)
    private int empleados;

    // Getters y Setters
}
