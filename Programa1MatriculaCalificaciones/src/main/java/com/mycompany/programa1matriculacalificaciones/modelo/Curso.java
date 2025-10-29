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

    public String getIdCurso() { return idCurso; }
    public void setIdCurso(String idCurso) { this.idCurso = idCurso; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    public int getHorasPorDia() { return horasPorDia; }
    public void setHorasPorDia(int horasPorDia) { this.horasPorDia = horasPorDia; }
    public int getMinEstudiantes() { return minEstudiantes; }
    public void setMinEstudiantes(int minEstudiantes) { this.minEstudiantes = minEstudiantes; }
    public int getMaxEstudiantes() { return maxEstudiantes; }
    public void setMaxEstudiantes(int maxEstudiantes) { this.maxEstudiantes = maxEstudiantes; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getNotaMinima() { return notaMinima; }
    public void setNotaMinima(int notaMinima) { this.notaMinima = notaMinima; }
    
}
