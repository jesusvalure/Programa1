package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.Matricula;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class MatriculaService {
    private static final String RUTA = PathConfig.BASE_DATA_DIR + "/matriculas.dat";
    private ArchivoService<Matricula> archivo = new ArchivoService<>();
    private List<Matricula> matriculas;

    public MatriculaService() {
        matriculas = archivo.cargarLista(RUTA);
        if (matriculas == null) {
            matriculas = new ArrayList<>();
        }
    }

    /**
     * Agrega una matrícula si no existe ya la misma matrícula (mismo estudiante en mismo grupo).
     * @param m Matrícula a agregar
     * @return true si se agregó, false si ya existía
     */
    public boolean agregar(Matricula m) {
        if (m == null || m.getEstudiante() == null || m.getGrupo() == null) return false;
        String estudianteId = m.getEstudiante().getIdentificacion();
        String grupoCodigo = m.getGrupo().getCodigo();
        for (Matricula existing : matriculas) {
            if (existing.getEstudiante() != null && existing.getGrupo() != null
                    && estudianteId != null && grupoCodigo != null
                    && estudianteId.equals(existing.getEstudiante().getIdentificacion())
                    && grupoCodigo.equals(existing.getGrupo().getCodigo())) {
                // Ya existe matrícula para este estudiante en este grupo
                return false;
            }
        }
        matriculas.add(m);
        archivo.guardarLista(matriculas, RUTA);
        return true;
    }

    public List<Matricula> listar() {
        return new ArrayList<>(matriculas);
    }
}

