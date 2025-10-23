package com.mycompany.programa1matriculacalificaciones.gui;

import javax.swing.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AuthService;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtId;
    private JPasswordField txtPass;
    private JButton btnIngresar;
    private AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Ingreso al Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(380, 200);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0; c.gridy = 0; p.add(new JLabel("Identificación:"), c);
        c.gridx = 1; txtId = new JTextField(15); p.add(txtId, c);
        c.gridx = 0; c.gridy = 1; p.add(new JLabel("Contraseña:"), c);
        c.gridx = 1; txtPass = new JPasswordField(15); p.add(txtPass, c);
        c.gridx = 1; c.gridy = 2; btnIngresar = new JButton("Ingresar"); p.add(btnIngresar, c);

        btnIngresar.addActionListener(e -> {
            String id = txtId.getText();
            String pass = new String(txtPass.getPassword());
            String rol = authService.autenticar(id, pass);
            if (rol == null) {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            } else {
                JOptionPane.showMessageDialog(this, "Bienvenido: " + rol);
                // aquí abrirías las otras ventanas por rol
            }
        });

        add(p);
    }
}
