package com.mycompany.programa1matriculacalificaciones.app;

import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

// Clase principal para ejectutar todo el sistema de administradores, profesores y estudiantes
public class Programa1MatriculaCalificaciones {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println("No se pudo aplicar Nimbus: " + e.getMessage());
        }
        new com.mycompany.programa1matriculacalificaciones.gui.LoginFrame().setVisible(true);
    }
}
