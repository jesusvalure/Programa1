package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.gui.LoginFrame;

public class MenuProfesorFrame extends JFrame {
    private String profesorId;

    public MenuProfesorFrame() {
        this.profesorId = com.mycompany.programa1matriculacalificaciones.util.SesionActual.getUsuarioId();
        setTitle("Panel del Profesor");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(240, 240, 240));

        JLabel lblTitulo = new JLabel("Panel del Profesor", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(39, 174, 96));

        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        botones.setBackground(panel.getBackground());

        JButton btnEvaluaciones = crearBoton("CRUD de Evaluaciones", new Color(46, 204, 113));
        JButton btnPreguntas = crearBoton("Gestionar Preguntas", new Color(46, 204, 113));
        JButton btnAsociarEval = crearBoton("Asignar Evaluación a Grupo", new Color(46, 204, 113));
        JButton btnReportes = crearBoton("Ver Reportes de Notas", new Color(46, 204, 113));
        JButton btnRegresar = crearBoton("Cerrar sesión", new Color(231, 76, 60));

        btnEvaluaciones.addActionListener(e -> {
            new FrmEvaluacionCRUD().setVisible(true);
        });
        btnPreguntas.addActionListener(e -> {
            new FrmPreguntasEvaluacion().setVisible(true);
        });
        btnAsociarEval.addActionListener(e -> {
            new FrmAsociarEvaluacionGrupo().setVisible(true);
        });
        btnReportes.addActionListener(e -> {
            new FrmResultados().setVisible(true);
        });
        btnRegresar.addActionListener(e -> {
            com.mycompany.programa1matriculacalificaciones.util.SesionActual.cerrarSesion();
            dispose();
            new LoginFrame().setVisible(true);
        });

        botones.add(btnEvaluaciones);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnPreguntas);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnAsociarEval);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnReportes);
        botones.add(Box.createVerticalStrut(20));
        botones.add(btnRegresar);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);

        add(panel);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(280, 40));
        return btn;
    }
}
