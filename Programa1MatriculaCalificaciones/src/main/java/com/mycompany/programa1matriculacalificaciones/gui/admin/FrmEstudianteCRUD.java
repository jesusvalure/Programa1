package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AdministradorService;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;

public class FrmEstudianteCRUD extends JFrame {
    private AdministradorService adminService = new AdministradorService();
    private JTextField txtNombre, txtApellido, txtId;
    private JButton btnAgregar, btnEditar, btnEliminar, btnLimpiar, btnRegresar;
    private JTable tabla;
    private DefaultTableModel model;
    private Estudiante estudianteSeleccionado;

    public FrmEstudianteCRUD() {
        setTitle("Gestión de Estudiantes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(800, 500));
        initUI();
        listar(); // Cargar lista al iniciar
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Gestión de Estudiantes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(230, 126, 34));

        // Inicializar tabla primero
        model = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(model);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setPreferredScrollableViewportSize(new Dimension(0, 200));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEstudianteSeleccionado();
            }
        });

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
        panelBotones.setOpaque(true);

        btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        btnEditar = crearBoton("Editar", new Color(52, 152, 219));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

        // Asegurar que los botones estén habilitados
        btnAgregar.setEnabled(true);
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnLimpiar.setEnabled(true);
        btnRegresar.setEnabled(true);

        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRegresar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(panelBotones, gbc);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Estudiantes Registrados"));
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
        btn.setBorderPainted(true);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setEnabled(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
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
        estudianteSeleccionado = null;
    }
}
