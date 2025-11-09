package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvaluacionAsignada implements Serializable {
    private static final long serialVersionUID = 1L;
    private Evaluacion evaluacion;
    private Grupo grupo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<ResultadoEvaluacion> resultados = new ArrayList<>();
    private boolean activa;

    public EvaluacionAsignada() {}

    public EvaluacionAsignada(Evaluacion e, Grupo g, LocalDateTime inicio) {
        this.evaluacion = e;
        this.grupo = g;
        this.fechaInicio = inicio;
        // Establecer fecha fin por defecto (7 días después)
        this.fechaFin = inicio.plusDays(7);
        this.activa = true;
    }

    // Método para verificar si la evaluación está vigente
    public boolean estaVigente() {
        LocalDateTime ahora = LocalDateTime.now();
        return activa && ahora.isAfter(fechaInicio) && ahora.isBefore(fechaFin);
    }

    // Método para verificar si la evaluación puede ser realizada por un estudiante
    public boolean puedeRealizarEvaluacion() {
        LocalDateTime ahora = LocalDateTime.now();
        return activa && 
               (ahora.isEqual(fechaInicio) || ahora.isAfter(fechaInicio)) && 
               (ahora.isEqual(fechaFin) || ahora.isBefore(fechaFin));
    }

    // Método para obtener el tiempo restante en minutos
    public long getTiempoRestanteMinutos() {
        if (!estaVigente()) {
            return 0;
        }
        return java.time.Duration.between(LocalDateTime.now(), fechaFin).toMinutes();
    }

    public void registrarResultado(ResultadoEvaluacion r) { 
        resultados.add(r); 
    }
    
    public List<ResultadoEvaluacion> getResultados() { 
        return new ArrayList<>(resultados); 
    }
    
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
    
    public LocalDateTime getFechaFin() { 
        return fechaFin; 
    }
    
    public void setFechaFin(LocalDateTime fechaFin) { 
        this.fechaFin = fechaFin; 
    }
    
    public boolean isActiva() { 
        return activa; 
    }
    
    public void setActiva(boolean activa) { 
        this.activa = activa; 
    }

    // Método para obtener el profesorId de la evaluación asignada
    public String getProfesorId() {
        return evaluacion != null ? evaluacion.getProfesorId() : null;
    }

    // Método para obtener información resumida
    public String getInfoResumida() {
        String evalTitulo = evaluacion != null ? evaluacion.getTitulo() : "Sin evaluación";
        String grupoCodigo = grupo != null ? grupo.getCodigo() : "Sin grupo";
        return evalTitulo + " - " + grupoCodigo + " (" + fechaInicio.toLocalDate() + ")";
    }

    @Override
    public String toString() {
        String evalTitulo = evaluacion != null ? evaluacion.getTitulo() : "Sin evaluación";
        String grupoCodigo = grupo != null ? grupo.getCodigo() : "Sin grupo";
        String estado = estaVigente() ? "Vigente" : "Expirada";
        return evalTitulo + " - " + grupoCodigo + " [" + estado + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        EvaluacionAsignada that = (EvaluacionAsignada) obj;
        
        if (evaluacion != null ? !evaluacion.equals(that.evaluacion) : that.evaluacion != null) return false;
        if (grupo != null ? !grupo.equals(that.grupo) : that.grupo != null) return false;
        if (fechaInicio != null ? !fechaInicio.equals(that.fechaInicio) : that.fechaInicio != null) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int result = evaluacion != null ? evaluacion.hashCode() : 0;
        result = 31 * result + (grupo != null ? grupo.hashCode() : 0);
        result = 31 * result + (fechaInicio != null ? fechaInicio.hashCode() : 0);
        return result;
    }
}