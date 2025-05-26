package ec.edu.espe.riesgocrediticio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cliente")
public abstract class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String nombre;

    @Column(nullable = false)
    protected int puntajeCrediticio;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Deuda> deudasActuales;

    @Column(nullable = false)
    protected BigDecimal montoSolicitado;

    @Column(nullable = false)
    protected int plazoEnMeses;

    public double getMontoDeudas() {
        return deudasActuales.stream()
                .mapToDouble(deuda -> deuda.getMonto().doubleValue())
                .sum();
    }

    public abstract double getIngresoReferencial();

    public abstract boolean esAptoParaCredito();
}
