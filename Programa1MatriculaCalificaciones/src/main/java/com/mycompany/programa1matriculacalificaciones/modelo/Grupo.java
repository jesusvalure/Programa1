package com.mycompany.programa1matriculacalificaciones.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Grupo {
    private int numeroGrupo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Curso curso;
    private Profesor profesor;
    private List<Estudiante> estudiantes = new ArrayList<>();
    private List<EvaluacionAsignada> evaluacionesAsignadas = new ArrayList<>();

    public Grupo() {}

    public Grupo(int numero, Curso curso) {
        this.numeroGrupo = numero;
        this.curso = curso;
    }

    public void agregarEstudiante(Estudiante e) { estudiantes.add(e); }
    public void asignarProfesor(Profesor p) { this.profesor = p; }
    public List<Estudiante> getEstudiantes() { return estudiantes; }
}
