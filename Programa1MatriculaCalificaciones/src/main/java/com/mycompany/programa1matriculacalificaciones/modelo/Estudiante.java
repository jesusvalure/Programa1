package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> temasInteres = new ArrayList<>();
    private LocalDate fechaRegistro = LocalDate.now();

    public Estudiante() { super(); }

    public Estudiante(String nombre, String apellido1, String identificacion) {
        super(nombre, apellido1, identificacion);
    }

    public LocalDate getFechaRegistro() { return fechaRegistro; }

    public void agregarTemaInteres(String tema) {
        temasInteres.add(tema);
    }

    public List<String> getTemasInteres() {
        return temasInteres;
    }
}
