package com.mycompany.programa1matriculacalificaciones.modelo;

import java.time.LocalDateTime;
import java.util.Map;

public class ResultadoEvaluacion {
    private Estudiante estudiante;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Map<Integer, Object> respuestas; // preguntaIndex -> respuesta
    private double puntosObtenidos;

    public ResultadoEvaluacion() {}

    public double calcularCalificacionTotal() {
        return puntosObtenidos; // placeholder
    }

    public Estudiante getEstudiante() { return estudiante; }
}
