package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.List;
import java.util.Map;

public class Pareo extends Pregunta {
    private List<String> columna1;
    private List<String> columna2;
    private Map<String,String> paresCorrectos;

    public Pareo() { super(); }

    @Override
    public double evaluar(Object respuesta) {
        // respuesta esperada: Map<String,String> con las parejas hechas por el estudiante
        return 0;
    }
}
