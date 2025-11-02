package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Curso;

public class CursoService {
    private static final String RUTA = "datos/matriculaycalificaciones/cursos.dat";
    private ArchivoService<Curso> archivo = new ArchivoService<>();
    private List<Curso> cursos;

    public CursoService() {
        cursos = archivo.cargarLista(RUTA);
        if (cursos == null) {
            cursos = new ArrayList<>();
        }
    }

    public void agregar(Curso c) {
        cursos.add(c);
        archivo.guardarLista(cursos, RUTA);
    }

    public List<Curso> listar() {
        return new ArrayList<>(cursos);
    }

    public boolean eliminar(String codigo) {
        boolean eliminado = cursos.removeIf(c -> c.getCodigo().equals(codigo));
        if (eliminado) archivo.guardarLista(cursos, RUTA);
        return eliminado;
    }

    public Curso buscar(String codigo) {
        for (Curso c : cursos) {
            if (c.getCodigo().equals(codigo)) return c;
        }
        return null;
    }
    
    public void actualizar(Curso cursoActualizado) {
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getCodigo().equals(cursoActualizado.getCodigo())) {
                cursos.set(i, cursoActualizado);
                archivo.guardarLista(cursos, RUTA);
                break;
            }
        }
    }
}
