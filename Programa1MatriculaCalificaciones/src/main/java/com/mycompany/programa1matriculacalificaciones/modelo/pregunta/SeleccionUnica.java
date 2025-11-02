package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.*;

public class SeleccionUnica extends Pregunta {
    private List<String> opciones;
    private int indiceCorrecto;

    public SeleccionUnica(String enunciado, double valor, List<String> opciones, int indiceCorrecto) {
        super(enunciado, valor);
        this.opciones = new ArrayList<>(opciones);
        this.indiceCorrecto = indiceCorrecto;
    }

    public List<String> getOpciones() { return opciones; }
    public int getIndiceCorrecto() { return indiceCorrecto; }

    @Override
    public String getTipo() { return "Selección Única"; }
}
