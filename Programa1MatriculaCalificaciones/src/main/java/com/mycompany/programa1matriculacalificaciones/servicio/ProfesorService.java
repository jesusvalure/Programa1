package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class ProfesorService {

    private final ArchivoService<Evaluacion> archivoService;
    private List<Evaluacion> evaluaciones;

    public ProfesorService() {
        this.archivoService = new ArchivoService<>(PathConfig.BASE_DATA_DIR + "/evaluaciones.dat");
        this.evaluaciones = archivoService.cargar();
        if (this.evaluaciones == null) {
            this.evaluaciones = new ArrayList<>();
        }
    }

    public List<Evaluacion> listarEvaluaciones() {
        return new ArrayList<>(evaluaciones);
    }

    /**
     * Lista las evaluaciones creadas por un profesor específico
     * @param profesorId ID del profesor
     * @return Lista de evaluaciones del profesor
     */
    public List<Evaluacion> listarEvaluacionesPorProfesor(String profesorId) {
        List<Evaluacion> evaluacionesProfesor = new ArrayList<>();
        for (Evaluacion e : evaluaciones) {
            if (e.getProfesorId() != null && e.getProfesorId().equals(profesorId)) {
                evaluacionesProfesor.add(e);
            }
        }
        return evaluacionesProfesor;
    }

    /**
     * Agrega una evaluación si no existe otra con el mismo id.
     * @param e Evaluación a agregar
     * @return true si fue agregada, false si ya existía o entrada inválida
     */
    public boolean agregarEvaluacion(Evaluacion e) {
        if (e == null || e.getId() == null) return false;
        if (obtenerEvaluacionPorId(e.getId()) != null) return false;
        evaluaciones.add(e);
        archivoService.guardar(evaluaciones);
        return true;
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

    /**
     * Obtiene una evaluación específica de un profesor
     * @param profesorId ID del profesor
     * @param evaluacionId ID de la evaluación
     * @return La evaluación si pertenece al profesor, null en caso contrario
     */
    public Evaluacion obtenerEvaluacionPorProfesorYId(String profesorId, String evaluacionId) {
        for (Evaluacion e : evaluaciones) {
            if (e.getId().equals(evaluacionId) && e.getProfesorId() != null && e.getProfesorId().equals(profesorId)) {
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