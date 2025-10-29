package com.mycompany.programa1matriculacalificaciones.modelo;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.pregunta.Pregunta;

public class Evaluacion {
    private int idEvaluacion;
    private String nombre;
    private String instrucciones;
    private List<String> objetivos = new ArrayList<>();
    private int duracion; // minutos
    private boolean aleatorioPreguntas;
    private boolean aleatorioOpciones;
    private List<Pregunta> preguntas = new ArrayList<>();

    public Evaluacion() {}

    public Evaluacion(int id, String nombre) {
        this.idEvaluacion = id;
        this.nombre = nombre;
    }

    public void agregarPregunta(Pregunta p) { preguntas.add(p); }
    public List<Pregunta> getPreguntas() { return preguntas; }
    public int getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(int idEvaluacion) { this.idEvaluacion = idEvaluacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }
    public List<String> getObjetivos() { return objetivos; }
    public void setObjetivos(List<String> objetivos) { this.objetivos = objetivos; }
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public boolean isAleatorioPreguntas() { return aleatorioPreguntas; }
    public void setAleatorioPreguntas(boolean aleatorioPreguntas) { this.aleatorioPreguntas = aleatorioPreguntas; }
    public boolean isAleatorioOpciones() { return aleatorioOpciones; }
    public void setAleatorioOpciones(boolean aleatorioOpciones) { this.aleatorioOpciones = aleatorioOpciones; }
}
