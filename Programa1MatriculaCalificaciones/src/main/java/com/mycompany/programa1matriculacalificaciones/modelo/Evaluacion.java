package com.mycompany.programa1matriculacalificaciones.modelo;

import com.mycompany.programa1matriculacalificaciones.modelo.pregunta.Pregunta;
import java.io.Serializable;
import java.util.*;

public class Evaluacion implements Serializable {
    private String id;
    private String titulo;
    private String tipo;
    private boolean ordenAleatorio;
    // Tiempo límite en minutos (0 = sin límite)
    private int tiempoMinutos = 0;
    private List<Pregunta> preguntas;

    public Evaluacion(String titulo, String tipo, boolean ordenAleatorio) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.tipo = tipo;
        this.ordenAleatorio = ordenAleatorio;
        this.preguntas = new ArrayList<>();
    }

    public Evaluacion(String id, String titulo, String tipo, boolean ordenAleatorio) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.ordenAleatorio = ordenAleatorio;
        this.preguntas = new ArrayList<>();
    }

    // Tiempo límite opcional
    public int getTiempoMinutos() { return tiempoMinutos; }
    public void setTiempoMinutos(int tiempoMinutos) { this.tiempoMinutos = tiempoMinutos; }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getTipo() { return tipo; }
    public boolean isOrdenAleatorio() { return ordenAleatorio; }
    public List<Pregunta> getPreguntas() { return preguntas; }

    public void agregarPregunta(Pregunta p) { preguntas.add(p); }
    public void eliminarPregunta(String id) {
        preguntas.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public String toString() {
        return titulo + " (" + tipo + ") [" + preguntas.size() + " preguntas]";
    }
}
