package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.*;

public class Pareo extends Pregunta {
    private Map<String, String> pares; // Ejemplo: "Costa Rica" → "San José"

    public Pareo(String enunciado, double valor, Map<String, String> pares) {
        super(enunciado, valor);
        this.pares = new LinkedHashMap<>(pares);
    }

    public Map<String, String> getPares() { return pares; }

    @Override
    public String getTipo() { return "Pareo"; }
}
