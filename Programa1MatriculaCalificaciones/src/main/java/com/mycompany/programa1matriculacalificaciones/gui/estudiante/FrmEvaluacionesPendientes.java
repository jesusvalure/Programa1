package com.mycompany.programa1matriculacalificaciones.gui.estudiante;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmEvaluacionesPendientes extends JFrame {
    private EvaluacionAsignadaService asignacionService = new EvaluacionAsignadaService();
    
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FrmEvaluacionesPendientes() {
        setTitle("Evaluaciones Pendientes");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
        cargarEvaluaciones();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Evaluaciones Pendientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(230, 126, 34));

        modeloTabla = new DefaultTableModel(new Object[]{"Evaluación", "Grupo", "Curso", "Fecha Asignación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Evaluaciones Asignadas"));

        JButton btnActualizar = crearBoton("Actualizar", new Color(243, 156, 18));
        btnActualizar.addActionListener(e -> cargarEvaluaciones());

        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> {
            dispose();
            new MenuEstudianteFrame().setVisible(true);
        });

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(panel.getBackground());
        botones.add(btnActualizar);
        botones.add(btnRegresar);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.PAGE_END);

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
        btn.setPreferredSize(new Dimension(130, 35));
        return btn;
    }

    private void cargarEvaluaciones() {
        modeloTabla.setRowCount(0);
        for (EvaluacionAsignada ea : asignacionService.listar()) {
            modeloTabla.addRow(new Object[]{
                ea.getEvaluacion().getTitulo(),
                ea.getGrupo().getCodigo(),
                ea.getGrupo().getCurso().getNombre(),
                ea.getFechaInicio().toString()
            });
        }
    }
}
