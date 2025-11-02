package com.mycompany.programa1matriculacalificaciones.gui.estudiante;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.ResultadoService;
import com.mycompany.programa1matriculacalificaciones.modelo.ResultadoEvaluacion;

public class FrmDesempenoPersonal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private ResultadoService resultadoService = new ResultadoService();

    public FrmDesempenoPersonal() {
        setTitle("Desempeño Personal");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        panel.setBackground(new Color(245, 245, 245));

        JLabel lblTitulo = new JLabel("Historial de Evaluaciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(230, 126, 34));

        modelo = new DefaultTableModel(new Object[]{"Fecha", "Evaluación", "Puntaje", "Nota (%)", "Estudiante"}, 0);
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados Registrados"));

        JButton btnActualizar = crearBoton("Actualizar", new Color(243, 156, 18));
        btnActualizar.addActionListener(e -> cargarDatos());

        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(panel.getBackground());
        botones.add(btnActualizar);
        botones.add(btnRegresar);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.PAGE_END);

        add(panel);
        cargarDatos();
    }

    private void cargarDatos() {
        modelo.setRowCount(0);
        for (ResultadoEvaluacion r : resultadoService.listarResultados()) {
            modelo.addRow(new Object[]{
                r.getFechaFormateada(),
                r.getTituloEvaluacion(),
                String.format("%.1f / %.1f", r.getPuntajeObtenido(), r.getPuntajeTotal()),
                String.format("%.1f%%", r.getNotaPorcentaje()),
                r.getEstudiante()
            });
        }
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
}
