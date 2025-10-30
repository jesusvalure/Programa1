package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion;

public class EvaluacionService {
    private static final String RUTA = "datos/matriculaycalificaciones/evaluaciones.dat";
    private ArchivoService<Evaluacion> archivo = new ArchivoService<>();
    private List<Evaluacion> evaluaciones;

    public EvaluacionService() {
        evaluaciones = archivo.cargarLista(RUTA);
    }

    public void agregar(Evaluacion e) {
        evaluaciones.add(e);
        archivo.guardarLista(evaluaciones, RUTA);
    }

    public List<Evaluacion> listar() {
        return new ArrayList<>(evaluaciones);
    }

    public boolean eliminar(String id) {
        boolean eliminado = evaluaciones.removeIf(e -> e.getId().equals(id));
        if (eliminado) archivo.guardarLista(evaluaciones, RUTA);
        return eliminado;
    }

    public Evaluacion buscar(String id) {
        for (Evaluacion e : evaluaciones) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }
}
