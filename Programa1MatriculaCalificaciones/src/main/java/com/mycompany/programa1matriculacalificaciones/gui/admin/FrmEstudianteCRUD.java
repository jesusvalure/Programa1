// === Archivo: gui/admin/FrmEstudianteCRUD.java ===
package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AdministradorService;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;

public class FrmEstudianteCRUD extends JFrame {
    private AdministradorService adminService = new AdministradorService();

    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtId;
    private JButton btnAgregar, btnListar;
    private JTable tabla;
    private DefaultTableModel model;

    public FrmEstudianteCRUD() {
        setTitle("CRUD Estudiantes");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel formulario = new JPanel(new GridLayout(2,4,8,8));
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
        formulario.add(btnAgregar);
        formulario.add(btnListar);

        model = new DefaultTableModel(new Object[]{"ID","Nombre","Apellido"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String id = txtId.getText().trim();
            if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos");
                return;
            }
            Estudiante est = new Estudiante(nombre, apellido, id);
            adminService.agregarEstudiante(est);
            JOptionPane.showMessageDialog(this, "Estudiante agregado");
        });

        btnListar.addActionListener(e -> {
            model.setRowCount(0);
            for (Estudiante es : adminService.listarEstudiantes()) {
                model.addRow(new Object[]{es.getIdentificacion(), es.getNombre(), es.getApellido1()});
            }
        });

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }
}
