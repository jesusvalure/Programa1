package com.mycompany.programa1matriculacalificaciones.gui;

import javax.swing.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AuthService;

public class LoginFrame extends JFrame {
    private JTextField txtIdentificacion;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private AuthService authService;

    public LoginFrame() {
        setTitle("Ingreso al Sistema");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        authService = new AuthService();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        txtIdentificacion = new JTextField(15);
        txtContrasena = new JPasswordField(15);
        btnIngresar = new JButton("Ingresar");
        panel.add(new JLabel("ID:"));
        panel.add(txtIdentificacion);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtContrasena);
        panel.add(btnIngresar);
        add(panel);
    }
}