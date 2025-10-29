
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
        setSize(380, 220);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Identificación:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        txtId = new JTextField(15);
        p.add(txtId, c);

        c.gridx = 0; c.gridy = 1; c.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Contraseña:"), c);
        c.gridx = 1; c.anchor = GridBagConstraints.WEST;
        txtPass = new JPasswordField(15);
        p.add(txtPass, c);

        c.gridx = 1; c.gridy = 2; c.anchor = GridBagConstraints.CENTER;
        btnIngresar = new JButton("Ingresar");
        p.add(btnIngresar, c);

        btnIngresar.addActionListener(e -> {
            String id = txtId.getText().trim();
            String pass = new String(txtPass.getPassword());
            String rol = authService.autenticar(id, pass);
            if (rol == null) {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Bienvenido: " + rol);
                openMenuForRole(rol);
                this.dispose();
            }
        });

        add(p);
    }

    private void openMenuForRole(String rol) {
        if ("Administrador".equalsIgnoreCase(rol)) {
            com.mycompany.programa1matriculacalificaciones.gui.admin.MenuAdministradorFrame f =
                    new com.mycompany.programa1matriculacalificaciones.gui.admin.MenuAdministradorFrame();
            f.setVisible(true);
        } else if ("Profesor".equalsIgnoreCase(rol)) {
            com.mycompany.programa1matriculacalificaciones.gui.profesor.MenuProfesorFrame f =
                    new com.mycompany.programa1matriculacalificaciones.gui.profesor.MenuProfesorFrame();
            f.setVisible(true);
        } else if ("Estudiante".equalsIgnoreCase(rol)) {
            com.mycompany.programa1matriculacalificaciones.gui.estudiante.MenuEstudianteFrame f =
                    new com.mycompany.programa1matriculacalificaciones.gui.estudiante.MenuEstudianteFrame();
            f.setVisible(true);
        } else {
            // rol desconocido, mostrar admin por defecto
            new com.mycompany.programa1matriculacalificaciones.gui.admin.MenuAdministradorFrame().setVisible(true);
        }
    }
}
