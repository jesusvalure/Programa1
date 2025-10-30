package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AdministradorService;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;

public class FrmEstudianteCRUD extends JFrame {
    private AdministradorService adminService = new AdministradorService();
    private JTextField txtNombre, txtApellido, txtId;
    private JButton btnAgregar, btnListar, btnEliminar;
    private JTable tabla;
    private DefaultTableModel model;

    public FrmEstudianteCRUD() {
        setTitle("CRUD Estudiantes");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel formulario = new JPanel(new GridLayout(2, 4, 8, 8));
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtId = new JTextField();

        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Apellido:"));
        formulario.add(txtApellido);
        formulario.add(new JLabel("Identificación:"));
        formulario.add(txtId);

        btnAgregar = new JButton("Agregar");
        btnListar = new JButton("Listar");
        btnEliminar = new JButton("Eliminar");
        formulario.add(btnAgregar);
        formulario.add(btnListar);
        formulario.add(btnEliminar);

        model = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> agregar());
        btnListar.addActionListener(e -> listar());
        btnEliminar.addActionListener(e -> eliminar());

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void agregar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String id = txtId.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        Estudiante e = new Estudiante(nombre, apellido, id);
        adminService.agregarEstudiante(e);
        JOptionPane.showMessageDialog(this, "Estudiante agregado correctamente");
        listar();
        limpiarCampos();
    }

    private void listar() {
        model.setRowCount(0);
        for (Estudiante es : adminService.listarEstudiantes()) {
            model.addRow(new Object[]{es.getIdentificacion(), es.getNombre(), es.getApellido1()});
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para eliminar");
            return;
        }
        String id = (String) model.getValueAt(fila, 0);
        if (adminService.eliminarEstudiante(id)) {
            JOptionPane.showMessageDialog(this, "Estudiante eliminado");
            listar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar estudiante");
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtId.setText("");
    }
}
