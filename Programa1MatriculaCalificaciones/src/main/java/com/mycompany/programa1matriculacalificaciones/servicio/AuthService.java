package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.util.Encriptador;
import com.mycompany.programa1matriculacalificaciones.modelo.Usuario;

public class AuthService {
    private UsuarioService usuarioService = new UsuarioService();

    public AuthService() {
        // Los usuarios se cargan desde archivo
    }

    public String autenticar(String id, String contrasena) {
        if (id == null || contrasena == null) return null;
        
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) return null;
        
        String enc = Encriptador.encriptar(contrasena);
        if (usuario.getContrasenaEncriptada().equals(enc)) {
            return usuario.getRol();
        }
        return null;
    }

    //login
    public String login(String id, String contrasena) {
        return autenticar(id, contrasena);
    }
    
    public void crearUsuario(String id, String contrasena, String rol) {
        String contrasenaEncriptada = Encriptador.encriptar(contrasena);
        Usuario usuario = new Usuario(id, contrasenaEncriptada, rol);
        usuarioService.agregarUsuario(usuario);
    }
    
    public void eliminarUsuario(String id) {
        usuarioService.eliminarUsuario(id);
    }
}
