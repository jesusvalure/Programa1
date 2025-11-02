package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.Matricula;
import java.util.*;

public class MatriculaService {
    private static final String RUTA = "datos/matriculaycalificaciones/matriculas.dat";
    private ArchivoService<Matricula> archivo = new ArchivoService<>();
    private List<Matricula> matriculas;

    public MatriculaService() {
        matriculas = archivo.cargarLista(RUTA);
        if (matriculas == null) {
            matriculas = new ArrayList<>();
        }
    }

    public void agregar(Matricula m) {
        matriculas.add(m);
        archivo.guardarLista(matriculas, RUTA);
    }

    public List<Matricula> listar() {
        return new ArrayList<>(matriculas);
    }
}

