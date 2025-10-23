package com.mycompany.programa1matriculacalificaciones.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona {
    private List<String> temasInteres = new ArrayList<>();
    private LocalDate fechaRegistro;
    private List<Matricula> matriculas = new ArrayList<>();

    public Estudiante() { super(); }

    public Estudiante(String nombre, String apellido1, String identificacion) {
        super(nombre, apellido1, identificacion);
        this.fechaRegistro = LocalDate.now();
    }

    public void agregarTemaInteres(String t) { temasInteres.add(t); }
    public List<Matricula> getMatriculas() { return matriculas; }
}
