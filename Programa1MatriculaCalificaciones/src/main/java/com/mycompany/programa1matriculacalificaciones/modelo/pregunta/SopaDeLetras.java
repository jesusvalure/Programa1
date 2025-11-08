package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.*;

public class SopaDeLetras extends Pregunta {
    private List<String> palabras;
    private int tamaño;
    private String pregunta;

    public SopaDeLetras(String enunciado, double valor, List<String> palabras, int tamaño, String pregunta) {
        super(enunciado, valor);
        // Normalizar palabras a mayúsculas
        this.palabras = new ArrayList<>();
        for (String palabra : palabras) {
            this.palabras.add(palabra.toUpperCase().replace(" ", ""));
        }
        this.tamaño = tamaño;
        this.pregunta = pregunta;
    }

    public SopaDeLetras(String enunciado, double valor, List<String> palabras, int tamaño) {
        this(enunciado, valor, palabras, tamaño, "Encuentra las siguientes palabras en la sopa de letras:");
    }

    public List<String> getPalabras() { return palabras; }
    public int getTamaño() { return tamaño; }
    public String getPregunta() { return pregunta; }

    @Override
    public String getTipo() { return "Sopa de Letras"; }
}