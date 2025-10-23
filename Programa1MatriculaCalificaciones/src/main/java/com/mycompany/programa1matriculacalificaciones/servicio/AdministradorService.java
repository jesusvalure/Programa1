package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Curso;

public class AdministradorService {
    private List<Curso> cursos = new ArrayList<>();

    public void agregarCurso(Curso c) { cursos.add(c); }
    public List<Curso> listarCursos() { return cursos; }
}
