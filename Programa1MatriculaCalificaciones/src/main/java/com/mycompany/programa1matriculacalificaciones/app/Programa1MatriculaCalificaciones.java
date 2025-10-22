package com.mycompany.programa1matriculacalificaciones.app;

import javax.swing.SwingUtilities;
import com.mycompany.programa1matriculacalificaciones.gui.LoginFrame;

public class Programa1MatriculaCalificaciones {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
