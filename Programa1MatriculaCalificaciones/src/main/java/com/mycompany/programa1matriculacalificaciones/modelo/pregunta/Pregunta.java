package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.io.Serializable;
import java.util.UUID;

public abstract class Pregunta implements Serializable {
    private String id;
    private String enunciado;
    private double valor;

    public Pregunta(String enunciado, double valor) {
        this.id = UUID.randomUUID().toString();
        this.enunciado = enunciado;
        this.valor = valor;
    }

    public String getId() { return id; }
    public String getEnunciado() { return enunciado; }
    public double getValor() { return valor; }

    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public void setValor(double valor) { this.valor = valor; }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "[" + getTipo() + "] " + enunciado + " (" + valor + " pts)";
    }
}
