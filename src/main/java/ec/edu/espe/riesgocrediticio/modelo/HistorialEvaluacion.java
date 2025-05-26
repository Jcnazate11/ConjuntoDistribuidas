package ec.edu.espe.riesgocrediticio.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class HistorialEvaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String clienteNombre;

    @Column(nullable = false)
    private String tipoCliente;

    @Column(nullable = false)
    private double montoSolicitado;

    @Column(nullable = false)
    private int plazoEnMeses;

    @Column(nullable = false)
    private String nivelRiesgo;

    @Column(nullable = false)
    private boolean aprobado;

    @Column(nullable = false)
    private double puntajeFinal;

    @Column(nullable = false)
    private double tasaInteres;

    @Column(nullable = false)
    private int plazoAprobado;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaConsulta;
}
