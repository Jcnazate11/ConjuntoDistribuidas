package ec.edu.espe.riesgocrediticio.servicio;

import ec.edu.espe.riesgocrediticio.modelo.Cliente;
import ec.edu.espe.riesgocrediticio.modelo.PersonaJuridica;
import ec.edu.espe.riesgocrediticio.modelo.PersonaNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EvaluadorFactory {

    @Autowired private EvaluadorRiesgoBajo bajo;
    @Autowired private EvaluadorRiesgoMedio medio;
    @Autowired private EvaluadorRiesgoAlto alto;

    public EvaluadorRiesgo obtenerEvaluador(Cliente cliente) {
        double puntaje = 100;

        if (cliente.getPuntajeCrediticio() < 650) puntaje -= 30;

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

        if (puntaje >= 80) return bajo;
        else if (puntaje >= 60) return medio;
        else return alto;
    }
}

