package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import java.awt.*;

public class MenuAdministradorFrame extends JFrame {
    public MenuAdministradorFrame() {
        setTitle("Menú Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JButton btnEstudiantes = new JButton("Gestionar Estudiantes");
        JButton btnCursos = new JButton("Gestionar Cursos");

        btnEstudiantes.addActionListener(e -> new FrmEstudianteCRUD().setVisible(true));
        btnCursos.addActionListener(e -> new FrmCursoCRUD().setVisible(true));

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.add(btnEstudiantes);
        panel.add(btnCursos);

        add(panel, BorderLayout.CENTER);
    }
}
