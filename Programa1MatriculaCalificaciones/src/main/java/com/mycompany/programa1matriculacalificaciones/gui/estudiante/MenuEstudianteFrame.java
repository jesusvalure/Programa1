package com.mycompany.programa1matriculacalificaciones.gui.estudiante;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.gui.LoginFrame;

public class MenuEstudianteFrame extends JFrame {
    private String estudianteId;

    public MenuEstudianteFrame() {
        this.estudianteId = com.mycompany.programa1matriculacalificaciones.util.SesionActual.getUsuarioId();
        setTitle("Panel del Estudiante");
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

        JLabel lblTitulo = new JLabel("Panel del Estudiante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(230, 126, 34));

        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        botones.setBackground(panel.getBackground());

        JButton btnMatricular = crearBoton("Matricular Curso", new Color(243, 156, 18));
        JButton btnEvaluaciones = crearBoton("Ver Evaluaciones Pendientes", new Color(243, 156, 18));
        JButton btnRealizarEval = crearBoton("Realizar Evaluación", new Color(243, 156, 18));
        JButton btnDesempeno = crearBoton("Ver Desempeño Académico", new Color(243, 156, 18));
    JButton btnCambiarContrasena = crearBoton("Cambiar contraseña", new Color(52, 152, 219));
        JButton btnRegresar = crearBoton("Cerrar sesión", new Color(231, 76, 60));

        btnMatricular.addActionListener(e -> {
            new FrmMatricularCurso(estudianteId).setVisible(true);
        });
        btnEvaluaciones.addActionListener(e -> {
            new FrmEvaluacionesPendientes().setVisible(true);
        });
        btnRealizarEval.addActionListener(e -> {
            new FrmRealizarEvaluacion().setVisible(true);
        });
        btnDesempeno.addActionListener(e -> {
            new FrmDesempenoPersonal().setVisible(true);
        });
        btnCambiarContrasena.addActionListener(e -> {
            new com.mycompany.programa1matriculacalificaciones.gui.FrmCambiarContrasena().setVisible(true);
        });
        btnRegresar.addActionListener(e -> {
            com.mycompany.programa1matriculacalificaciones.util.SesionActual.cerrarSesion();
            dispose();
            new LoginFrame().setVisible(true);
        });

        botones.add(btnMatricular);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnEvaluaciones);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnRealizarEval);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnDesempeno);
        botones.add(Box.createVerticalStrut(20));
    botones.add(btnCambiarContrasena);
    botones.add(Box.createVerticalStrut(10));
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
