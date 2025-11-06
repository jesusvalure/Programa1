package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.AdministradorService;
import com.mycompany.programa1matriculacalificaciones.modelo.Estudiante;

public class FrmEstudianteCRUD extends JFrame {
    private AdministradorService adminService = new AdministradorService();
    private JTextField txtNombre, txtApellido, txtApellido2, txtId, txtTelefono, txtCorreo, txtDireccion;
    private JTextField txtFechaNacimiento, txtCarrera, txtNivelEducativo, txtInstitucion;
    private JComboBox<String> cmbGenero;
    private JButton btnAgregar, btnEditar, btnEliminar, btnLimpiar, btnRegresar;
    private JTable tabla;
    private DefaultTableModel model;
    private Estudiante estudianteSeleccionado;

    public FrmEstudianteCRUD() {
        setTitle("Gestión de Estudiantes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1000, 800));
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
        gbc.insets = new Insets(10, 12, 10, 12); // Aumentar el espaciado entre componentes
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre = new JTextField(25);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre.setPreferredSize(new Dimension(200, 30));
        
        txtApellido = new JTextField(25);
        txtApellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellido.setPreferredSize(new Dimension(200, 30));
        
        txtApellido2 = new JTextField(25);
        txtApellido2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellido2.setPreferredSize(new Dimension(200, 30));
        
        txtId = new JTextField(25);
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtId.setPreferredSize(new Dimension(200, 30));
        
        txtTelefono = new JTextField(25);
        txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTelefono.setPreferredSize(new Dimension(200, 30));
        
        txtCorreo = new JTextField(25);
        txtCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCorreo.setPreferredSize(new Dimension(200, 30));
        
        txtDireccion = new JTextField(25);
        txtDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDireccion.setPreferredSize(new Dimension(200, 30));
        
        txtFechaNacimiento = new JTextField(25);
        txtFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFechaNacimiento.setPreferredSize(new Dimension(200, 30));
        
        txtCarrera = new JTextField(25);
        txtCarrera.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCarrera.setPreferredSize(new Dimension(200, 30));
        
        txtNivelEducativo = new JTextField(25);
        txtNivelEducativo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNivelEducativo.setPreferredSize(new Dimension(200, 30));
        
        txtInstitucion = new JTextField(25);
        txtInstitucion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInstitucion.setPreferredSize(new Dimension(200, 30));
        
        cmbGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        cmbGenero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbGenero.setPreferredSize(new Dimension(200, 30));
        ((JLabel)cmbGenero.getRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Columna izquierda
        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        // Fila 2: Primer Apellido
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Primer Apellido:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtApellido, gbc);
        
        // Fila 3: Segundo Apellido
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Segundo Apellido:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtApellido2, gbc);

        // Fila 4: Identificación
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Identificación:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtId, gbc);
        
        // Fila 5: Teléfono
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Teléfono:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTelefono, gbc);
        
        // Fila 6: Correo
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Correo:*"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCorreo, gbc);
        
        // Columna derecha
        // Fila 1: Dirección
        gbc.gridx = 2; gbc.gridy = 0;
        formPanel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtDireccion, gbc);
        
        // Fila 2: Fecha de Nacimiento
        gbc.gridx = 2; gbc.gridy = 1;
        formPanel.add(new JLabel("Fecha Nacimiento:*"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtFechaNacimiento, gbc);
        
        // Fila 3: Género
        gbc.gridx = 2; gbc.gridy = 2;
        formPanel.add(new JLabel("Género:"), gbc);
        gbc.gridx = 3;
        formPanel.add(cmbGenero, gbc);
        
        // Fila 4: Carrera
        gbc.gridx = 2; gbc.gridy = 3;
        formPanel.add(new JLabel("Carrera:*"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtCarrera, gbc);
        
        // Fila 5: Nivel Educativo
        gbc.gridx = 2; gbc.gridy = 4;
        formPanel.add(new JLabel("Nivel Educativo:*"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtNivelEducativo, gbc);
        
        // Fila 6: Institución de Procedencia
        gbc.gridx = 2; gbc.gridy = 5;
        formPanel.add(new JLabel("Institución Procedencia:"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtInstitucion, gbc);
        
        // Nota campos obligatorios
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 4;
        formPanel.add(new JLabel("* Campos obligatorios"), gbc);
        gbc.gridwidth = 1;

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

        gbc.gridx = 0; gbc.gridy = 7;
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
        String apellido2 = txtApellido2.getText().trim();
        String id = txtId.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();
        String genero = (String) cmbGenero.getSelectedItem();
        String carrera = txtCarrera.getText().trim();
        String nivelEducativo = txtNivelEducativo.getText().trim();
        String institucionProcedencia = txtInstitucion.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty() || telefono.isEmpty() || 
            correo.isEmpty() || fechaNacimiento.isEmpty() || carrera.isEmpty() || 
            nivelEducativo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (adminService.buscarPorId(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un estudiante con esta identificación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear estudiante
        Estudiante e = new Estudiante(nombre, apellido, apellido2, id, telefono, correo, direccion, 
                                     fechaNacimiento, genero, carrera, nivelEducativo, institucionProcedencia);
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
            txtApellido2.setText(estudianteSeleccionado.getApellido2() != null ? estudianteSeleccionado.getApellido2() : "");
            txtId.setText(estudianteSeleccionado.getIdentificacion());
            txtTelefono.setText(estudianteSeleccionado.getTelefono() != null ? estudianteSeleccionado.getTelefono() : "");
            txtCorreo.setText(estudianteSeleccionado.getCorreo() != null ? estudianteSeleccionado.getCorreo() : "");
            txtDireccion.setText(estudianteSeleccionado.getDireccion() != null ? estudianteSeleccionado.getDireccion() : "");
            txtFechaNacimiento.setText(estudianteSeleccionado.getFechaNacimiento() != null ? estudianteSeleccionado.getFechaNacimiento() : "");
            cmbGenero.setSelectedItem(estudianteSeleccionado.getGenero() != null ? estudianteSeleccionado.getGenero() : "Masculino");
            txtCarrera.setText(estudianteSeleccionado.getCarrera() != null ? estudianteSeleccionado.getCarrera() : "");
            txtNivelEducativo.setText(estudianteSeleccionado.getNivelEducativo() != null ? estudianteSeleccionado.getNivelEducativo() : "");
            txtInstitucion.setText(estudianteSeleccionado.getInstitucionProcedencia() != null ? estudianteSeleccionado.getInstitucionProcedencia() : "");
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
        txtApellido2.setText("");
        txtId.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtFechaNacimiento.setText("");
        cmbGenero.setSelectedIndex(0);
        txtCarrera.setText("");
        txtNivelEducativo.setText("");
        txtInstitucion.setText("");
        estudianteSeleccionado = null;
    }
}
