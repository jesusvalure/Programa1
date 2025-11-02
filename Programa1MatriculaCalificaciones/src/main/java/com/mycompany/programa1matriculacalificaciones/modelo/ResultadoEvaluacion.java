package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ResultadoEvaluacion implements Serializable {
    private String id;
    private String estudiante;
    private String evaluacionId;
    private String tituloEvaluacion;
    private double puntajeObtenido;
    private double puntajeTotal;
    private double notaPorcentaje;
    private LocalDateTime fecha;

    public ResultadoEvaluacion(String estudiante, Evaluacion evaluacion, double puntajeObtenido, double puntajeTotal) {
        this.id = UUID.randomUUID().toString();
        this.estudiante = estudiante;
        this.evaluacionId = evaluacion.getId();
        this.tituloEvaluacion = evaluacion.getTitulo();
        this.puntajeObtenido = puntajeObtenido;
        this.puntajeTotal = puntajeTotal;
        this.notaPorcentaje = (puntajeObtenido / puntajeTotal) * 100.0;
        this.fecha = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getEstudiante() { return estudiante; }
    public String getEvaluacionId() { return evaluacionId; }
    public String getTituloEvaluacion() { return tituloEvaluacion; }
    public double getPuntajeObtenido() { return puntajeObtenido; }
    public double getPuntajeTotal() { return puntajeTotal; }
    public double getNotaPorcentaje() { return notaPorcentaje; }
    public LocalDateTime getFecha() { return fecha; }

    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "[" + getFechaFormateada() + "] " + tituloEvaluacion +
               " - " + String.format("%.1f", notaPorcentaje) + "% (" + estudiante + ")";
    }
}
