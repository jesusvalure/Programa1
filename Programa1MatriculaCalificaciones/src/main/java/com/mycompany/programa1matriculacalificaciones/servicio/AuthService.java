package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.HashMap;
import java.util.Map;
import com.mycompany.programa1matriculacalificaciones.util.Encriptador;

public class AuthService {
    private Map<String,String> usuarios = new HashMap<>();
    private Map<String,String> roles = new HashMap<>();

    public AuthService() {
        // ejemplo de usuarios temporales
        usuarios.put("admin", Encriptador.encriptar("1234"));
        roles.put("admin", "Administrador");

        usuarios.put("prof1", Encriptador.encriptar("abcd"));
        roles.put("prof1", "Profesor");

        usuarios.put("est1", Encriptador.encriptar("pass"));
        roles.put("est1", "Estudiante");
    }

    public String autenticar(String id, String contrasena) {
        String enc = Encriptador.encriptar(contrasena);
        if (!usuarios.containsKey(id)) return null;
        if (usuarios.get(id).equals(enc)) return roles.get(id);
        return null;
    }
}
