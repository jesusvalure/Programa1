package com.mycompany.programa1matriculacalificaciones.util;

public class SesionActual {
    private static String usuarioId;
    private static String rol;
    
    public static void iniciarSesion(String id, String rolUsuario) {
        usuarioId = id;
        rol = rolUsuario;
    }
    
    public static void cerrarSesion() {
        usuarioId = null;
        rol = null;
    }
    
    public static String getUsuarioId() {
        return usuarioId;
    }
    
    public static String getRol() {
        return rol;
    }
    
    public static boolean estaLogueado() {
        return usuarioId != null;
    }
}

