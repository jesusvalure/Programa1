package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.*;

public class SopaDeLetras extends Pregunta {
    private List<String> palabras;
    private int tamaño;

    public SopaDeLetras(String enunciado, double valor, List<String> palabras, int tamaño) {
        super(enunciado, valor);
        this.palabras = new ArrayList<>(palabras);
        this.tamaño = tamaño;
    }

    public List<String> getPalabras() { return palabras; }
    public int getTamaño() { return tamaño; }

    @Override
    public String getTipo() { return "Sopa de Letras"; }
}
