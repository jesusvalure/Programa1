package com.mycompany.programa1matriculacalificaciones.gui;

import javax.swing.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AuthService;
import com.mycompany.programa1matriculacalificaciones.gui.admin.MenuAdministradorFrame;
import com.mycompany.programa1matriculacalificaciones.gui.profesor.MenuProfesorFrame;

public class LoginFrame extends JFrame {
    private JTextField txtId;
    private JPasswordField txtPass;
    private JButton btnLogin;
    private AuthService auth = new AuthService();

    public LoginFrame() {
        setTitle("Ingreso al Sistema");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0; c.gridy = 0; panel.add(new JLabel("Identificación:"), c);
        c.gridx = 1; txtId = new JTextField(15); panel.add(txtId, c);
        c.gridx = 0; c.gridy = 1; panel.add(new JLabel("Contraseña:"), c);
        c.gridx = 1; txtPass = new JPasswordField(15); panel.add(txtPass, c);
        c.gridx = 1; c.gridy = 2; btnLogin = new JButton("Ingresar"); panel.add(btnLogin, c);

        btnLogin.addActionListener(e -> login());
        add(panel);
    }

    private void login() {
        String id = txtId.getText().trim();
        String pass = new String(txtPass.getPassword());
        String rol = auth.autenticar(id, pass);

        if (rol == null) {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (rol.equals("Administrador")) {
            new MenuAdministradorFrame().setVisible(true);
            this.dispose();
        } else if (rol.equals("Profesor")) {
            new MenuProfesorFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Bienvenido " + rol);
        }
    }
}
