package com.mycompany.programa1matriculacalificaciones.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AuthService;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtClave;
    private JButton btnLogin;
    private JCheckBox chkMostrarClave;
    private AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Inicio de Sesión - Sistema Académico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 320);
        setResizable(false);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(240, 240, 240));

        JLabel lblTitulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(44, 62, 80));

        JPanel campos = new JPanel();
        campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
        campos.setBackground(panel.getBackground());

        txtUsuario = crearCampoTexto("Usuario");
        txtClave = crearCampoClave("Contraseña");

        chkMostrarClave = new JCheckBox("Mostrar contraseña");
        chkMostrarClave.setBackground(panel.getBackground());
        chkMostrarClave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkMostrarClave.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkMostrarClave.addActionListener(e -> mostrarClave());

        btnLogin = crearBoton("Ingresar", new Color(52, 152, 219));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> autenticar());

        JButton btnSalir = crearBoton("Salir", new Color(231, 76, 60));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> System.exit(0));

        // Agregar espacio entre componentes
        campos.add(txtUsuario);
        campos.add(Box.createVerticalStrut(15));
        campos.add(txtClave);
        campos.add(Box.createVerticalStrut(5));
        campos.add(chkMostrarClave);
        campos.add(Box.createVerticalStrut(15));
        campos.add(btnLogin);
        campos.add(Box.createVerticalStrut(10));
        campos.add(btnSalir);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(campos, BorderLayout.CENTER);
        add(panel);
    }


    private JTextField crearCampoTexto(String titulo) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(Color.WHITE);
        campo.setForeground(Color.BLACK);
        campo.setOpaque(true);
        // Border simplificado con TitledBorder
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)), titulo, 
                TitledBorder.LEFT, TitledBorder.TOP
        );
        border.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        campo.setBorder(border);
        campo.setMargin(new Insets(0, 5, 5, 5)); // espacio interno
        campo.setPreferredSize(new Dimension(300, 60));
        return campo;
    }

    private JPasswordField crearCampoClave(String titulo) {
        JPasswordField campo = new JPasswordField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(Color.WHITE);
        campo.setForeground(Color.BLACK);
        campo.setOpaque(true);
        // Border simplificado con TitledBorder
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)), titulo, 
                TitledBorder.LEFT, TitledBorder.TOP
        );
        border.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        campo.setBorder(border);
        campo.setMargin(new Insets(0, 5, 5, 5));
        campo.setPreferredSize(new Dimension(300, 60));
        return campo;
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
        btn.setPreferredSize(new Dimension(150, 35));
        return btn;
    }

    private void mostrarClave() {
        txtClave.setEchoChar(chkMostrarClave.isSelected() ? (char) 0 : '•');
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtClave.getPassword()).trim();

        if (usuario.isEmpty() || clave.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rol = authService.login(usuario, clave);
        if (rol == null) {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Guardar sesión actual
        com.mycompany.programa1matriculacalificaciones.util.SesionActual.iniciarSesion(usuario, rol);

        //JOptionPane.showMessageDialog(this, "Bienvenido, " + rol);

        dispose();
        switch (rol.toLowerCase()) {
            case "administrador":
                new com.mycompany.programa1matriculacalificaciones.gui.admin.MenuAdministradorFrame().setVisible(true);
                break;
            case "profesor":
                new com.mycompany.programa1matriculacalificaciones.gui.profesor.MenuProfesorFrame().setVisible(true);
                break;
            case "estudiante":
                new com.mycompany.programa1matriculacalificaciones.gui.estudiante.MenuEstudianteFrame().setVisible(true);
                break;
        }
    }

    
}
