package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
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
    public LocalDate getFechaMatricula() { return fechaMatricula; }
    
}
