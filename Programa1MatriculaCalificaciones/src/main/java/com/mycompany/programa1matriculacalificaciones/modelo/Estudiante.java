package com.mycompany.programa1matriculacalificaciones.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona {
    private static final long serialVersionUID = 1L;

    private List<String> temasInteres = new ArrayList<>();
    private LocalDate fechaRegistro = LocalDate.now();
    private String carrera;
    private String nivelEducativo;
    private String institucionProcedencia;

    public Estudiante() { super(); }

    public Estudiante(String nombre, String apellido1, String identificacion) {
        super(nombre, apellido1, identificacion);
    }
    
    public Estudiante(String nombre, String apellido1, String apellido2, String identificacion, 
                     String telefono, String correo, String direccion, String fechaNacimiento, String genero,
                     String carrera, String nivelEducativo, String institucionProcedencia) {
        super(nombre, apellido1, apellido2, identificacion, telefono, correo, direccion, fechaNacimiento, genero);
        this.carrera = carrera;
        this.nivelEducativo = nivelEducativo;
        this.institucionProcedencia = institucionProcedencia;
    }

    public LocalDate getFechaRegistro() { return fechaRegistro; }

    public void agregarTemaInteres(String tema) {
        temasInteres.add(tema);
    }

    public List<String> getTemasInteres() {
        return temasInteres;
    }
    
    public String getCarrera() {
        return carrera;
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    public String getNivelEducativo() {
        return nivelEducativo;
    }
    
    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }
    
    public String getInstitucionProcedencia() {
        return institucionProcedencia;
    }
    
    public void setInstitucionProcedencia(String institucionProcedencia) {
        this.institucionProcedencia = institucionProcedencia;
    }
}
