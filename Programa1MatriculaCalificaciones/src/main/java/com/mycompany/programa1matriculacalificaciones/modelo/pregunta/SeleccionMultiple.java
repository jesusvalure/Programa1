package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.List;

public class SeleccionMultiple extends Pregunta {
    private List<String> opciones;
    private List<Integer> indicesCorrectas;

    public SeleccionMultiple() { super(); }

    @Override
    public double evaluar(Object respuesta) {
        // dejar la implementación real para después
        return 0;
    }
}
