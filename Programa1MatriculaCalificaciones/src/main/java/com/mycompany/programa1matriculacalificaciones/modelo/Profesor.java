package com.mycompany.programa1matriculacalificaciones.modelo;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona {
    private List<String> titulos = new ArrayList<>();
    private List<String> certificaciones = new ArrayList<>();
    private List<Grupo> gruposAsignados = new ArrayList<>();

    public Profesor() { super(); }
    public Profesor(String nombre, String apellido1, String identificacion) {
        super(nombre, apellido1, identificacion);
    }

    public void asignarGrupo(Grupo g) { gruposAsignados.add(g); }
}

