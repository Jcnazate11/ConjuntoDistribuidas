package ec.edu.espe.riesgocrediticio.repository;

import ec.edu.espe.riesgocrediticio.modelo.HistorialEvaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialEvaluacionRepository extends JpaRepository<HistorialEvaluacion, Long> {
    
}
