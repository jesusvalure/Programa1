package com.mycompany.programa1matriculacalificaciones.modelo.pregunta;

public class FalsoVerdadero extends Pregunta {
    private boolean respuestaCorrecta;

    public FalsoVerdadero(String enunciado, double valor, boolean respuestaCorrecta) {
        super(enunciado, valor);
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public boolean isRespuestaCorrecta() { return respuestaCorrecta; }

    @Override
    public String getTipo() { return "Falso/Verdadero"; }
}
