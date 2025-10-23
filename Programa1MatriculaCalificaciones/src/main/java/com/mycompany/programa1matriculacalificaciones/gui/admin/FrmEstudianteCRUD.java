package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import java.awt.*;

public class FrmEstudianteCRUD extends JFrame {
    public FrmEstudianteCRUD() {
        setTitle("CRUD Estudiantes");
        setSize(700,500);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel(new BorderLayout());
        // TODO: agregar formulario y JTable
        add(p);
    }
}
