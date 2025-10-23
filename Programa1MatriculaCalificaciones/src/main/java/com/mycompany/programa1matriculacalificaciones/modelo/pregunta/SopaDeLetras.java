package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.Map;

public class SopaDeLetras extends Pregunta {
    private Map<String,String> enunciados; // pista -> palabra
    private char[][] cuadrilla;

    public SopaDeLetras() { super(); }

    @Override
    public double evaluar(Object respuesta) {
        // respuesta esperada: List<String> con palabras encontradas
        return 0;
    }
}
