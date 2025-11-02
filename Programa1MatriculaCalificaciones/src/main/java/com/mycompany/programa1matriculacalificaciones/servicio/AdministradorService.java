package com.mycompany.programa1matriculacalificaciones.servicio;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;

/**
 * Manejo real de estudiantes guardados en archivo.
 */
public class AdministradorService implements Serializable {
    private static final String RUTA_ESTUDIANTES = "datos/matriculaycalificaciones/estudiantes.dat";
    private ArchivoService<Estudiante> archivo = new ArchivoService<>();
    private List<Estudiante> estudiantes;

    public AdministradorService() {
        estudiantes = archivo.cargarLista(RUTA_ESTUDIANTES);
        if (estudiantes == null) {
            estudiantes = new ArrayList<>();
        }
    }

    public void agregarEstudiante(Estudiante e) {
        estudiantes.add(e);
        archivo.guardarLista(estudiantes, RUTA_ESTUDIANTES);
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
            if (e.getIdentificacion().equals(id)) return e;
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
