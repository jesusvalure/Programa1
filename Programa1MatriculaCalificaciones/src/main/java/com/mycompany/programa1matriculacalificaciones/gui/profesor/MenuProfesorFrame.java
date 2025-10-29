package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import java.awt.*;

public class MenuProfesorFrame extends JFrame {
    public MenuProfesorFrame() {
        setTitle("Profesor - Menú");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new FlowLayout());
        p.add(new JButton("Mis evaluaciones"));
        p.add(new JButton("Asociar evaluación a grupo"));
        p.add(new JButton("Previsualizar"));
        add(p);
    }
}
