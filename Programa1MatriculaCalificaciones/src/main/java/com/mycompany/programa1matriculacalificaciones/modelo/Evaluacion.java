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
}
