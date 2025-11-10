package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.EvaluacionAsignada;
import com.mycompany.programa1matriculacalificaciones.modelo.Grupo;
import com.mycompany.programa1matriculacalificaciones.modelo.Matricula;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class EvaluacionAsignadaService {
    private static final String RUTA = PathConfig.BASE_DATA_DIR + "/evaluaciones_asignadas.dat";
    private ArchivoService<EvaluacionAsignada> archivo = new ArchivoService<>();
    private List<EvaluacionAsignada> asignaciones;

    public EvaluacionAsignadaService() {
        asignaciones = archivo.cargarLista(RUTA);
        if (asignaciones == null) {
            asignaciones = new ArrayList<>();
        }
    }

    public void agregar(EvaluacionAsignada ea) {
        // Verificar si ya existe una asignación igual
        if (!asignaciones.contains(ea)) {
            asignaciones.add(ea);
            archivo.guardarLista(asignaciones, RUTA);
        }
    }

    public List<EvaluacionAsignada> listar() {
        return new ArrayList<>(asignaciones);
    }

    /**
     * Lista las asignaciones de evaluaciones creadas por un profesor específico
     * @param profesorId ID del profesor
     * @return Lista de asignaciones del profesor
     */
    public List<EvaluacionAsignada> listarAsignacionesPorProfesor(String profesorId) {
        List<EvaluacionAsignada> asignacionesProfesor = new ArrayList<>();
        for (EvaluacionAsignada ea : asignaciones) {
            if (ea.getProfesorId() != null && ea.getProfesorId().equals(profesorId)) {
                asignacionesProfesor.add(ea);
            }
        }
        return asignacionesProfesor;
    }

    public List<EvaluacionAsignada> listarPorGrupo(Grupo grupo) {
        List<EvaluacionAsignada> resultado = new ArrayList<>();
        for (EvaluacionAsignada ea : asignaciones) {
            if (ea.getGrupo() != null && ea.getGrupo().getCodigo().equals(grupo.getCodigo())) {
                resultado.add(ea);
            }
        }
        return resultado;
    }

    /**
     * Obtiene evaluaciones asignadas vigentes para un grupo
     * @param grupo Grupo para filtrar
     * @return Lista de evaluaciones asignadas vigentes
     */
    public List<EvaluacionAsignada> listarVigentesPorGrupo(Grupo grupo) {
        List<EvaluacionAsignada> resultado = new ArrayList<>();
        for (EvaluacionAsignada ea : asignaciones) {
            if (ea.getGrupo() != null && 
                ea.getGrupo().getCodigo().equals(grupo.getCodigo()) && 
                ea.estaVigente()) {
                resultado.add(ea);
            }
        }
        return resultado;
    }

    /**
     * Elimina una asignación específica
     * @param ea EvaluacionAsignada a eliminar
     * @return true si fue eliminada, false si no existía
     */
    public boolean eliminar(EvaluacionAsignada ea) {
        boolean eliminado = asignaciones.remove(ea);
        if (eliminado) {
            archivo.guardarLista(asignaciones, RUTA);
        }
        return eliminado;
    }

    /**
     * Actualiza una asignación existente
     * @param eaActualizada EvaluacionAsignada actualizada
     */
    public void actualizar(EvaluacionAsignada eaActualizada) {
        for (int i = 0; i < asignaciones.size(); i++) {
            if (asignaciones.get(i).equals(eaActualizada)) {
                asignaciones.set(i, eaActualizada);
                archivo.guardarLista(asignaciones, RUTA);
                break;
            }
        }
    }

    /**
     * Verifica si ya existe una asignación para una evaluación y grupo específicos
     */
    public boolean existeAsignacion(String evaluacionId, String grupoCodigo) {
        for (EvaluacionAsignada ea : asignaciones) {
            if (ea.getEvaluacion() != null && ea.getGrupo() != null &&
                ea.getEvaluacion().getId().equals(evaluacionId) && 
                ea.getGrupo().getCodigo().equals(grupoCodigo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene las evaluaciones asignadas a un estudiante específico
     * @param estudianteId ID del estudiante
     * @return Lista de evaluaciones asignadas al estudiante
     */
    public List<EvaluacionAsignada> listarPorEstudiante(String estudianteId) {
        List<EvaluacionAsignada> evaluacionesEstudiante = new ArrayList<>();
        
        // Usar el nuevo método de MatriculaService
        MatriculaService matriculaService = new MatriculaService();
        Set<String> gruposEstudiante = matriculaService.obtenerGruposDelEstudiante(estudianteId);
        
        if (gruposEstudiante.isEmpty()) {
            return evaluacionesEstudiante; // Estudiante no está en ningún grupo
        }
        
        // Filtrar evaluaciones asignadas a los grupos del estudiante
        for (EvaluacionAsignada ea : asignaciones) {
            if (ea.getGrupo() != null && 
                gruposEstudiante.contains(ea.getGrupo().getCodigo()) && 
                ea.estaVigente()) {
                evaluacionesEstudiante.add(ea);
            }
        }
        
        return evaluacionesEstudiante;
    }

    /**
     * Elimina una asignación por evaluación y grupo
     */
    public boolean eliminarAsignacion(String evaluacionNombre, String grupoInfo) {
        Iterator<EvaluacionAsignada> iterator = asignaciones.iterator();
        while (iterator.hasNext()) {
            EvaluacionAsignada ea = iterator.next();
            if (ea.getEvaluacion() != null && ea.getGrupo() != null &&
                ea.getEvaluacion().getTitulo().equals(evaluacionNombre) && 
                (ea.getGrupo().getCodigo() + " - " + ea.getGrupo().getCurso().getNombre()).equals(grupoInfo)) {
                iterator.remove();
                archivo.guardarLista(asignaciones, RUTA);
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene evaluaciones asignadas a un grupo específico por código de grupo
     * @param grupoCodigo Código del grupo
     * @return Lista de evaluaciones asignadas al grupo
     */
    public List<EvaluacionAsignada> listarPorCodigoGrupo(String grupoCodigo) {
        List<EvaluacionAsignada> resultado = new ArrayList<>();
        for (EvaluacionAsignada ea : asignaciones) {
            if (ea.getGrupo() != null && ea.getGrupo().getCodigo().equals(grupoCodigo)) {
                resultado.add(ea);
            }
        }
        return resultado;
    }

    /**
     * Obtiene evaluaciones asignadas vigentes para un estudiante
     * @param estudianteId ID del estudiante
     * @return Lista de evaluaciones vigentes para el estudiante
     */
    public List<EvaluacionAsignada> listarVigentesPorEstudiante(String estudianteId) {
        List<EvaluacionAsignada> evaluacionesVigentes = new ArrayList<>();
        List<EvaluacionAsignada> evaluacionesEstudiante = listarPorEstudiante(estudianteId);
        
        for (EvaluacionAsignada ea : evaluacionesEstudiante) {
            if (ea.estaVigente()) {
                evaluacionesVigentes.add(ea);
            }
        }
        
        return evaluacionesVigentes;
    }
}
