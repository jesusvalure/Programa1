package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.Profesor;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class ProfesorCRUDService {
    private static final String RUTA = PathConfig.BASE_DATA_DIR + "/profesores.dat";
    private ArchivoService<Profesor> archivo = new ArchivoService<>();
    private List<Profesor> profesores;

    public ProfesorCRUDService() {
        profesores = archivo.cargarLista(RUTA);
        if (profesores == null) {
            profesores = new ArrayList<>();
        }
    }

    /**
     * Agrega un profesor si no existe otro con la misma identificación.
     * @param p Profesor a agregar
     * @return true si fue agregado, false si ya existía o entrada inválida
     */
    public boolean agregar(Profesor p) {
        if (p == null || p.getIdentificacion() == null) return false;
        if (buscar(p.getIdentificacion()) != null) return false;
        profesores.add(p);
        archivo.guardarLista(profesores, RUTA);
        return true;
    }

    public List<Profesor> listar() {
        return new ArrayList<>(profesores);
    }

    public boolean eliminar(String id) {
        boolean eliminado = profesores.removeIf(p -> p.getIdentificacion().equals(id));
        if (eliminado) archivo.guardarLista(profesores, RUTA);
        return eliminado;
    }

    public Profesor buscar(String id) {
        for (Profesor p : profesores) {
            if (p.getIdentificacion().equals(id)) return p;
        }
        return null;
    }

    public void actualizar(Profesor profesorActualizado) {
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getIdentificacion().equals(profesorActualizado.getIdentificacion())) {
                profesores.set(i, profesorActualizado);
                archivo.guardarLista(profesores, RUTA);
                break;
            }
        }
    }
}

