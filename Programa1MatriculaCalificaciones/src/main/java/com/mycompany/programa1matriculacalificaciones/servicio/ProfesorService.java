package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Profesor;

public class ProfesorService {
    private static final String RUTA = "datos/matriculaycalificaciones/profesores.dat";
    private ArchivoService<Profesor> archivo = new ArchivoService<>();
    private List<Profesor> profesores;

    public ProfesorService() {
        profesores = archivo.cargarLista(RUTA);
    }

    public void agregar(Profesor p) {
        profesores.add(p);
        archivo.guardarLista(profesores, RUTA);
    }

    public List<Profesor> listar() {
        return new ArrayList<>(profesores);
    }

    public Profesor buscarPorId(String id) {
        for (Profesor p : profesores) {
            if (p.getIdentificacion().equals(id)) return p;
        }
        return null;
    }
}
