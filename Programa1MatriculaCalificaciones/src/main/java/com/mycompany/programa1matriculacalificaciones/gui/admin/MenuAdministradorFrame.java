package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import java.awt.*;

public class MenuAdministradorFrame extends JFrame {
    public MenuAdministradorFrame() {
        setTitle("Administrador - Menú");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new FlowLayout());
        p.add(new JButton("Estudiantes"));
        p.add(new JButton("Profesores"));
        p.add(new JButton("Cursos"));
        p.add(new JButton("Reportes"));
        add(p);
    }
}
