package ec.edu.espe.riesgocrediticio.servicio;

import ec.edu.espe.riesgocrediticio.modelo.Cliente;
import ec.edu.espe.riesgocrediticio.modelo.ResultadoEvaluacion;

public abstract class EvaluadorRiesgo {
    public abstract ResultadoEvaluacion evaluar(Cliente cliente);
}
