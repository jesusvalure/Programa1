package com.mycompany.programa1matriculacalificaciones.servicio;

import java.io.*;
import java.util.*;
import com.mycompany.programa1matriculacalificaciones.util.PathConfig;

public class ArchivoService<T> {

    private final String rutaArchivo;

    public ArchivoService() {
        this.rutaArchivo = PathConfig.BASE_DATA_DIR + "/default.dat";
        File f = new File(rutaArchivo);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
    }

    public ArchivoService(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        File f = new File(rutaArchivo);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
    }

    public void guardar(List<T> lista) {
        if (lista == null) {
            lista = new ArrayList<>();
        }
        try {
            // Asegurar que el directorio existe
            File f = new File(rutaArchivo);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
                oos.writeObject(lista);
                oos.flush();
            }
        } catch (IOException e) {
            System.err.println("Error guardando archivo " + rutaArchivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> cargar() {
        File f = new File(rutaArchivo);
        if (!f.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando archivo: " + e.getMessage());
            // Intentar mover el archivo a un backup con timestamp para evitar repetidos errores
            try {
                String backupPath = rutaArchivo + ".bak_" + System.currentTimeMillis();
                File bak = new File(backupPath);
                if (f.renameTo(bak)) {
                    System.err.println("Archivo movido a backup: " + bak.getAbsolutePath());
                } else {
                    System.err.println("No se pudo mover el archivo a: " + backupPath);
                }
            } catch (Exception ex) {
                System.err.println("Error al mover archivo a backup: " + ex.getMessage());
            }
            return new ArrayList<>();
        }
    }

    public void guardarLista(List<T> lista, String ruta) {
        if (lista == null) {
            lista = new ArrayList<>();
        }
        try {
            // Asegurar que el directorio existe
            File f = new File(ruta);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
                oos.writeObject(lista);
                oos.flush();
            }
        } catch (IOException e) {
            System.err.println("Error guardando archivo " + ruta + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> cargarLista(String ruta) {
        File f = new File(ruta);
        if (!f.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error cargando archivo: " + e.getMessage());
            // Intentar mover el archivo a un backup con timestamp para evitar repetidos errores
            try {
                String backupPath = ruta + ".bak_" + System.currentTimeMillis();
                File bak = new File(backupPath);
                if (f.renameTo(bak)) {
                    System.err.println("Archivo movido a backup: " + bak.getAbsolutePath());
                } else {
                    System.err.println("No se pudo mover el archivo a: " + backupPath);
                }
            } catch (Exception ex) {
                System.err.println("Error al mover archivo a backup: " + ex.getMessage());
            }
            return new ArrayList<>();
        }
    }

}
