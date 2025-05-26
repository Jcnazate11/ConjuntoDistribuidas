package ec.edu.espe.riesgocrediticio.modelo;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ResultadoEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "nivelRiesgo",nullable = false)
    private String nivelRiesgo;

    @Column(name = "aprobado",nullable = false)
    private boolean aprobado;

    @Column(name = "puntajeFinal",nullable = false)
    private double puntajeFinal;

    @Column(name = "tasaInteres",nullable = false)
    private double tasaInteres;

    @Column(name = "plazoAprobado",nullable = false)
    private int plazoAprobado;

    @Column(name = "mensaje",nullable = false)
    private String mensaje;

    @Column(name = "fechaEvaluacion",nullable = false)
    private LocalDateTime fechaEvaluacion;
}