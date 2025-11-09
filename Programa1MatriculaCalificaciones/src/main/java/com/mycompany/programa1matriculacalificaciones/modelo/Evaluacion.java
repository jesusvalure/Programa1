package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.mycompany.programa1matriculacalificaciones.modelo.pregunta.Pregunta; // Importar Pregunta

public class Evaluacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String titulo;
    private String tipo;
    private boolean ordenAleatorio;
    private int tiempoMinutos;
    private String profesorId;
    private List<Pregunta> preguntas = new ArrayList<>();

    // Constructor para nueva evaluación
    public Evaluacion(String titulo, String tipo, boolean ordenAleatorio) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.tipo = tipo;
        this.ordenAleatorio = ordenAleatorio;
        this.tiempoMinutos = 0;
    }

    // Constructor para evaluación existente
    public Evaluacion(String id, String titulo, String tipo, boolean ordenAleatorio) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.ordenAleatorio = ordenAleatorio;
        this.tiempoMinutos = 0;
    }

    // Constructor para uso en combobox
    public Evaluacion(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = "Mixta";
        this.ordenAleatorio = false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getTipo() { return tipo; }
    public boolean isOrdenAleatorio() { return ordenAleatorio; }
    public int getTiempoMinutos() { return tiempoMinutos; }
    public String getProfesorId() { return profesorId; }
    public List<Pregunta> getPreguntas() { return preguntas; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setOrdenAleatorio(boolean ordenAleatorio) { this.ordenAleatorio = ordenAleatorio; }
    public void setTiempoMinutos(int tiempoMinutos) { this.tiempoMinutos = tiempoMinutos; }
    public void setProfesorId(String profesorId) { this.profesorId = profesorId; }
    public void setPreguntas(List<Pregunta> preguntas) { this.preguntas = preguntas; }

    // Métodos para gestionar preguntas
    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    public void eliminarPregunta(String preguntaId) {
        preguntas.removeIf(p -> p.getId().equals(preguntaId));
    }

    public Pregunta obtenerPregunta(String preguntaId) {
        for (Pregunta p : preguntas) {
            if (p.getId().equals(preguntaId)) {
                return p;
            }
        }
        return null;
    }

    public double getPuntajeTotal() {
        return preguntas.stream().mapToDouble(Pregunta::getValor).sum();
    }

    @Override
    public String toString() {
        return titulo + " (" + tipo + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Evaluacion that = (Evaluacion) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}