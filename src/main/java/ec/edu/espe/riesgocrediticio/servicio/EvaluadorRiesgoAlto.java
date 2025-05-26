package ec.edu.espe.riesgocrediticio.servicio;

import ec.edu.espe.riesgocrediticio.modelo.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EvaluadorRiesgoAlto extends EvaluadorRiesgo {

    @Override
    public ResultadoEvaluacion evaluar(Cliente cliente) {
        double puntaje = 100;

        if (cliente.getPuntajeCrediticio() < 650) {
            puntaje -= 30;
        }

        double ingreso = cliente.getIngresoReferencial();
        double deudas = cliente.getMontoDeudas();
        double montoSolicitado = cliente.getMontoSolicitado().doubleValue();

        if (cliente instanceof PersonaNatural) {
            if (deudas > ingreso * 0.4) puntaje -= 15;
            if (montoSolicitado > ingreso * 0.5) puntaje -= 10;
        } else if (cliente instanceof PersonaJuridica) {
            if (deudas > ingreso * 0.35) puntaje -= 20;
            if (montoSolicitado > ingreso * 0.3) puntaje -= 15;
        }

        ResultadoEvaluacion resultado = new ResultadoEvaluacion();
        resultado.setNivelRiesgo("ALTO");
        resultado.setAprobado(false);
        resultado.setPuntajeFinal(puntaje);
        resultado.setTasaInteres(0.0);
        resultado.setPlazoAprobado(0);
        resultado.setMensaje("Cliente con alto riesgo financiero. Préstamo no aprobado.");
        resultado.setFechaEvaluacion(LocalDateTime.now());
        return resultado;
    }
}
