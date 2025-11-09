package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.Usuario;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class UsuarioService {
    private static final String RUTA = PathConfig.BASE_DATA_DIR + "/usuarios.dat";
    private ArchivoService<Usuario> archivo = new ArchivoService<>();
    private List<Usuario> usuarios;
    
    public UsuarioService() {
        usuarios = archivo.cargarLista(RUTA);
        if (usuarios == null || usuarios.isEmpty()) {
            usuarios = new ArrayList<>();
            // Crear usuario admin por defecto si no existe
            crearUsuarioAdminPorDefecto();
        } else {
            // Verificar si existe el admin, si no existe crearlo
            boolean existeAdmin = usuarios.stream()
                .anyMatch(u -> u.getId().equals("admin") && u.getRol().equals("Administrador"));
            if (!existeAdmin) {
                crearUsuarioAdminPorDefecto();
            }
        }
    }
    
    private void crearUsuarioAdminPorDefecto() {
        // Verificar que no exista ya el admin antes de agregarlo
        boolean existeAdmin = usuarios.stream()
            .anyMatch(u -> u.getId().equals("admin"));
        if (!existeAdmin) {
            String adminPass = com.mycompany.programa1matriculacalificaciones.util.Encriptador.encriptar("1234");
            usuarios.add(new Usuario("admin", adminPass, "Administrador"));
            archivo.guardarLista(usuarios, RUTA);
        }
    }
    
    public void agregarUsuario(Usuario usuario) {
        if (buscarPorId(usuario.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con este ID");
        }
        usuarios.add(usuario);
        archivo.guardarLista(usuarios, RUTA);
    }
    
    public Usuario buscarPorId(String id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }
    
    public void eliminarUsuario(String id) {
        usuarios.removeIf(u -> u.getId().equals(id));
        archivo.guardarLista(usuarios, RUTA);
    }
    
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }
    
    public Map<String, String> obtenerUsuariosMap() {
        Map<String, String> map = new HashMap<>();
        for (Usuario u : usuarios) {
            map.put(u.getId(), u.getContrasenaEncriptada());
        }
        return map;
    }
    
    public Map<String, String> obtenerRolesMap() {
        Map<String, String> map = new HashMap<>();
        for (Usuario u : usuarios) {
            map.put(u.getId(), u.getRol());
        }
        return map;
    }

    /**
     * Actualiza la contraseña encriptada de un usuario existente.
     * @param id identificador del usuario
     * @param nuevaContrasenaEncriptada contraseña ya encriptada
     * @return true si se actualizó, false si no se encontró usuario
     */
    public boolean actualizarContrasena(String id, String nuevaContrasenaEncriptada) {
        Usuario u = buscarPorId(id);
        if (u == null) return false;
        u.setContrasenaEncriptada(nuevaContrasenaEncriptada);
        archivo.guardarLista(usuarios, RUTA);
        return true;
    }
}

