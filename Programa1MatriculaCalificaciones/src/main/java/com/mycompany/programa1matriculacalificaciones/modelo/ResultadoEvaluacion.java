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
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }
    public Map<Integer, Object> getRespuestas() { return respuestas; }
    public void setRespuestas(Map<Integer, Object> respuestas) { this.respuestas = respuestas; }
    public double getPuntosObtenidos() { return puntosObtenidos; }
    public void setPuntosObtenidos(double puntosObtenidos) { this.puntosObtenidos = puntosObtenidos; }
    
}
