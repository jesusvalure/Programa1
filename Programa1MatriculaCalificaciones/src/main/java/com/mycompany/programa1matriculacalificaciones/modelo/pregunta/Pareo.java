package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.List;
import java.util.Map;

public class Pareo extends Pregunta {
    private List<String> columna1;
    private List<String> columna2;
    private Map<String,String> paresCorrectos;

    public Pareo() { super(); }

    public Pareo(int id, String enunciado, List<String> col1, List<String> col2, Map<String,String> pares) {
        this.columna1 = col1;
        this.columna2 = col2;
        this.paresCorrectos = pares;
    }
    public List<String> getColumna1() { return columna1; }
    public List<String> getColumna2() { return columna2; }
    public Map<String,String> getParesCorrectos() { return paresCorrectos; }

    @Override
    public double evaluar(Object respuesta) {
        // respuesta esperada: Map<String,String> con las parejas hechas por el estudiante
        if (!(respuesta instanceof Map)) return 0;
        @SuppressWarnings("unchecked")
        Map<String,String> dadas = (Map<String,String>) respuesta;
        int correctas = 0;
        for (Map.Entry<String,String> e : paresCorrectos.entrySet()) {
            String clave = e.getKey();
            String valor = e.getValue();
            if (valor.equals(dadas.get(clave))) correctas++;
        }
        // puntaje proporcional
        if (paresCorrectos.size() == 0) return 0;
        return ((double) correctas / paresCorrectos.size()) * puntos;
    }

}
