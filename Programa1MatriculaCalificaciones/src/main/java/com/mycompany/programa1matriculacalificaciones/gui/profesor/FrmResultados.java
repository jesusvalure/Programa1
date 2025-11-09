package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;
import com.mycompany.programa1matriculacalificaciones.util.SesionActual;

public class FrmResultados extends JFrame {
    private ResultadoService resultadoService = new ResultadoService();
    private ProfesorService profesorService = new ProfesorService();
    private GrupoService grupoService = new GrupoService();
    private String profesorId;

    private JComboBox<Evaluacion> cmbEvaluacion;
    private JComboBox<Grupo> cmbGrupo;
    private JTable tabla;
    private DefaultTableModel model;

    public FrmResultados() {
        this.profesorId = SesionActual.getUsuarioId();
        setTitle("Reporte de Notas - Profesor: " + profesorId);
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        cargarDatos();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Reporte de Notas - Profesor: " + profesorId, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(39, 174, 96));

        // Panel de filtros
        JPanel filtrosPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));

        cmbEvaluacion = new JComboBox<>();
        cmbGrupo = new JComboBox<>();

        filtrosPanel.add(new JLabel("Evaluación:"));
        filtrosPanel.add(new JLabel("Grupo:"));
        filtrosPanel.add(new JLabel("")); // Espacio vacío

        filtrosPanel.add(cmbEvaluacion);
        filtrosPanel.add(cmbGrupo);

        JButton btnFiltrar = crearBoton("Filtrar", new Color(52, 152, 219));
        JButton btnVerTodos = crearBoton("Ver Todos", new Color(46, 204, 113));
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

        filtrosPanel.add(btnFiltrar);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.add(btnVerTodos);
        botonesPanel.add(btnRegresar);

        btnFiltrar.addActionListener(e -> filtrarResultados());
        btnVerTodos.addActionListener(e -> cargarTodosLosResultados());
        btnRegresar.addActionListener(e -> dispose());

        model = new DefaultTableModel(new Object[]{
            "Estudiante", "Evaluación", "Grupo", "Puntaje Obtenido", 
            "Puntaje Total", "Nota (%)", "Fecha"
        }, 0);
        tabla = new JTable(model);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados de Evaluaciones"));

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(filtrosPanel, BorderLayout.CENTER);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void cargarDatos() {
        // Cargar evaluaciones del profesor
        cmbEvaluacion.removeAllItems();
        cmbEvaluacion.addItem(new Evaluacion("", "Todas las evaluaciones", "Todas", false));
        for (Evaluacion eval : profesorService.listarEvaluacionesPorProfesor(profesorId)) {
            cmbEvaluacion.addItem(eval);
        }

        // Cargar grupos del profesor
        cmbGrupo.removeAllItems();
        cmbGrupo.addItem(new Grupo("", "Todos los grupos", null));
        for (Grupo grupo : grupoService.listarGruposPorProfesor(profesorId)) {
            cmbGrupo.addItem(grupo);
        }

        cargarTodosLosResultados();
    }

    private void cargarTodosLosResultados() {
        model.setRowCount(0);
        try {
            for (ResultadoEvaluacion r : resultadoService.listarResultadosPorProfesor(profesorId)) {
                model.addRow(new Object[]{
                    r.getEstudiante(),
                    r.getTituloEvaluacion(),
                    r.getGrupo() != null ? r.getGrupo().getCodigo() : "N/A",
                    String.format("%.1f", r.getPuntajeObtenido()),
                    String.format("%.1f", r.getPuntajeTotal()),
                    String.format("%.1f%%", r.getNotaPorcentaje()),
                    r.getFecha() != null ? r.getFechaFormateada() : "N/A"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar resultados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarResultados() {
        model.setRowCount(0);
        try {
            Evaluacion evalSeleccionada = (Evaluacion) cmbEvaluacion.getSelectedItem();
            Grupo grupoSeleccionado = (Grupo) cmbGrupo.getSelectedItem();

            String evaluacionId = (evalSeleccionada != null && !evalSeleccionada.getId().isEmpty()) ? evalSeleccionada.getId() : null;
            String grupoId = (grupoSeleccionado != null && !grupoSeleccionado.getCodigo().isEmpty()) ? grupoSeleccionado.getCodigo() : null;

            for (ResultadoEvaluacion r : resultadoService.filtrarResultadosPorProfesor(profesorId, evaluacionId, grupoId)) {
                model.addRow(new Object[]{
                    r.getEstudiante(),
                    r.getTituloEvaluacion(),
                    r.getGrupo() != null ? r.getGrupo().getCodigo() : "N/A",
                    String.format("%.1f", r.getPuntajeObtenido()),
                    String.format("%.1f", r.getPuntajeTotal()),
                    String.format("%.1f%%", r.getNotaPorcentaje()),
                    r.getFecha() != null ? r.getFechaFormateada() : "N/A"
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar resultados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 30));
        return btn;
    }
}