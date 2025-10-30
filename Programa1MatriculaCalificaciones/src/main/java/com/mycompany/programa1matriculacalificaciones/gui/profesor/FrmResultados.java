package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmResultados extends JFrame {
    private ResultadoService resultadoService = new ResultadoService();
    private EvaluacionService evalService = new EvaluacionService();
    private AdministradorService estService = new AdministradorService();

    private JComboBox<Evaluacion> cmbEvaluacion;
    private JComboBox<Estudiante> cmbEstudiante;
    private JTextField txtNota;
    private JTable tabla;
    private DefaultTableModel model;

    public FrmResultados() {
        setTitle("Registro de Resultados");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));

        cmbEvaluacion = new JComboBox<>(evalService.listar().toArray(new Evaluacion[0]));
        cmbEstudiante = new JComboBox<>(estService.listarEstudiantes().toArray(new Estudiante[0]));
        txtNota = new JTextField();

        panel.add(new JLabel("Evaluación:"));
        panel.add(cmbEvaluacion);
        panel.add(new JLabel("Estudiante:"));
        panel.add(cmbEstudiante);
        panel.add(new JLabel("Nota:"));
        panel.add(txtNota);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnListar = new JButton("Listar");
        panel.add(btnRegistrar);
        panel.add(btnListar);

        model = new DefaultTableModel(new Object[]{"Estudiante", "Evaluación", "Nota"}, 0);
        tabla = new JTable(model);

        btnRegistrar.addActionListener(e -> registrar());
        btnListar.addActionListener(e -> listar());

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void registrar() {
        try {
            Estudiante est = (Estudiante) cmbEstudiante.getSelectedItem();
            Evaluacion ev = (Evaluacion) cmbEvaluacion.getSelectedItem();
            double nota = Double.parseDouble(txtNota.getText().trim());

            if (est == null || ev == null) {
                JOptionPane.showMessageDialog(this, "Seleccione evaluación y estudiante");
                return;
            }

            resultadoService.registrar(new ResultadoEvaluacion(est, ev, nota));
            JOptionPane.showMessageDialog(this, "Resultado registrado");
            listar();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nota inválida");
        }
    }

    private void listar() {
        model.setRowCount(0);
        for (ResultadoEvaluacion r : resultadoService.listar()) {
            model.addRow(new Object[]{r.getEstudiante().getNombre(), r.getEvaluacion().getTitulo(), r.getNota()});
        }
    }
}
