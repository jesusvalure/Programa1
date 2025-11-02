package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.ProfesorCRUDService;
import com.mycompany.programa1matriculacalificaciones.modelo.Profesor;

public class FrmProfesorCRUD extends JFrame {
    private ProfesorCRUDService profesorService = new ProfesorCRUDService();
    private JTextField txtNombre, txtApellido, txtId;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnRegresar;

    public FrmProfesorCRUD() {
        setTitle("Gestión de Profesores");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 500));
        initUI();
        cargarProfesores();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Gestión de Profesores", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(52, 152, 219));

        // Panel de formulario con mejor layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(panel.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellido = new JTextField(20);
        txtApellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtId = new JTextField(20);
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        // Fila 2: Apellido
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtApellido, gbc);

        // Fila 3: Identificación
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtId, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(panel.getBackground());

        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        btnEditar = crearBoton("Editar", new Color(52, 152, 219));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

        btnAgregar.addActionListener(e -> agregarProfesor());
        btnEditar.addActionListener(e -> editarProfesor());
        btnEliminar.addActionListener(e -> eliminarProfesor());
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRegresar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(panelBotones, gbc);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setPreferredScrollableViewportSize(new Dimension(0, 200));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarProfesorSeleccionado();
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Profesores Registrados"));
        scroll.setPreferredSize(new Dimension(0, 250));

        // Panel norte con formulario
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(panel.getBackground());
        panelNorte.add(formPanel, BorderLayout.CENTER);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelNorte, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);

        add(panel);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void cargarProfesores() {
        modeloTabla.setRowCount(0);
        for (Profesor p : profesorService.listar()) {
            modeloTabla.addRow(new Object[]{
                p.getIdentificacion(),
                p.getNombre(),
                p.getApellido1()
            });
        }
    }

    private void cargarProfesorSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        String id = (String) modeloTabla.getValueAt(fila, 0);
        Profesor prof = profesorService.buscar(id);
        if (prof != null) {
            txtNombre.setText(prof.getNombre());
            txtApellido.setText(prof.getApellido1());
            txtId.setText(prof.getIdentificacion());
        }
    }

    private void agregarProfesor() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String id = txtId.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (profesorService.buscar(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un profesor con esta identificación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear profesor
        Profesor profesor = new Profesor(nombre, apellido, id);
        profesorService.agregar(profesor);
        
        // Crear usuario para el profesor
        String contrasena = JOptionPane.showInputDialog(this, 
            "Ingrese una contraseña para el profesor " + nombre + ":", 
            "Contraseña por defecto");
        if (contrasena == null || contrasena.trim().isEmpty()) {
            contrasena = "1234"; // Contraseña por defecto
        }
        
        try {
            com.mycompany.programa1matriculacalificaciones.servicio.AuthService authService = 
                new com.mycompany.programa1matriculacalificaciones.servicio.AuthService();
            authService.crearUsuario(id, contrasena, "Profesor");
            JOptionPane.showMessageDialog(this, 
                "Profesor agregado correctamente.\nUsuario: " + id + "\nContraseña: " + contrasena, 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Profesor creado pero error al crear usuario: " + e.getMessage(), 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
        cargarProfesores();
        limpiarCampos();
    }

    private void editarProfesor() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idOriginal = (String) modeloTabla.getValueAt(fila, 0);
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String id = txtId.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Profesor prof = profesorService.buscar(idOriginal);
        if (prof != null) {
            Profesor actualizado = new Profesor(nombre, apellido, id);
            if (!id.equals(idOriginal) && profesorService.buscar(id) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un profesor con esta identificación", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            profesorService.eliminar(idOriginal);
            profesorService.agregar(actualizado);
            JOptionPane.showMessageDialog(this, "Profesor actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarProfesores();
            limpiarCampos();
        }
    }

    private void eliminarProfesor() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este profesor?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (profesorService.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Profesor eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarProfesores();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar profesor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtId.setText("");
    }
}
