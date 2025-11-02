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

    public EvaluacionAsignada() {}

    public EvaluacionAsignada(Evaluacion e, Grupo g, LocalDateTime inicio) {
        this.evaluacion = e;
        this.grupo = g;
        this.fechaInicio = inicio;
        this.fechaFin = inicio.plusMinutes(e.getPreguntas().size() > 0 ? e.getPreguntas().size() : e.getPreguntas().size()); // placeholder
    }

    public void registrarResultado(ResultadoEvaluacion r) { resultados.add(r); }
    public List<ResultadoEvaluacion> getResultados() { return resultados; }
    public Evaluacion getEvaluacion() { return evaluacion; }
    public void setEvaluacion(Evaluacion evaluacion) { this.evaluacion = evaluacion; }
    public Grupo getGrupo() { return grupo; }
    public void setGrupo(Grupo grupo) { this.grupo = grupo; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
}
