package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion;
import com.mycompany.programa1matriculacalificaciones.servicio.ProfesorService;

public class FrmEvaluacionCRUD extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtTiempo;
    private JComboBox<String> cmbTipo;
    private JCheckBox chkAleatorio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private ProfesorService profesorService = new ProfesorService();

    public FrmEvaluacionCRUD() {
        setTitle("Gestión de Evaluaciones");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(850, 550));
        initUI();
        cargarEvaluaciones();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Gestión de Evaluaciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(39, 174, 96));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(panel.getBackground());
        txtTitulo = crearCampoTexto("Título de la Evaluación");
        txtTiempo = crearCampoTexto("Tiempo (minutos) - 0 sin límite");
        cmbTipo = new JComboBox<>(new String[]{
            "Selección Única", "Selección Múltiple", "Falso/Verdadero", "Pareo", "Sopa de Letras"
        });
        cmbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTipo.setBackground(Color.WHITE);
        cmbTipo.setBorder(BorderFactory.createTitledBorder("Tipo de Pregunta"));

        chkAleatorio = new JCheckBox("Mostrar en orden aleatorio");
        chkAleatorio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkAleatorio.setBackground(panel.getBackground());

    formPanel.add(txtTitulo);
    formPanel.add(cmbTipo);
    formPanel.add(txtTiempo);
    formPanel.add(chkAleatorio);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(panel.getBackground());

        JButton btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        JButton btnEditar = crearBoton("Editar", new Color(52, 152, 219));
        JButton btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnRegresar);

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Título", "Tipo", "Aleatorio"}, 0);
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setGridColor(new Color(220, 220, 220));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Evaluaciones Existentes"));

        // --- Eventos ---
        btnAgregar.addActionListener(e -> agregarEvaluacion());
        btnEditar.addActionListener(e -> editarEvaluacion());
        btnEliminar.addActionListener(e -> eliminarEvaluacion());
        btnRegresar.addActionListener(e -> {
            dispose();
        });
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarEvaluacionSeleccionada();
            }
        });

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);
        panel.add(botones, BorderLayout.PAGE_END);

        add(panel);
    }

    private JTextField crearCampoTexto(String titulo) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(Color.WHITE);
        campo.setForeground(Color.BLACK);
        campo.setOpaque(true);
        TitledBorder border = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                titulo, TitledBorder.LEFT, TitledBorder.TOP);
        border.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        campo.setBorder(border);
        campo.setPreferredSize(new Dimension(250, 60));
        return campo;
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
        btn.setPreferredSize(new Dimension(130, 35));
        return btn;
    }

    // --- Lógica CRUD ---
    private void cargarEvaluaciones() {
        modeloTabla.setRowCount(0);
        List<Evaluacion> evaluaciones = profesorService.listarEvaluaciones();
        for (Evaluacion e : evaluaciones) {
            modeloTabla.addRow(new Object[]{
                e.getId(), e.getTitulo(), e.getTipo(),
                e.isOrdenAleatorio() ? "Sí" : "No"
            });
        }
    }

    private void agregarEvaluacion() {
        String titulo = txtTitulo.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();
        boolean aleatorio = chkAleatorio.isSelected();

        int tiempo = 0;
        try {
            String t = txtTiempo.getText().trim();
            if (!t.isEmpty()) tiempo = Integer.parseInt(t);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tiempo inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un título para la evaluación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion ev = new Evaluacion(titulo, tipo, aleatorio);
        ev.setTiempoMinutos(tiempo);
        boolean agregado = profesorService.agregarEvaluacion(ev);
        if (!agregado) {
            JOptionPane.showMessageDialog(this, "No fue posible agregar la evaluación (ID duplicado o datos inválidos).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        cargarEvaluaciones();
        limpiarCampos();
    }

    private void cargarEvaluacionSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        String id = (String) modeloTabla.getValueAt(fila, 0);
        Evaluacion eval = profesorService.obtenerEvaluacionPorId(id);
        if (eval != null) {
            txtTitulo.setText(eval.getTitulo());
            // Buscar el índice del tipo en el combo box
            String[] tipos = {"Selección Única", "Selección Múltiple", "Falso/Verdadero", "Pareo", "Sopa de Letras"};
            for (int i = 0; i < tipos.length; i++) {
                if (tipos[i].equals(eval.getTipo())) {
                    cmbTipo.setSelectedIndex(i);
                    break;
                }
            }
            chkAleatorio.setSelected(eval.isOrdenAleatorio());
            txtTiempo.setText(String.valueOf(eval.getTiempoMinutos()));
        }
    }

    private void editarEvaluacion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación para editar");
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);
        Evaluacion evalExistente = profesorService.obtenerEvaluacionPorId(id);
        if (evalExistente == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la evaluación");
            return;
        }

        String titulo = txtTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un título para la evaluación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = (String) cmbTipo.getSelectedItem();
        boolean aleatorio = chkAleatorio.isSelected();

        // Preservar preguntas existentes
        Evaluacion evalActualizada = new Evaluacion(id, titulo, tipo, aleatorio);
        // establecer tiempo
        try {
            String t = txtTiempo.getText().trim();
            int tiempo = t.isEmpty() ? 0 : Integer.parseInt(t);
            evalActualizada.setTiempoMinutos(tiempo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tiempo inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        evalActualizada.getPreguntas().addAll(evalExistente.getPreguntas());
        
    profesorService.actualizarEvaluacion(evalActualizada);
        cargarEvaluaciones();
        limpiarCampos();
    }

    private void eliminarEvaluacion() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación para eliminar");
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);
        profesorService.eliminarEvaluacion(id);
        cargarEvaluaciones();
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        cmbTipo.setSelectedIndex(0);
        chkAleatorio.setSelected(false);
    }
}
