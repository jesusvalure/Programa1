package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AdministradorService;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;

public class FrmEstudianteCRUD extends JFrame {
    private AdministradorService adminService = new AdministradorService();
    private JTextField txtNombre, txtApellido, txtId;
    private JButton btnAgregar, btnEditar, btnEliminar, btnLimpiar;
    private JTable tabla;
    private DefaultTableModel model;
    private Estudiante estudianteSeleccionado;

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
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        formulario.add(btnAgregar);
        formulario.add(btnEditar);
        formulario.add(btnEliminar);
        formulario.add(btnLimpiar);

        model = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEstudianteSeleccionado();
            }
        });

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            dispose();
            new MenuAdministradorFrame().setVisible(true);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegresar);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        listar(); // Cargar lista al iniciar
    }

    private void agregar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String id = txtId.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (adminService.buscarPorId(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un estudiante con esta identificación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear estudiante
        Estudiante e = new Estudiante(nombre, apellido, id);
        adminService.agregarEstudiante(e);
        
        // Crear usuario para el estudiante
        String contrasena = JOptionPane.showInputDialog(this, 
            "Ingrese una contraseña para el estudiante " + nombre + ":", 
            "1234");
        if (contrasena == null || contrasena.trim().isEmpty()) {
            contrasena = "1234"; // Contraseña por defecto
        }
        
        try {
            com.mycompany.programa1matriculacalificaciones.servicio.AuthService authService = 
                new com.mycompany.programa1matriculacalificaciones.servicio.AuthService();
            authService.crearUsuario(id, contrasena, "Estudiante");
            JOptionPane.showMessageDialog(this, 
                "Estudiante agregado correctamente.\nUsuario: " + id + "\nContraseña: " + contrasena, 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Estudiante creado pero error al crear usuario: " + ex.getMessage(), 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
        listar();
        limpiarCampos();
    }
    
    private void cargarEstudianteSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            estudianteSeleccionado = null;
            return;
        }
        String id = (String) model.getValueAt(fila, 0);
        estudianteSeleccionado = adminService.buscarPorId(id);
        if (estudianteSeleccionado != null) {
            txtNombre.setText(estudianteSeleccionado.getNombre());
            txtApellido.setText(estudianteSeleccionado.getApellido1());
            txtId.setText(estudianteSeleccionado.getIdentificacion());
        }
    }
    
    private void editar() {
        if (estudianteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String id = txtId.getText().trim();
        
        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idOriginal = estudianteSeleccionado.getIdentificacion();
        if (!id.equals(idOriginal) && adminService.buscarPorId(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un estudiante con esta identificación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!id.equals(idOriginal)) {
            // Si cambió el ID, eliminar el viejo y agregar el nuevo
            adminService.eliminarEstudiante(idOriginal);
            Estudiante actualizado = new Estudiante(nombre, apellido, id);
            adminService.agregarEstudiante(actualizado);
        } else {
            // Si no cambió el ID, solo actualizar
            Estudiante actualizado = new Estudiante(nombre, apellido, id);
            adminService.actualizarEstudiante(actualizado);
        }
        JOptionPane.showMessageDialog(this, "Estudiante actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este estudiante?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String id = (String) model.getValueAt(fila, 0);
            if (adminService.eliminarEstudiante(id)) {
                JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar estudiante", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtId.setText("");
    }
}
