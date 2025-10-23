package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

public class FalsoVerdadero extends Pregunta {
    private boolean respuestaCorrecta;

    public FalsoVerdadero() { super(); }

    public FalsoVerdadero(String desc, int puntos, boolean correcta) {
        super(desc, puntos);
        this.respuestaCorrecta = correcta;
    }

    @Override
    public double evaluar(Object respuesta) {
        if (!(respuesta instanceof Boolean)) return 0;
        boolean r = (Boolean) respuesta;
        return r == respuestaCorrecta ? puntos : 0;
    }
}
