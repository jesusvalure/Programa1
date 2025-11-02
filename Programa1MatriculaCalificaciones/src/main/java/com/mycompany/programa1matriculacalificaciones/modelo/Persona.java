package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;

public abstract class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String nombre;
    protected String apellido1;
    protected String apellido2;
    protected String identificacion;
    protected String telefono;
    protected String correo;
    protected String direccion;
    protected String contrasenaEncriptada;

    public Persona() {
        
    }

    public Persona(String nombre, String apellido1, String identificacion) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.identificacion = identificacion;
    }

    public String getIdentificacion() { 
        return identificacion; 
    }
    public String getNombre() { 
        return nombre; 
    }

    public void setContrasenaEncriptada(String c) { 
        this.contrasenaEncriptada = c; 
    }

    // Otros getters y setters según sea necesario

    public String getApellido1() {
        return apellido1;
    }
}