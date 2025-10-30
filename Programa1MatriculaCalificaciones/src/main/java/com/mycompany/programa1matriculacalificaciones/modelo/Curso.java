package com.mycompany.programa1matriculacalificaciones.modelo;

import java.io.Serializable;

public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigo;
    private String nombre;
    private int creditos;

    public Curso() {}

    public Curso(String codigo, String nombre, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCreditos() { return creditos; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (" + creditos + " créditos)";
    }
}
