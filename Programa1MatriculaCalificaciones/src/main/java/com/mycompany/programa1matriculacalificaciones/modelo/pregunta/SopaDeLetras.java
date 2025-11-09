package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.*;

public class SopaDeLetras extends Pregunta {
    private List<String> palabras;
    private int tamaño;

    public SopaDeLetras(String enunciado, double valor, List<String> palabras, int tamaño) {
        super(enunciado, valor);
        // Normalizar palabras a mayúsculas
        this.palabras = new ArrayList<>();
        for (String palabra : palabras) {
            this.palabras.add(palabra.toUpperCase().replace(" ", ""));
        }
        this.tamaño = tamaño;
    }

    public List<String> getPalabras() { return palabras; }
    public int getTamaño() { return tamaño; }

    @Override
    public String getTipo() { return "Sopa de Letras"; }
}