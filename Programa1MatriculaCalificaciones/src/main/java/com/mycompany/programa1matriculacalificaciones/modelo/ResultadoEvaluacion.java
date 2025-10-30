package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;

public class ResultadoEvaluacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Estudiante estudiante;
    private Evaluacion evaluacion;
    private double nota;

    public ResultadoEvaluacion(Estudiante estudiante, Evaluacion evaluacion, double nota) {
        this.estudiante = estudiante;
        this.evaluacion = evaluacion;
        this.nota = nota;
    }

    public Estudiante getEstudiante() { return estudiante; }
    public Evaluacion getEvaluacion() { return evaluacion; }
    public double getNota() { return nota; }

    @Override
    public String toString() {
        return estudiante.getNombre() + " - " + evaluacion.getTitulo() + ": " + nota;
    }
}
