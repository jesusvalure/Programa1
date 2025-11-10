package com.mycompany.programa1matriculacalificaciones.modelo;

import java.time.LocalDateTime;
import java.util.*;

public class EvaluacionAsignada {
    private Evaluacion evaluacion;
    private Grupo grupo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private List<ResultadoEvaluacion> resultados; // Lista para almacenar resultados

    public EvaluacionAsignada() {
        this.resultados = new ArrayList<>();
    }

    public EvaluacionAsignada(Evaluacion evaluacion, Grupo grupo, LocalDateTime fechaInicio) {
        this.evaluacion = evaluacion;
        this.grupo = grupo;
        this.fechaInicio = fechaInicio;
        this.resultados = new ArrayList<>();
    }

    public EvaluacionAsignada(Evaluacion evaluacion, Grupo grupo, LocalDateTime fechaInicio, LocalDateTime fechaCierre) {
        this.evaluacion = evaluacion;
        this.grupo = grupo;
        this.fechaInicio = fechaInicio;
        this.fechaCierre = fechaCierre;
        this.resultados = new ArrayList<>();
    }

    // Getters y Setters existentes...
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    // NUEVOS MÉTODOS PARA MANEJAR RESULTADOS
    public List<ResultadoEvaluacion> getResultados() {
        return new ArrayList<>(resultados);
    }

    public void setResultados(List<ResultadoEvaluacion> resultados) {
        this.resultados = resultados != null ? new ArrayList<>(resultados) : new ArrayList<>();
    }

    /**
     * Registra un resultado para esta evaluación asignada
     * @param resultado Resultado a registrar
     */
    public void registrarResultado(ResultadoEvaluacion resultado) {
        if (resultado != null) {
            resultados.add(resultado);
        }
    }

    /**
     * Obtiene los resultados de un estudiante específico
     * @param estudianteId ID del estudiante
     * @return Lista de resultados del estudiante
     */
    public List<ResultadoEvaluacion> getResultadosPorEstudiante(String estudianteId) {
        List<ResultadoEvaluacion> resultadosEstudiante = new ArrayList<>();
        for (ResultadoEvaluacion resultado : resultados) {
            if (resultado.getEstudiante().equalsIgnoreCase(estudianteId)) {
                resultadosEstudiante.add(resultado);
            }
        }
        return resultadosEstudiante;
    }

    /**
     * Verifica si un estudiante ya realizó esta evaluación
     * @param estudianteId ID del estudiante
     * @return true si ya realizó la evaluación, false si no
     */
    public boolean estudianteYaRealizoEvaluacion(String estudianteId) {
        for (ResultadoEvaluacion resultado : resultados) {
            if (resultado.getEstudiante().equalsIgnoreCase(estudianteId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el promedio de notas de todos los resultados
     * @return Promedio de notas, o 0 si no hay resultados
     */
    public double getPromedioNotas() {
        if (resultados.isEmpty()) {
            return 0.0;
        }
        
        double suma = 0.0;
        for (ResultadoEvaluacion resultado : resultados) {
            suma += resultado.getNotaPorcentaje();
        }
        return suma / resultados.size();
    }

    /**
     * Obtiene el número de estudiantes que han realizado la evaluación
     * @return Número de estudiantes
     */
    public int getNumeroEstudiantesRealizados() {
        Set<String> estudiantes = new HashSet<>();
        for (ResultadoEvaluacion resultado : resultados) {
            estudiantes.add(resultado.getEstudiante());
        }
        return estudiantes.size();
    }

    /**
     * Obtiene el porcentaje de completitud de la evaluación
     * @return Porcentaje de estudiantes que han realizado la evaluación
     */
    public double getPorcentajeCompletitud() {
        if (grupo == null || grupo.getEstudiantes() == null) {
            return 0.0;
        }
        
        int totalEstudiantes = grupo.getEstudiantes().size();
        if (totalEstudiantes == 0) {
            return 0.0;
        }
        
        return (double) getNumeroEstudiantesRealizados() / totalEstudiantes * 100.0;
    }

    // Métodos existentes de estado y utilidad...
    public String getProfesorId() {
        return evaluacion != null ? evaluacion.getProfesorId() : null;
    }

    public boolean estaVigente() {
        LocalDateTime ahora = LocalDateTime.now();
        
        if (fechaInicio == null) {
            return false;
        }
        
        if (fechaCierre != null) {
            return !ahora.isBefore(fechaInicio) && !ahora.isAfter(fechaCierre);
        }
        
        return !ahora.isBefore(fechaInicio);
    }

    public boolean estaExpirada() {
        if (fechaCierre == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(fechaCierre);
    }

    public boolean noHaComenzado() {
        if (fechaInicio == null) {
            return true;
        }
        return LocalDateTime.now().isBefore(fechaInicio);
    }

    public String getTiempoRestante() {
        if (fechaCierre == null || estaExpirada()) {
            return "Sin límite";
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        if (ahora.isBefore(fechaInicio)) {
            return "Aún no inicia";
        }
        
        long segundosRestantes = java.time.Duration.between(ahora, fechaCierre).getSeconds();
        
        if (segundosRestantes <= 0) {
            return "Expirada";
        }
        
        long dias = segundosRestantes / (24 * 3600);
        long horas = (segundosRestantes % (24 * 3600)) / 3600;
        long minutos = (segundosRestantes % 3600) / 60;
        long segundos = segundosRestantes % 60;
        
        if (dias > 0) {
            return String.format("%d días, %02d:%02d:%02d", dias, horas, minutos, segundos);
        } else if (horas > 0) {
            return String.format("%02d:%02d:%02d", horas, minutos, segundos);
        } else {
            return String.format("%02d:%02d", minutos, segundos);
        }
    }

    public String getEstado() {
        if (noHaComenzado()) {
            return "Programada";
        } else if (estaExpirada()) {
            return "Expirada";
        } else if (estaVigente()) {
            return "Vigente";
        } else {
            return "Desconocido";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvaluacionAsignada that = (EvaluacionAsignada) o;
        
        boolean evaluacionIgual = Objects.equals(
            evaluacion != null ? evaluacion.getId() : null,
            that.evaluacion != null ? that.evaluacion.getId() : null
        );
        
        boolean grupoIgual = Objects.equals(
            grupo != null ? grupo.getCodigo() : null,
            that.grupo != null ? that.grupo.getCodigo() : null
        );
        
        return evaluacionIgual && grupoIgual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            evaluacion != null ? evaluacion.getId() : null,
            grupo != null ? grupo.getCodigo() : null
        );
    }

    @Override
    public String toString() {
        return "EvaluacionAsignada{" +
                "evaluacion=" + (evaluacion != null ? evaluacion.getTitulo() : "null") +
                ", grupo=" + (grupo != null ? grupo.getCodigo() : "null") +
                ", fechaInicio=" + fechaInicio +
                ", fechaCierre=" + fechaCierre +
                ", estado=" + getEstado() +
                ", resultados=" + resultados.size() +
                '}';
    }

    public String getDescripcion() {
        String evalNombre = evaluacion != null ? evaluacion.getTitulo() : "Evaluación desconocida";
        String grupoNombre = grupo != null ? 
            grupo.getCodigo() + " - " + (grupo.getCurso() != null ? grupo.getCurso().getNombre() : "Curso desconocido") : 
            "Grupo desconocido";
        
        return String.format("%s asignada a %s (%s) - %d resultados", 
            evalNombre, grupoNombre, getEstado(), resultados.size());
    }
}