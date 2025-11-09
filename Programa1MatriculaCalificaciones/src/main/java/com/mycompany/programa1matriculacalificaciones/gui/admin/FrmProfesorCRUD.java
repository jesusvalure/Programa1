package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.ProfesorCRUDService;
import com.mycompany.programa1matriculacalificaciones.util.Validator;
import com.mycompany.programa1matriculacalificaciones.modelo.Profesor;

public class FrmProfesorCRUD extends JFrame {
    private ProfesorCRUDService profesorService = new ProfesorCRUDService();
    private JTextField txtNombre, txtApellido, txtApellido2, txtId, txtTelefono, txtCorreo, txtDireccion;
    private JTextField txtFechaNacimiento, txtEspecialidad, txtGradoAcademico;
    private JSpinner spnAniosExperiencia;
    private JComboBox<String> cmbGenero;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar, btnEditar, btnEliminar, btnRegresar;

    public FrmProfesorCRUD() {
        setTitle("Gestión de Profesores");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(950, 650));
        initUI();
        cargarProfesores(); // Cargar lista al iniciar
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Gestión de Profesores", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(52, 152, 219));
        
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre.setPreferredSize(new Dimension(200, 30));
        txtNombre.setMinimumSize(new Dimension(200, 30));
        
        txtApellido = new JTextField(20);
        txtApellido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellido.setPreferredSize(new Dimension(200, 30));
        txtApellido.setMinimumSize(new Dimension(200, 30));
        
        txtApellido2 = new JTextField(20);
        txtApellido2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtApellido2.setPreferredSize(new Dimension(200, 30));
        txtApellido2.setMinimumSize(new Dimension(200, 30));
        
        txtId = new JTextField(20);
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtId.setPreferredSize(new Dimension(200, 30));
        txtId.setMinimumSize(new Dimension(200, 30));
        
        txtTelefono = new JTextField(20);
        txtTelefono.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTelefono.setPreferredSize(new Dimension(200, 30));
        txtTelefono.setMinimumSize(new Dimension(200, 30));
        
        txtCorreo = new JTextField(20);
        txtCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCorreo.setPreferredSize(new Dimension(200, 30));
        txtCorreo.setMinimumSize(new Dimension(200, 30));
        
        txtDireccion = new JTextField(20);
        txtDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDireccion.setPreferredSize(new Dimension(200, 30));
        txtDireccion.setMinimumSize(new Dimension(200, 30));
        
        txtFechaNacimiento = new JTextField(20);
        txtFechaNacimiento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtFechaNacimiento.setPreferredSize(new Dimension(200, 30));
        txtFechaNacimiento.setMinimumSize(new Dimension(200, 30));
        
        txtEspecialidad = new JTextField(20);
        txtEspecialidad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEspecialidad.setPreferredSize(new Dimension(200, 30));
        txtEspecialidad.setMinimumSize(new Dimension(200, 30));
        
        txtGradoAcademico = new JTextField(20);
        txtGradoAcademico.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtGradoAcademico.setPreferredSize(new Dimension(200, 30));
        txtGradoAcademico.setMinimumSize(new Dimension(200, 30));
        
        spnAniosExperiencia = new JSpinner(new SpinnerNumberModel(0, 0, 50, 1));
        spnAniosExperiencia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spnAniosExperiencia.setPreferredSize(new Dimension(200, 30));
        
        cmbGenero = new JComboBox<>(new String[]{"Masculino", "Femenino", "Otro"});
        cmbGenero.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbGenero.setPreferredSize(new Dimension(200, 30));

        // Panel de formulario con mejor layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(panel.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // Aumentar el espaciado entre componentes
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

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
        
        // Fila 4: Especialidad
        gbc.gridx = 2; gbc.gridy = 3;
        formPanel.add(new JLabel("Especialidad:*"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtEspecialidad, gbc);
        
        // Fila 5: Grado Académico
        gbc.gridx = 2; gbc.gridy = 4;
        formPanel.add(new JLabel("Grado Académico:*"), gbc);
        gbc.gridx = 3;
        formPanel.add(txtGradoAcademico, gbc);
        
        // Fila 6: Años de Experiencia
        gbc.gridx = 2; gbc.gridy = 5;
        formPanel.add(new JLabel("Años de Experiencia:"), gbc);
        gbc.gridx = 3;
        formPanel.add(spnAniosExperiencia, gbc);
        
        // Nota campos obligatorios
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 4;
        formPanel.add(new JLabel("* Campos obligatorios"), gbc);
        gbc.gridwidth = 1;

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

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(panelBotones, gbc);

        // Tabla con mejor diseño
        modeloTabla = new DefaultTableModel(new Object[]{
            "ID", "Nombre", "Apellido", "Teléfono", "Correo", "Especialidad", "Grado Académico"
        }, 0) {
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
                p.getApellido1(),
                p.getTelefono(),
                p.getCorreo(),
                p.getEspecialidad(),
                p.getGradoAcademico()
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
            txtApellido2.setText(prof.getApellido2() != null ? prof.getApellido2() : "");
            txtId.setText(prof.getIdentificacion());
            txtTelefono.setText(prof.getTelefono() != null ? prof.getTelefono() : "");
            txtCorreo.setText(prof.getCorreo() != null ? prof.getCorreo() : "");
            txtDireccion.setText(prof.getDireccion() != null ? prof.getDireccion() : "");
            txtFechaNacimiento.setText(prof.getFechaNacimiento() != null ? prof.getFechaNacimiento() : "");
            cmbGenero.setSelectedItem(prof.getGenero() != null ? prof.getGenero() : "Masculino");
            txtEspecialidad.setText(prof.getEspecialidad() != null ? prof.getEspecialidad() : "");
            txtGradoAcademico.setText(prof.getGradoAcademico() != null ? prof.getGradoAcademico() : "");
            spnAniosExperiencia.setValue(prof.getAniosExperiencia());
        }
    }

    private void agregarProfesor() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String apellido2 = txtApellido2.getText().trim();
        String id = txtId.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();
        String genero = (String) cmbGenero.getSelectedItem();
        String especialidad = txtEspecialidad.getText().trim();
        String gradoAcademico = txtGradoAcademico.getText().trim();
        int aniosExperiencia = (Integer) spnAniosExperiencia.getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty() || telefono.isEmpty() || 
            correo.isEmpty() || fechaNacimiento.isEmpty() || especialidad.isEmpty() || 
            gradoAcademico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validaciones adicionales
        if (!Validator.isEmailValid(correo)) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isPhoneValid(telefono)) {
            JOptionPane.showMessageDialog(this, "Ingrese un teléfono válido (sólo dígitos y signos +, espacios).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (profesorService.buscar(id) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un profesor con esta identificación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear profesor
        Profesor profesor = new Profesor(nombre, apellido, apellido2, id, telefono, correo, direccion, 
                                        fechaNacimiento, genero, especialidad, gradoAcademico, aniosExperiencia);
        boolean agregado = profesorService.agregar(profesor);
        if (!agregado) {
            JOptionPane.showMessageDialog(this, "No fue posible agregar el profesor (ya existe o datos inválidos).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

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
        String apellido2 = txtApellido2.getText().trim();
        String id = txtId.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String fechaNacimiento = txtFechaNacimiento.getText().trim();
        String genero = (String) cmbGenero.getSelectedItem();
        String especialidad = txtEspecialidad.getText().trim();
        String gradoAcademico = txtGradoAcademico.getText().trim();
        int aniosExperiencia = (Integer) spnAniosExperiencia.getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty() || telefono.isEmpty() || 
            correo.isEmpty() || fechaNacimiento.isEmpty() || especialidad.isEmpty() || 
            gradoAcademico.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validaciones adicionales
        if (!Validator.isPhoneValid(telefono)) {
            JOptionPane.showMessageDialog(this, "Ingrese un teléfono válido (sólo dígitos y signos +, espacios).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isEmailValid(correo)) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Profesor prof = profesorService.buscar(idOriginal);
        if (prof != null) {
            Profesor actualizado = new Profesor(nombre, apellido, apellido2, id, telefono, correo, direccion, 
                                        fechaNacimiento, genero, especialidad, gradoAcademico, aniosExperiencia);
            if (!id.equals(idOriginal) && profesorService.buscar(id) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un profesor con esta identificación", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            profesorService.eliminar(idOriginal);
            boolean agregado = profesorService.agregar(actualizado);
            if (!agregado) {
                JOptionPane.showMessageDialog(this, "No fue posible actualizar el profesor (el nuevo ID ya existe).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
        txtApellido2.setText("");
        txtId.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtFechaNacimiento.setText("");
        txtEspecialidad.setText("");
        txtGradoAcademico.setText("");
        spnAniosExperiencia.setValue(0);
        cmbGenero.setSelectedIndex(0);
    }

    // Validation delegated to Validator util
}
