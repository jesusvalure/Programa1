package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String contrasenaEncriptada;
    private String rol;
    
    public Usuario() {}
    
    public Usuario(String id, String contrasenaEncriptada, String rol) {
        this.id = id;
        this.contrasenaEncriptada = contrasenaEncriptada;
        this.rol = rol;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getContrasenaEncriptada() { return contrasenaEncriptada; }
    public void setContrasenaEncriptada(String contrasenaEncriptada) { this.contrasenaEncriptada = contrasenaEncriptada; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

