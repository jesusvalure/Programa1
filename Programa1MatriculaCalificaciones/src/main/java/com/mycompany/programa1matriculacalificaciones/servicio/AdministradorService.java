package com.mycompany.programa1matriculacalificaciones.servicio;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

/**
 * Manejo real de estudiantes guardados en archivo.
 */
public class AdministradorService implements Serializable {
    private static final String RUTA_ESTUDIANTES = PathConfig.BASE_DATA_DIR + "/estudiantes.dat";
    private ArchivoService<Estudiante> archivo = new ArchivoService<>();
    private List<Estudiante> estudiantes;

    public AdministradorService() {
        estudiantes = archivo.cargarLista(RUTA_ESTUDIANTES);
        if (estudiantes == null) {
            estudiantes = new ArrayList<>();
        }
    }

    /**
     * Agrega un estudiante si no existe uno con la misma identificación.
     * @param e Estudiante a agregar
     * @return true si fue agregado, false si ya existía o entrada inválida
     */
    public boolean agregarEstudiante(Estudiante e) {
        if (e == null || e.getIdentificacion() == null) return false;
        if (buscarPorId(e.getIdentificacion()) != null) return false;
        estudiantes.add(e);
        archivo.guardarLista(estudiantes, RUTA_ESTUDIANTES);
        return true;
    }

    public List<Estudiante> listarEstudiantes() {
        return new ArrayList<>(estudiantes);
    }

    public boolean eliminarEstudiante(String id) {
        boolean eliminado = estudiantes.removeIf(e -> e.getIdentificacion().equals(id));
        if (eliminado) archivo.guardarLista(estudiantes, RUTA_ESTUDIANTES);
        return eliminado;
    }

    public Estudiante buscarPorId(String id) {
        for (Estudiante e : estudiantes) {
            if (e != null && e.getIdentificacion() != null && e.getIdentificacion().equals(id)) {
                return e;
            }
        }
        return null;
    }
    
    public void actualizarEstudiante(Estudiante estudianteActualizado) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getIdentificacion().equals(estudianteActualizado.getIdentificacion())) {
                estudiantes.set(i, estudianteActualizado);
                archivo.guardarLista(estudiantes, RUTA_ESTUDIANTES);
                break;
            }
        }
    }
}
