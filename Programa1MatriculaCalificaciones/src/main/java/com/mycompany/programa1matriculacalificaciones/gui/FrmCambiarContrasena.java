package com.mycompany.programa1matriculacalificaciones.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AuthService;
import com.mycompany.programa1matriculacalificaciones.util.SesionActual;

public class FrmCambiarContrasena extends JFrame {

    private JPasswordField txtActual, txtNueva, txtConfirmar;
    private AuthService auth = new AuthService();

    public FrmCambiarContrasena() {
        setTitle("Cambiar Contraseña");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtActual = new JPasswordField();
        txtNueva = new JPasswordField();
        txtConfirmar = new JPasswordField();

        panel.add(labeledPanel("Contraseña actual:", txtActual));
        panel.add(labeledPanel("Nueva contraseña:", txtNueva));
        panel.add(labeledPanel("Confirmar nueva:", txtConfirmar));

        JButton btnCambiar = new JButton("Cambiar");
        btnCambiar.addActionListener(e -> cambiar());
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(btnCambiar);
        panel.add(p);

        add(panel);
    }

    private JPanel labeledPanel(String label, JComponent comp) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    private void cambiar() {
        String id = SesionActual.getUsuarioId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "No hay usuario logueado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String actual = new String(txtActual.getPassword());
        String nueva = new String(txtNueva.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());

        if (nueva == null || nueva.isBlank() || !nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas nuevas no coinciden o están vacías.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean ok = auth.cambiarContrasena(id, actual, nueva);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Contraseña cambiada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al cambiar la contraseña. Verifique la contraseña actual.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
