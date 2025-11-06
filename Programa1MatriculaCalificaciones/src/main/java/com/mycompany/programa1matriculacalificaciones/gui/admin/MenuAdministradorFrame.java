package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.gui.LoginFrame;

public class MenuAdministradorFrame extends JFrame {

    public MenuAdministradorFrame() {
        setTitle("Panel del Administrador");
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

        JLabel lblTitulo = new JLabel("Panel del Administrador", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(41, 128, 185));

        JPanel botones = new JPanel();
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));
        botones.setBackground(panel.getBackground());

        JButton btnCrudEst = crearBoton("Gestionar Estudiantes", new Color(52, 152, 219));
        JButton btnCrudProf = crearBoton("Gestionar Profesores", new Color(52, 152, 219));
        JButton btnCrudCurso = crearBoton("Gestionar Cursos", new Color(52, 152, 219));
        JButton btnGrupos = crearBoton("Gestionar Grupos", new Color(52, 152, 219));
        JButton btnReportes = crearBoton("Ver Reportes", new Color(52, 152, 219));
    JButton btnCambiarContrasena = crearBoton("Cambiar contraseña", new Color(52, 152, 219));
        JButton btnRegresar = crearBoton("Cerrar sesión", new Color(231, 76, 60));

        btnCrudEst.addActionListener(e -> {
            new FrmEstudianteCRUD().setVisible(true);
        });
        btnCrudProf.addActionListener(e -> {
            new FrmProfesorCRUD().setVisible(true);
        });
        btnCrudCurso.addActionListener(e -> {
            new FrmCursoCRUD().setVisible(true);
        });
        btnGrupos.addActionListener(e -> {
            new FrmGrupoCRUD().setVisible(true);
        });
        btnReportes.addActionListener(e -> {
            new FrmReportesAdmin().setVisible(true);
        });
        btnCambiarContrasena.addActionListener(e -> {
            new com.mycompany.programa1matriculacalificaciones.gui.FrmCambiarContrasena().setVisible(true);
        });
        btnRegresar.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        botones.add(btnCrudEst);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnCrudProf);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnCrudCurso);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnGrupos);
        botones.add(Box.createVerticalStrut(10));
        botones.add(btnReportes);
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
        btn.setMaximumSize(new Dimension(250, 40));
        return btn;
    }
}
