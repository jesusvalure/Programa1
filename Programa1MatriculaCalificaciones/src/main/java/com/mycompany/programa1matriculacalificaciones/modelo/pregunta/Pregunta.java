package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

public abstract class Pregunta {
    protected String descripcion;
    protected int puntos;

    public Pregunta() {}
    public Pregunta(String descripcion, int puntos) {
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public abstract double evaluar(Object respuesta);
    public String getDescripcion() { return descripcion; }
}
