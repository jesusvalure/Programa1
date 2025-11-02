package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.*;

public class SeleccionMultiple extends Pregunta {
    private List<String> opciones;
    private Set<Integer> indicesCorrectos;

    public SeleccionMultiple(String enunciado, double valor, List<String> opciones, Set<Integer> indicesCorrectos) {
        super(enunciado, valor);
        this.opciones = new ArrayList<>(opciones);
        this.indicesCorrectos = new HashSet<>(indicesCorrectos);
    }

    public List<String> getOpciones() { return opciones; }
    public Set<Integer> getIndicesCorrectos() { return indicesCorrectos; }

    @Override
    public String getTipo() { return "Selección Múltiple"; }
}
