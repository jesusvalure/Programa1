package com.mycompany.programa1matriculacalificaciones.modelo;

import java.time.LocalDate;

public class Matricula {
    private Estudiante estudiante;
    private Grupo grupo;
    private LocalDate fechaMatricula;

    public Matricula() {}

    public Matricula(Estudiante e, Grupo g) {
        this.estudiante = e;
        this.grupo = g;
        this.fechaMatricula = LocalDate.now();
    }

    public Estudiante getEstudiante() { return estudiante; }
    public Grupo getGrupo() { return grupo; }
}
