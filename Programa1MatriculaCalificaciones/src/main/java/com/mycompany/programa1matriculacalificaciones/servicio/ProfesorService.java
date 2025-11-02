package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion;
import java.util.*;

public class ProfesorService {

    private final ArchivoService<Evaluacion> archivoService;
    private List<Evaluacion> evaluaciones;

    public ProfesorService() {
        this.archivoService = new ArchivoService<>("datos/matriculaycalificaciones/evaluaciones.dat");
        this.evaluaciones = archivoService.cargar();
        if (this.evaluaciones == null) {
            this.evaluaciones = new ArrayList<>();
        }
    }

    public List<Evaluacion> listarEvaluaciones() {
        return new ArrayList<>(evaluaciones);
    }

    public void agregarEvaluacion(Evaluacion e) {
        evaluaciones.add(e);
        archivoService.guardar(evaluaciones);
    }

    public void actualizarEvaluacion(Evaluacion eActualizada) {
        for (int i = 0; i < evaluaciones.size(); i++) {
            Evaluacion e = evaluaciones.get(i);
            if (e.getId().equals(eActualizada.getId())) {
                // Preservar las preguntas existentes si la evaluación actualizada tiene lista vacía
                if (eActualizada.getPreguntas().isEmpty() && !e.getPreguntas().isEmpty()) {
                    eActualizada.getPreguntas().addAll(e.getPreguntas());
                }
                evaluaciones.set(i, eActualizada);
                break;
            }
        }
        archivoService.guardar(evaluaciones);
    }

    public Evaluacion obtenerEvaluacionPorId(String id) {
        for (Evaluacion e : evaluaciones) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public void eliminarEvaluacion(String id) {
        evaluaciones.removeIf(e -> e.getId().equals(id));
        archivoService.guardar(evaluaciones);
    }
}
