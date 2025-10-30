package com.mycompany.programa1matriculacalificaciones.servicio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio genérico de persistencia para leer y escribir listas de objetos serializables.
 */
public class ArchivoService<T extends Serializable> {

    public void guardarLista(List<T> lista, String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en " + ruta + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> cargarLista(String ruta) {
        File f = new File(ruta);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            System.err.println("Error al cargar datos desde " + ruta + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
