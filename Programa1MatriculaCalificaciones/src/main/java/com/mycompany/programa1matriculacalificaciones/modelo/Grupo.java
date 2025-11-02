package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private Curso curso;
    private Profesor profesor;
    private List<Estudiante> estudiantes = new ArrayList<>();

    public Grupo(String codigo, Curso curso, Profesor profesor) {
        this.codigo = codigo;
        this.curso = curso;
        this.profesor = profesor;
    }

    public String getCodigo() { return codigo; }
    public Curso getCurso() { return curso; }
    public Profesor getProfesor() { return profesor; }
    public List<Estudiante> getEstudiantes() { return estudiantes; }

    public void agregarEstudiante(Estudiante e) {
        if (!estudiantes.contains(e)) estudiantes.add(e);
    }

    @Override
    public String toString() {
        return "Grupo " + codigo + " - " + curso.getNombre() + " (" + profesor.getNombre() + ")";
    }

}
