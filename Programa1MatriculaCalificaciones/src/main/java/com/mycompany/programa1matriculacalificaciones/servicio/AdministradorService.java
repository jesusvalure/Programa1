package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Curso;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;


public class AdministradorService {
    private List<Curso> cursos = new ArrayList<>();
    private List<Estudiante> estudiantes = new ArrayList<>();

    public void agregarCurso(Curso c) { cursos.add(c); }
    public List<Curso> listarCursos() { return cursos; }

    public void agregarEstudiante(Estudiante e) { estudiantes.add(e); }
    public List<Estudiante> listarEstudiantes() { return estudiantes; }
}
