package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

import java.util.List;

public class SeleccionUnica extends Pregunta {
    private List<String> opciones;
    private int indiceCorrecta;

    public SeleccionUnica() { super(); }

    public SeleccionUnica(String desc, int puntos, List<String> opts, int idx) {
        super(desc, puntos);
        this.opciones = opts;
        this.indiceCorrecta = idx;
    }
    // Getters y setters
    public List<String> getOpciones() { return opciones; }
    public int getIndiceCorrecta() { return indiceCorrecta; }
    

    @Override
    public double evaluar(Object respuesta) {
        if (!(respuesta instanceof Integer)) return 0;
        int r = (Integer) respuesta;
        return r == indiceCorrecta ? puntos : 0;
    }
}
