package ec.edu.espe.riesgocrediticio.servicio;

import ec.edu.espe.riesgocrediticio.modelo.Cliente;
import ec.edu.espe.riesgocrediticio.modelo.PersonaJuridica;
import ec.edu.espe.riesgocrediticio.modelo.PersonaNatural;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

        return Stream.<Map.Entry<Boolean, EvaluadorRiesgo>>of(
            new AbstractMap.SimpleEntry<>(puntaje >= 80, bajo),
            new AbstractMap.SimpleEntry<>(puntaje >= 60, medio)
        )
        .filter(Map.Entry::getKey)
        .map(Map.Entry::getValue)
        .findFirst()
        .orElse(alto);

        }
}
