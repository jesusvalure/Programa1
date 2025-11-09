package com.mycompany.programa1matriculacalificaciones.servicio;

import java.util.ArrayList;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Grupo;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class GrupoService {
    private static final String RUTA = PathConfig.BASE_DATA_DIR + "/grupos.dat";
    private ArchivoService<Grupo> archivo = new ArchivoService<>();
    private List<Grupo> grupos;

    public GrupoService() {
        grupos = archivo.cargarLista(RUTA);
        if (grupos == null) {
            grupos = new ArrayList<>();
        }
    }

    /**
     * Agrega un grupo si no existe otro con el mismo código.
     * @param g Grupo a agregar
     * @return true si fue agregado, false si ya existía o entrada inválida
     */
    public boolean agregar(Grupo g) {
        if (g == null || g.getCodigo() == null) return false;
        if (buscar(g.getCodigo()) != null) return false;
        grupos.add(g);
        archivo.guardarLista(grupos, RUTA);
        return true;
    }

    public List<Grupo> listar() {
        return new ArrayList<>(grupos);
    }

    public boolean eliminar(String codigo) {
        boolean eliminado = grupos.removeIf(g -> g.getCodigo().equals(codigo));
        if (eliminado) archivo.guardarLista(grupos, RUTA);
        return eliminado;
    }

    public Grupo buscar(String codigo) {
        for (Grupo g : grupos) {
            if (g.getCodigo().equals(codigo)) return g;
        }
        return null;
    }

    public void actualizar(Grupo grupoActualizado) {
        for (int i = 0; i < grupos.size(); i++) {
            if (grupos.get(i).getCodigo().equals(grupoActualizado.getCodigo())) {
                grupos.set(i, grupoActualizado);
                archivo.guardarLista(grupos, RUTA);
                break;
            }
        }
    }
}

