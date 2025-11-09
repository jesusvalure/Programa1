package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ResultadoEvaluacion implements Serializable {
    private String id;
    private String estudiante;
    private String profesorId;
    private String evaluacionId;
    private String grupoId;
    private String tituloEvaluacion;
    private double puntajeObtenido;
    private double puntajeTotal;
    private double notaPorcentaje;
    private LocalDateTime fecha;
    private Grupo grupo; // Nuevo campo para referencia directa

    // Constructor original
    public ResultadoEvaluacion(String estudiante, Evaluacion evaluacion, double puntajeObtenido, double puntajeTotal) {
        this.id = UUID.randomUUID().toString();
        this.estudiante = estudiante;
        this.evaluacionId = evaluacion.getId();
        this.tituloEvaluacion = evaluacion.getTitulo();
        this.puntajeObtenido = puntajeObtenido;
        this.puntajeTotal = puntajeTotal;
        this.notaPorcentaje = (puntajeObtenido / puntajeTotal) * 100.0;
        this.fecha = LocalDateTime.now();
        this.profesorId = evaluacion.getProfesorId(); // Asignar profesorId desde la evaluación
    }

    // Nuevo constructor con grupo
    public ResultadoEvaluacion(String estudiante, Evaluacion evaluacion, Grupo grupo, double puntajeObtenido, double puntajeTotal) {
        this(estudiante, evaluacion, puntajeObtenido, puntajeTotal);
        this.grupo = grupo;
        this.grupoId = grupo != null ? grupo.getCodigo() : null;
    }

    // Getters
    public String getId() { return id; }
    public String getEstudiante() { return estudiante; }
    public String getProfesorId() { return profesorId; }
    public String getEvaluacionId() { return evaluacionId; }
    public String getGrupoId() { return grupoId; }
    public String getTituloEvaluacion() { return tituloEvaluacion; }
    public double getPuntajeObtenido() { return puntajeObtenido; }
    public double getPuntajeTotal() { return puntajeTotal; }
    public double getNotaPorcentaje() { return notaPorcentaje; }
    public LocalDateTime getFecha() { return fecha; }
    public Grupo getGrupo() { return grupo; }
    public LocalDateTime getFechaRealizacion() { return fecha; }

    // Setters para los nuevos campos
    public void setProfesorId(String profesorId) { this.profesorId = profesorId; }
    public void setGrupoId(String grupoId) { this.grupoId = grupoId; }
    public void setGrupo(Grupo grupo) { 
        this.grupo = grupo; 
        this.grupoId = grupo != null ? grupo.getCodigo() : null;
    }

    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "[" + getFechaFormateada() + "] " + tituloEvaluacion +
               " - " + String.format("%.1f", notaPorcentaje) + "% (" + estudiante + ")";
    }
}