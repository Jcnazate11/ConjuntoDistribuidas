package ec.edu.espe.riesgocrediticio.modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cliente")
@Getter
@Setter
public abstract class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = " nombre",nullable = false)
    protected String nombre;

     @Column(name = "puntajeCrediticio",nullable = false)
    protected int puntajeCrediticio;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Deuda> deudasActuales;

    @Column(name = "montoSolicitado",nullable = false)
    protected BigDecimal montoSolicitado;

    @Column(name = "plazoEnMeses",nullable = false)
    protected int plazoEnMeses;
}
