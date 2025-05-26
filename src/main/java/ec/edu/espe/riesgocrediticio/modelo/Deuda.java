package ec.edu.espe.riesgocrediticio.modelo;


import jakarta.persistence.*;
import java.math.BigDecimal;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto",nullable = false)
    private BigDecimal monto;

    @Column(name = "plazoMeses",nullable = false)
    private int plazoMeses;

    // Getters y Setters
}
