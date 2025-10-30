package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.EvaluacionService;
import com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion;

public class FrmEvaluacionCRUD extends JFrame {
    private EvaluacionService service = new EvaluacionService();
    private JTextField txtId, txtTitulo;
    private JComboBox<String> cmbTipo;
    private JCheckBox chkAleatorio;
    private JTable tabla;
    private DefaultTableModel model;

    public FrmEvaluacionCRUD() {
        setTitle("CRUD Evaluaciones");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 10, 10));

        txtId = new JTextField();
        txtTitulo = new JTextField();
        cmbTipo = new JComboBox<>(new String[]{"Pareo", "Sopa de letras", "Selección múltiple", "Falso/Verdadero"});
        chkAleatorio = new JCheckBox("Orden aleatorio");

        panel.add(new JLabel("ID:"));
        panel.add(txtId);
        panel.add(new JLabel("Título:"));
        panel.add(txtTitulo);
        panel.add(new JLabel("Tipo:"));
        panel.add(cmbTipo);
        panel.add(chkAleatorio);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        panel.add(btnAgregar);
        panel.add(btnListar);
        panel.add(btnEliminar);

        model = new DefaultTableModel(new Object[]{"ID", "Título", "Tipo", "Aleatorio"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> agregar());
        btnListar.addActionListener(e -> listar());
        btnEliminar.addActionListener(e -> eliminar());

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void agregar() {
        String id = txtId.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String tipo = (String) cmbTipo.getSelectedItem();
        boolean aleatorio = chkAleatorio.isSelected();

        if (id.isEmpty() || titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        Evaluacion ev = new Evaluacion(id, titulo, tipo, aleatorio);
        service.agregar(ev);
        JOptionPane.showMessageDialog(this, "Evaluación agregada");
        listar();
    }

    private void listar() {
        model.setRowCount(0);
        for (Evaluacion e : service.listar()) {
            model.addRow(new Object[]{e.getId(), e.getTitulo(), e.getTipo(), e.isOrdenAleatorio()});
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        String id = (String) model.getValueAt(fila, 0);
        if (service.eliminar(id)) {
            JOptionPane.showMessageDialog(this, "Evaluación eliminada");
            listar();
        }
    }
}
