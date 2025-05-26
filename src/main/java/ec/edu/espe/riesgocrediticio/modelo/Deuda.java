package ec.edu.espe.riesgocrediticio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private int plazoMeses;
}
