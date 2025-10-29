package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AdministradorService;

public class MenuAdministradorFrame extends JFrame {
    private AdministradorService adminService = new AdministradorService();

    public MenuAdministradorFrame() {
        setTitle("Administrador - Menú");
        setSize(700,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEstudiantes = new JButton("Estudiantes");
        JButton btnProfesores = new JButton("Profesores");
        JButton btnCursos = new JButton("Cursos");
        JButton btnReportes = new JButton("Reportes");

        botones.add(btnEstudiantes);
        botones.add(btnProfesores);
        botones.add(btnCursos);
        botones.add(btnReportes);

        p.add(botones, BorderLayout.NORTH);

        btnEstudiantes.addActionListener(e -> new FrmEstudianteCRUD().setVisible(true));
        btnProfesores.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modulo Profesores (no implementado aún)"));
        btnCursos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modulo Cursos (no implementado aún)"));
        btnReportes.addActionListener(e -> JOptionPane.showMessageDialog(this, "Reportes (no implementado aún)"));

        add(p);
    }
}
