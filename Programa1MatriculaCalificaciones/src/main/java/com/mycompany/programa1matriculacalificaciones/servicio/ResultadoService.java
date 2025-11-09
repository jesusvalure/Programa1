package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.ResultadoEvaluacion;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class ResultadoService {

    private final ArchivoService<ResultadoEvaluacion> archivoService;
    private List<ResultadoEvaluacion> resultados;

    public ResultadoService() {
        this.archivoService = new ArchivoService<>(PathConfig.BASE_DATA_DIR + "/resultados.dat");
        this.resultados = archivoService.cargar();
        if (this.resultados == null) {
            this.resultados = new ArrayList<>();
        }
    }

    public void registrarResultado(ResultadoEvaluacion r) {
        resultados.add(r);
        archivoService.guardar(resultados);
    }

    public List<ResultadoEvaluacion> listarResultados() {
        return new ArrayList<>(resultados);
    }

    /**
     * Lista los resultados de evaluaciones creadas por un profesor específico
     * @param profesorId ID del profesor
     * @return Lista de resultados del profesor
     */
    public List<ResultadoEvaluacion> listarResultadosPorProfesor(String profesorId) {
        List<ResultadoEvaluacion> resultadosProfesor = new ArrayList<>();
        for (ResultadoEvaluacion r : resultados) {
            if (r.getProfesorId() != null && r.getProfesorId().equals(profesorId)) {
                resultadosProfesor.add(r);
            }
        }
        return resultadosProfesor;
    }

    /**
     * Filtra resultados por profesor y opcionalmente por evaluación y grupo
     * @param profesorId ID del profesor
     * @param evaluacionId ID de la evaluación (opcional, puede ser null)
     * @param grupoId ID del grupo (opcional, puede ser null)
     * @return Lista de resultados filtrados
     */
    public List<ResultadoEvaluacion> filtrarResultadosPorProfesor(String profesorId, String evaluacionId, String grupoId) {
        List<ResultadoEvaluacion> resultadosFiltrados = new ArrayList<>();
        for (ResultadoEvaluacion r : resultados) {
            boolean coincideProfesor = r.getProfesorId() != null && r.getProfesorId().equals(profesorId);
            boolean coincideEvaluacion = evaluacionId == null || (r.getEvaluacionId() != null && r.getEvaluacionId().equals(evaluacionId));
            boolean coincideGrupo = grupoId == null || (r.getGrupoId() != null && r.getGrupoId().equals(grupoId));
            
            if (coincideProfesor && coincideEvaluacion && coincideGrupo) {
                resultadosFiltrados.add(r);
            }
        }
        return resultadosFiltrados;
    }

    public List<ResultadoEvaluacion> resultadosPorEstudiante(String nombre) {
        List<ResultadoEvaluacion> lista = new ArrayList<>();
        for (ResultadoEvaluacion r : resultados) {
            if (r.getEstudiante().equalsIgnoreCase(nombre)) lista.add(r);
        }
        return lista;
    }
}