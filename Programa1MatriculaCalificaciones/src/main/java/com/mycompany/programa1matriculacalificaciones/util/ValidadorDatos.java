package com.mycompany.programa1matriculacalificaciones.util;

public class ValidadorDatos {
    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.contains("@") && correo.length() >= 5;
    }

    public static boolean esIdentificacionValida(String id) {
        return id != null && id.length() >= 9;
    }
}
