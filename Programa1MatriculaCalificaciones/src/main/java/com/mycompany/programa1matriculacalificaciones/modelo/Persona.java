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
    protected String fechaNacimiento;
    protected String genero;

    public Persona() {
        
    }

    public Persona(String nombre, String apellido1, String identificacion) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.identificacion = identificacion;
    }
    
    public Persona(String nombre, String apellido1, String apellido2, String identificacion, 
                  String telefono, String correo, String direccion, String fechaNacimiento, String genero) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
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
    
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    
    public String getApellido2() {
        return apellido2;
    }
    
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
}