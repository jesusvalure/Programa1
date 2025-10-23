package com.mycompany.programa1matriculacalificaciones.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvaluacionAsignada {
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
}
