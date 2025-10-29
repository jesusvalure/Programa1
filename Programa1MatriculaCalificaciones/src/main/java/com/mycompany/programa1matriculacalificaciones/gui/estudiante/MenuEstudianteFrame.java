package com.mycompany.programa1matriculacalificaciones.gui.estudiante;

import javax.swing.*;
import java.awt.*;

public class MenuEstudianteFrame extends JFrame {
    public MenuEstudianteFrame() {
        setTitle("Estudiante - Menú");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new FlowLayout());
        p.add(new JButton("Matricular"));
        p.add(new JButton("Evaluaciones pendientes"));
        p.add(new JButton("Desempeño"));
        add(p);
    }
}
