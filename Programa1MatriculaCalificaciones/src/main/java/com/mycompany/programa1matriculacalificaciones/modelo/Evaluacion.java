package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Evaluacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String titulo;
    private String tipo; 
    private boolean ordenAleatorio;
    private List<String> preguntas = new ArrayList<>();

    public Evaluacion() {}

    public Evaluacion(String id, String titulo, String tipo, boolean ordenAleatorio) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.ordenAleatorio = ordenAleatorio;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getTipo() { return tipo; }
    public boolean isOrdenAleatorio() { return ordenAleatorio; }
    public List<String> getPreguntas() { return preguntas; }

    public void agregarPregunta(String pregunta) {
        preguntas.add(pregunta);
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + titulo + (ordenAleatorio ? " (aleatoria)" : "");
    }
}
