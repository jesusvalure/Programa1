package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private Curso curso;
    private Profesor profesor;
    private String profesorId; // Nuevo campo para facilitar el filtrado
    private List<Estudiante> estudiantes = new ArrayList<>();

    public Grupo(String codigo, Curso curso, Profesor profesor) {
        this.codigo = codigo;
        this.curso = curso;
        this.profesor = profesor;
        this.profesorId = profesor != null ? profesor.getIdentificacion() : null;
    }

    // Nuevo constructor para uso en combobox
    public Grupo(String codigo, String nombre, Curso curso) {
        this.codigo = codigo;
        this.curso = curso;
    }

    public String getCodigo() { return codigo; }
    public Curso getCurso() { return curso; }
    public Profesor getProfesor() { return profesor; }
    public String getProfesorId() { return profesorId; } // Nuevo getter
    public List<Estudiante> getEstudiantes() { return estudiantes; }

    public void setProfesorId(String profesorId) {
        this.profesorId = profesorId;
    }

    public void agregarEstudiante(Estudiante e) {
        if (!estudiantes.contains(e)) estudiantes.add(e);
    }

    @Override
    public String toString() {
        if (curso != null && profesor != null) {
            return "Grupo " + codigo + " - " + curso.getNombre() + " (" + profesor.getNombre() + ")";
        } else if (curso != null) {
            return "Grupo " + codigo + " - " + curso.getNombre();
        } else {
            return "Grupo " + codigo;
        }
    }
}