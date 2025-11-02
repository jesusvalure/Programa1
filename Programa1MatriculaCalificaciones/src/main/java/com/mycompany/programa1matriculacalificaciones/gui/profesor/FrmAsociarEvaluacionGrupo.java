package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmAsociarEvaluacionGrupo extends JFrame {
    private ProfesorService profesorService = new ProfesorService();
    private GrupoService grupoService = new GrupoService();
    private EvaluacionAsignadaService asignacionService = new EvaluacionAsignadaService();
    
    private JComboBox<Evaluacion> cmbEvaluacion;
    private JComboBox<Grupo> cmbGrupo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FrmAsociarEvaluacionGrupo() {
        setTitle("Asignar Evaluación a Grupo");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
        cargarAsignaciones();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Asignar Evaluación a Grupo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(39, 174, 96));

        JPanel formPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        formPanel.setBackground(panel.getBackground());

        cmbEvaluacion = new JComboBox<>(profesorService.listarEvaluaciones().toArray(new Evaluacion[0]));
        cmbEvaluacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbGrupo = new JComboBox<>(grupoService.listar().toArray(new Grupo[0]));
        cmbGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(new JLabel("Evaluación:"));
        formPanel.add(cmbEvaluacion);
        formPanel.add(Box.createHorizontalStrut(10));

        formPanel.add(new JLabel("Grupo:"));
        formPanel.add(cmbGrupo);
        formPanel.add(Box.createHorizontalStrut(10));

        JButton btnAsignar = crearBoton("Asignar", new Color(46, 204, 113));
        btnAsignar.addActionListener(e -> asignarEvaluacion());
        
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.setBackground(panel.getBackground());
        botonesPanel.add(btnAsignar);
        botonesPanel.add(btnRegresar);

        modeloTabla = new DefaultTableModel(new Object[]{"Evaluación", "Grupo", "Fecha Asignación"}, 0) {
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

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.PAGE_START);
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
        btn.setPreferredSize(new Dimension(130, 35));
        return btn;
    }

    private void asignarEvaluacion() {
        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        Grupo grupo = (Grupo) cmbGrupo.getSelectedItem();

        if (eval == null || grupo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación y un grupo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EvaluacionAsignada asignacion = new EvaluacionAsignada(eval, grupo, LocalDateTime.now());
        asignacionService.agregar(asignacion);
        JOptionPane.showMessageDialog(this, "Evaluación asignada correctamente al grupo", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cargarAsignaciones();
    }

    private void cargarAsignaciones() {
        modeloTabla.setRowCount(0);
        for (EvaluacionAsignada ea : asignacionService.listar()) {
            modeloTabla.addRow(new Object[]{
                ea.getEvaluacion().getTitulo(),
                ea.getGrupo().getCodigo() + " - " + ea.getGrupo().getCurso().getNombre(),
                ea.getFechaInicio().toString()
            });
        }
    }
}
