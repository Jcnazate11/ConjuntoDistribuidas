package ec.edu.espe.riesgocrediticio.modelo;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class HistorialEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clienteNombre",nullable = false)
    private String clienteNombre;

    @Column(name = "tipoCliente",nullable = false)
    private String tipoCliente;

    @Column(name = "montoSolicitado",nullable = false)
    private double montoSolicitado;

    @Column(name = "plazoEnMeses",nullable = false)
    private int plazoEnMeses;

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

    @Column(name = "fechaConsulta",nullable = false)
    private LocalDateTime fechaConsulta;
}