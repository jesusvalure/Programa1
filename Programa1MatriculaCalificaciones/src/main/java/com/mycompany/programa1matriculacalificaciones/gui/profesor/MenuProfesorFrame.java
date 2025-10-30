package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import java.awt.*;

public class MenuProfesorFrame extends JFrame {

    public MenuProfesorFrame() {
        setTitle("Menú Profesor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JButton btnEvaluaciones = new JButton("Gestionar Evaluaciones");
        JButton btnReportes = new JButton("Generar Reporte Evaluaciones");
        JButton btnResultados = new JButton("Registrar Resultados");

        btnEvaluaciones.addActionListener(e -> new FrmEvaluacionCRUD().setVisible(true));
        btnReportes.addActionListener(e -> new FrmReporteEvaluacion().setVisible(true));
        btnResultados.addActionListener(e -> new FrmResultados().setVisible(true));

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.add(btnEvaluaciones);
        panel.add(btnReportes);
        panel.add(btnResultados);
        add(panel, BorderLayout.CENTER);
    }
}
