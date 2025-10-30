package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.CursoService;
import com.mycompany.programa1matriculacalificaciones.modelo.Curso;

public class FrmCursoCRUD extends JFrame {
    private CursoService cursoService = new CursoService();
    private JTextField txtCodigo, txtNombre, txtCreditos;
    private JTable tabla;
    private DefaultTableModel model;

    public FrmCursoCRUD() {
        setTitle("CRUD Cursos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCreditos = new JTextField();

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Créditos:"));
        panel.add(txtCreditos);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");

        panel.add(btnAgregar);
        panel.add(btnListar);
        panel.add(btnEliminar);

        model = new DefaultTableModel(new Object[]{"Código", "Nombre", "Créditos"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> agregar());
        btnListar.addActionListener(e -> listar());
        btnEliminar.addActionListener(e -> eliminar());

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void agregar() {
        try {
            String cod = txtCodigo.getText().trim();
            String nom = txtNombre.getText().trim();
            int cred = Integer.parseInt(txtCreditos.getText().trim());

            if (cod.isEmpty() || nom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }

            cursoService.agregar(new Curso(cod, nom, cred));
            JOptionPane.showMessageDialog(this, "Curso agregado");
            listar();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Créditos inválidos");
        }
    }

    private void listar() {
        model.setRowCount(0);
        for (Curso c : cursoService.listar()) {
            model.addRow(new Object[]{c.getCodigo(), c.getNombre(), c.getCreditos()});
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso");
            return;
        }
        String codigo = (String) model.getValueAt(fila, 0);
        if (cursoService.eliminar(codigo)) {
            JOptionPane.showMessageDialog(this, "Curso eliminado");
            listar();
        }
    }
}
