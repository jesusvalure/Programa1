package com.mycompany.programa1matriculacalificaciones.modelo;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona {
    private List<String> titulos = new ArrayList<>();
    private List<String> certificaciones = new ArrayList<>();
    private List<Grupo> gruposAsignados = new ArrayList<>();
    private String profesorId;
    private String especialidad;
    private String gradoAcademico;
    private int aniosExperiencia;

    public Profesor() { super(); }
    
    public Profesor(String nombre, String apellido1, String identificacion) {
        super(nombre, apellido1, identificacion);
        this.profesorId = identificacion; // Usar la identificación como ID del profesor
    }
    
    public Profesor(String nombre, String apellido1, String apellido2, String identificacion, 
                   String telefono, String correo, String direccion, String fechaNacimiento, String genero,
                   String especialidad, String gradoAcademico, int aniosExperiencia) {
        super(nombre, apellido1, apellido2, identificacion, telefono, correo, direccion, fechaNacimiento, genero);
        this.profesorId = identificacion; // Usar la identificación como ID del profesor
        this.especialidad = especialidad;
        this.gradoAcademico = gradoAcademico;
        this.aniosExperiencia = aniosExperiencia;
    }

    public void asignarGrupo(Grupo g) { 
        gruposAsignados.add(g);
        if (g != null) {
            g.setProfesorId(this.profesorId); // Actualizar el profesorId en el grupo
        }
    }
    
    public List<Grupo> getGruposAsignados() { return gruposAsignados; }
    public void agregarTitulo(String t) { titulos.add(t); }
    public void agregarCertificacion(String c) { certificaciones.add(c); }
    public List<String> getTitulos() { return titulos; }
    public List<String> getCertificaciones() { return certificaciones; }

    public String getProfesorId() {
        return profesorId != null ? profesorId : getIdentificacion();
    }

    public void setProfesorId(String profesorId) {
        this.profesorId = profesorId;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getGradoAcademico() {
        return gradoAcademico;
    }
    
    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }
    
    public int getAniosExperiencia() {
        return aniosExperiencia;
    }
    
    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido1() + " (" + getProfesorId() + ")";
    }
}