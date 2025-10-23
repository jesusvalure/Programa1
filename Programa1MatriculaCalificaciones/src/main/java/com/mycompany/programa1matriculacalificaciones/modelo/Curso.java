package com.mycompany.programa1matriculacalificaciones.modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String idCurso;
    private String nombre;
    private String descripcion;
    private String modalidad;
    private int horasPorDia;
    private int minEstudiantes;
    private int maxEstudiantes;
    private String tipo;
    private int notaMinima;
    private List<Grupo> grupos = new ArrayList<>();

    public Curso() {}

    public Curso(String idCurso, String nombre) {
        this.idCurso = idCurso;
        this.nombre = nombre;
    }

    public void agregarGrupo(Grupo g) { grupos.add(g); }
    public List<Grupo> getGrupos() { return grupos; }
}
