package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.CursoService;
import com.mycompany.programa1matriculacalificaciones.modelo.Curso;

public class FrmCursoCRUD extends JFrame {
    private CursoService cursoService = new CursoService();
    private JTextField txtCodigo, txtNombre, txtCreditos;
    private JTable tabla;
    private DefaultTableModel model;
    private Curso cursoSeleccionado;

    public FrmCursoCRUD() {
        setTitle("Gestión de Cursos");
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

        JLabel lblTitulo = new JLabel("Gestión de Cursos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(155, 89, 182));

        // Panel de formulario con mejor layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(panel.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtCodigo = new JTextField(20);
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCreditos = new JTextField(20);
        txtCreditos.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Fila 1: Código
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCodigo, gbc);

        // Fila 2: Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        // Fila 3: Créditos
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Créditos:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCreditos, gbc);

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
        model = new DefaultTableModel(new Object[]{"Código", "Nombre", "Créditos"}, 0) {
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
                cargarCursoSeleccionado();
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Cursos Registrados"));
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
        try {
            String cod = txtCodigo.getText().trim();
            String nom = txtNombre.getText().trim();
            String credStr = txtCreditos.getText().trim();
            
            if (cod.isEmpty() || nom.isEmpty() || credStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int cred = Integer.parseInt(credStr);
            
            if (cred <= 0) {
                JOptionPane.showMessageDialog(this, "Los créditos deben ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cursoService.buscar(cod) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un curso con este código", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cursoService.agregar(new Curso(cod, nom, cred));
            JOptionPane.showMessageDialog(this, "Curso agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listar();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los créditos deben ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarCursoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            cursoSeleccionado = null;
            return;
        }
        String codigo = (String) model.getValueAt(fila, 0);
        cursoSeleccionado = cursoService.buscar(codigo);
        if (cursoSeleccionado != null) {
            txtCodigo.setText(cursoSeleccionado.getCodigo());
            txtNombre.setText(cursoSeleccionado.getNombre());
            txtCreditos.setText(String.valueOf(cursoSeleccionado.getCreditos()));
        }
    }
    
    private void editar() {
        if (cursoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String cod = txtCodigo.getText().trim();
            String nom = txtNombre.getText().trim();
            String credStr = txtCreditos.getText().trim();
            
            if (cod.isEmpty() || nom.isEmpty() || credStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int cred = Integer.parseInt(credStr);
            
            if (cred <= 0) {
                JOptionPane.showMessageDialog(this, "Los créditos deben ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String codigoOriginal = cursoSeleccionado.getCodigo();
            if (!cod.equals(codigoOriginal) && cursoService.buscar(cod) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un curso con este código", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!cod.equals(codigoOriginal)) {
                // Si cambió el código, eliminar el viejo y agregar el nuevo
                cursoService.eliminar(codigoOriginal);
                cursoService.agregar(new Curso(cod, nom, cred));
            } else {
                // Si no cambió el código, solo actualizar
                cursoService.actualizar(new Curso(cod, nom, cred));
            }
            JOptionPane.showMessageDialog(this, "Curso actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listar();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los créditos deben ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCreditos.setText("");
        cursoSeleccionado = null;
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
            JOptionPane.showMessageDialog(this, "Seleccione un curso para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este curso?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String codigo = (String) model.getValueAt(fila, 0);
            if (cursoService.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Curso eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar curso", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
