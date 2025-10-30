package com.mycompany.programa1matriculacalificaciones.modelo;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Profesor extends Persona implements Serializable {
    private List<String> titulos = new ArrayList<>();
    private List<String> certificaciones = new ArrayList<>();
    private List<Grupo> gruposAsignados = new ArrayList<>();

    public Profesor() { super(); }
    public Profesor(String nombre, String apellido1, String identificacion) {
        super(nombre, apellido1, identificacion);
    }

    public void asignarGrupo(Grupo g) { gruposAsignados.add(g); }
    public List<Grupo> getGruposAsignados() { return gruposAsignados; }
    public void agregarTitulo(String t) { titulos.add(t); }
    public void agregarCertificacion(String c) { certificaciones.add(c); }
    public List<String> getTitulos() { return titulos; }
    public List<String> getCertificaciones() { return certificaciones; }
    
}

