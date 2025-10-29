package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.Map;
import java.util.List;

public class SopaDeLetras extends Pregunta {
    private Map<String,String> enunciados; // pista -> palabra
    private char[][] cuadrilla;

    public SopaDeLetras() { super(); }

    public SopaDeLetras(String desc, int puntos, Map<String,String> ens, char[][] cuad) {
        super(desc, puntos);
        this.enunciados = ens;
        this.cuadrilla = cuad;
    }
    // Getters y setters
    public Map<String,String> getEnunciados() { return enunciados; }
    public char[][] getCuadrilla() { return cuadrilla; }

    @Override
    public double evaluar(Object respuesta) {
        // respuesta esperada: List<String> con palabras encontradas
        if (!(respuesta instanceof List)) return 0;
        @SuppressWarnings("unchecked")
        List<String> encontradas = (List<String>) respuesta;
        int total = enunciados.size();
        int aciertos = 0;
        for (String palabra : enunciados.values()) {
            if (encontradas.contains(palabra)) aciertos++;
        }
        if (total == 0) return 0;
        return ((double) aciertos / total) * puntos;
    }
}
