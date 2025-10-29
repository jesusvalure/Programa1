package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeleccionMultiple extends Pregunta {
    private List<String> opciones;
    private List<Integer> indicesCorrectas;

    public SeleccionMultiple() { super(); }

    public SeleccionMultiple(String desc, int puntos, List<String> opts, List<Integer> idxs) {
        super(desc, puntos);
        this.opciones = opts;
        this.indicesCorrectas = idxs;
    }

    // Getters y setters
    public List<String> getOpciones() { return opciones; }
    public List<Integer> getIndicesCorrectas() { return indicesCorrectas; }
    

    @Override
    public double evaluar(Object respuesta) {
        if (!(respuesta instanceof List)) return 0;
        @SuppressWarnings("unchecked")
        List<Integer> respuestas = (List<Integer>) respuesta;
        Set<Integer> correctas = new HashSet<>(indicesCorrectas);
        Set<Integer> dadas = new HashSet<>(respuestas);

        // punto simple: si coinciden exacto, da el total, si no, 0.
        return correctas.equals(dadas) ? puntos : 0;
    }
}
