package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.EvaluacionAsignada;
import com.mycompany.programa1matriculacalificaciones.modelo.Grupo;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class EvaluacionAsignadaService {
    private static final String RUTA = PathConfig.BASE_DATA_DIR + "/evaluaciones_asignadas.dat";
    private ArchivoService<EvaluacionAsignada> archivo = new ArchivoService<>();
    private List<EvaluacionAsignada> asignaciones;

    public EvaluacionAsignadaService() {
        asignaciones = archivo.cargarLista(RUTA);
        if (asignaciones == null) {
            asignaciones = new ArrayList<>();
        }
    }

    public void agregar(EvaluacionAsignada ea) {
        asignaciones.add(ea);
        archivo.guardarLista(asignaciones, RUTA);
    }

    public List<EvaluacionAsignada> listar() {
        return new ArrayList<>(asignaciones);
    }

    public List<EvaluacionAsignada> listarPorGrupo(Grupo grupo) {
        List<EvaluacionAsignada> resultado = new ArrayList<>();
        for (EvaluacionAsignada ea : asignaciones) {
            if (ea.getGrupo().getCodigo().equals(grupo.getCodigo())) {
                resultado.add(ea);
            }
        }
        return resultado;
    }
}

