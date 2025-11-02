package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmGrupoCRUD extends JFrame {
    private GrupoService grupoService = new GrupoService();
    private CursoService cursoService = new CursoService();
    private ProfesorCRUDService profesorService = new ProfesorCRUDService();

    private JTextField txtCodigo;
    private JComboBox<Curso> cmbCurso;
    private JComboBox<Profesor> cmbProfesor;
    private JTable tabla;
    private DefaultTableModel model;
    private Grupo grupoSeleccionado;

    public FrmGrupoCRUD() {
        setTitle("Gestión de Grupos");
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

        JLabel lblTitulo = new JLabel("Gestión de Grupos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(241, 196, 15));

        // Panel de formulario con mejor layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(panel.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtCodigo = new JTextField(20);
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Inicializar combos
        cmbCurso = new JComboBox<>(cursoService.listar().toArray(new Curso[0]));
        cmbCurso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCurso.setPreferredSize(new Dimension(250, 30));
        // Renderer personalizado para mostrar el nombre del curso
        cmbCurso.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Curso) {
                    Curso curso = (Curso) value;
                    setText(curso.getCodigo() + " - " + curso.getNombre());
                }
                return this;
            }
        });
        
        cmbProfesor = new JComboBox<>(profesorService.listar().toArray(new Profesor[0]));
        cmbProfesor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbProfesor.setPreferredSize(new Dimension(250, 30));
        // Renderer personalizado para mostrar el nombre del profesor
        cmbProfesor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Profesor) {
                    Profesor prof = (Profesor) value;
                    String nombre = prof.getNombre();
                    String apellido = prof.getApellido1();
                    if (nombre != null && apellido != null) {
                        setText(nombre + " " + apellido);
                    } else if (nombre != null) {
                        setText(nombre);
                    } else if (apellido != null) {
                        setText(apellido);
                    } else {
                        setText("Profesor " + prof.getIdentificacion());
                    }
                }
                return this;
            }
        });

        // Fila 1: Código
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCodigo, gbc);

        // Fila 2: Curso
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Curso:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbCurso, gbc);

        // Fila 3: Profesor
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Profesor:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbProfesor, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(panel.getBackground());

        JButton btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        JButton btnEditar = crearBoton("Editar", new Color(52, 152, 219));
        JButton btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        JButton btnLimpiar = crearBoton("Limpiar", new Color(149, 165, 166));
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

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
        formPanel.add(panelBotones, gbc);

        // Tabla
        model = new DefaultTableModel(new Object[]{"Código", "Curso", "Profesor"}, 0) {
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
                cargarGrupoSeleccionado();
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Grupos Registrados"));
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
        btn.setPreferredSize(new Dimension(120, 35));
        return btn;
    }

    private void agregar() {
        String codigo = txtCodigo.getText().trim();
        Curso curso = (Curso) cmbCurso.getSelectedItem();
        Profesor prof = (Profesor) cmbProfesor.getSelectedItem();

        if (codigo.isEmpty() || curso == null || prof == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (grupoService.buscar(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un grupo con este código", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        grupoService.agregar(new Grupo(codigo, curso, prof));
        JOptionPane.showMessageDialog(this, "Grupo agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        listar();
        limpiarCampos();
    }
    
    private void cargarGrupoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            grupoSeleccionado = null;
            return;
        }
        String codigo = (String) model.getValueAt(fila, 0);
        grupoSeleccionado = grupoService.buscar(codigo);
        if (grupoSeleccionado != null) {
            txtCodigo.setText(grupoSeleccionado.getCodigo());
            // Buscar y seleccionar el curso y profesor en los combos
            for (int i = 0; i < cmbCurso.getItemCount(); i++) {
                if (cmbCurso.getItemAt(i).getCodigo().equals(grupoSeleccionado.getCurso().getCodigo())) {
                    cmbCurso.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cmbProfesor.getItemCount(); i++) {
                if (cmbProfesor.getItemAt(i).getIdentificacion().equals(grupoSeleccionado.getProfesor().getIdentificacion())) {
                    cmbProfesor.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    private void editar() {
        if (grupoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un grupo para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codigo = txtCodigo.getText().trim();
        Curso curso = (Curso) cmbCurso.getSelectedItem();
        Profesor prof = (Profesor) cmbProfesor.getSelectedItem();
        
        if (codigo.isEmpty() || curso == null || prof == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codigoOriginal = grupoSeleccionado.getCodigo();
        if (!codigo.equals(codigoOriginal) && grupoService.buscar(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un grupo con este código", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Grupo actualizado = new Grupo(codigo, curso, prof);
        // Preservar estudiantes si los hay
        if (grupoSeleccionado.getEstudiantes() != null && !grupoSeleccionado.getEstudiantes().isEmpty()) {
            for (Estudiante e : grupoSeleccionado.getEstudiantes()) {
                actualizado.agregarEstudiante(e);
            }
        }
        
        if (!codigo.equals(codigoOriginal)) {
            grupoService.eliminar(codigoOriginal);
            grupoService.agregar(actualizado);
        } else {
            grupoService.actualizar(actualizado);
        }
        
        JOptionPane.showMessageDialog(this, "Grupo actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        listar();
        limpiarCampos();
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        cmbCurso.setSelectedIndex(-1);
        cmbProfesor.setSelectedIndex(-1);
        grupoSeleccionado = null;
        actualizarCombos();
    }
    
    private void actualizarCombos() {
        // Actualizar el contenido de los combos sin recrearlos
        Curso cursoSeleccionado = (Curso) cmbCurso.getSelectedItem();
        Profesor profesorSeleccionado = (Profesor) cmbProfesor.getSelectedItem();
        
        cmbCurso.removeAllItems();
        for (Curso c : cursoService.listar()) {
            cmbCurso.addItem(c);
        }
        if (cursoSeleccionado != null) {
            cmbCurso.setSelectedItem(cursoSeleccionado);
        }
        
        cmbProfesor.removeAllItems();
        for (Profesor p : profesorService.listar()) {
            cmbProfesor.addItem(p);
        }
        if (profesorSeleccionado != null) {
            cmbProfesor.setSelectedItem(profesorSeleccionado);
        }
    }

    private void listar() {
        model.setRowCount(0);
        for (Grupo g : grupoService.listar()) {
            String nombreProfesor = "N/A";
            if (g.getProfesor() != null) {
                String nombre = g.getProfesor().getNombre();
                String apellido = g.getProfesor().getApellido1();
                if (nombre != null && apellido != null) {
                    nombreProfesor = nombre + " " + apellido;
                } else if (nombre != null) {
                    nombreProfesor = nombre;
                } else if (apellido != null) {
                    nombreProfesor = apellido;
                }
            }
            model.addRow(new Object[]{g.getCodigo(), g.getCurso().getNombre(), nombreProfesor});
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un grupo para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este grupo?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String codigo = (String) model.getValueAt(fila, 0);
            if (grupoService.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Grupo eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar grupo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
