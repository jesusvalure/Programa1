package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;
import com.mycompany.programa1matriculacalificaciones.util.SesionActual;

public class FrmAsociarEvaluacionGrupo extends JFrame {
    private ProfesorService profesorService = new ProfesorService();
    private GrupoService grupoService = new GrupoService();
    private EvaluacionAsignadaService asignacionService = new EvaluacionAsignadaService();
    private String profesorId;
    
    private JComboBox<Evaluacion> cmbEvaluacion;
    private JComboBox<Grupo> cmbGrupo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FrmAsociarEvaluacionGrupo() {
        this.profesorId = SesionActual.getUsuarioId();
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

        JLabel lblTitulo = new JLabel("Asignar Evaluación a Grupo - Profesor: " + profesorId, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(39, 174, 96));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 0, 0));

        // Panel para el formulario de selección
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(panel.getBackground());
        formPanel.setBorder(BorderFactory.createTitledBorder("Seleccionar Evaluación y Grupo"));

        cmbEvaluacion = new JComboBox<>();
        cmbEvaluacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cargarEvaluaciones();
        
        cmbGrupo = new JComboBox<>();
        cmbGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cargarGrupos();

        formPanel.add(new JLabel("Evaluación:"));
        formPanel.add(cmbEvaluacion);
        formPanel.add(new JLabel("Grupo:"));
        formPanel.add(cmbGrupo);

        // Panel para botones
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.setBackground(panel.getBackground());
        botonesPanel.setBorder(new EmptyBorder(15, 0, 10, 0));

        JButton btnAsignar = crearBoton("Asignar", new Color(46, 204, 113));
        btnAsignar.addActionListener(e -> asignarEvaluacion());
        
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> dispose());

        botonesPanel.add(btnAsignar);
        botonesPanel.add(btnRegresar);

        // Configuración de la tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Evaluación", "Grupo", "Fecha Asignación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(new Color(39, 174, 96));
        tabla.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Evaluaciones Asignadas"));
        scroll.setPreferredSize(new Dimension(800, 250));

        // Panel central que contiene el formulario y botones
        JPanel centerPanel = new JPanel(new BorderLayout(0, 15));
        centerPanel.setBackground(panel.getBackground());
        centerPanel.setBorder(new EmptyBorder(10, 0, 15, 0));
        
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(botonesPanel, BorderLayout.CENTER);

        // Organización final de los componentes
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
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
        
        // Efecto hover para el botón
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }

    private void cargarEvaluaciones() {
        cmbEvaluacion.removeAllItems();
        try {
            for (Evaluacion eval : profesorService.listarEvaluacionesPorProfesor(profesorId)) {
                cmbEvaluacion.addItem(eval);
            }
            if (cmbEvaluacion.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No tiene evaluaciones creadas. Cree una evaluación primero.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar evaluaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarGrupos() {
        cmbGrupo.removeAllItems();
        try {
            for (Grupo grupo : grupoService.listarGruposPorProfesor(profesorId)) {
                cmbGrupo.addItem(grupo);
            }
            if (cmbGrupo.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No tiene grupos asignados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar grupos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void asignarEvaluacion() {
        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        Grupo grupo = (Grupo) cmbGrupo.getSelectedItem();

        if (eval == null || grupo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación y un grupo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Verificar que la evaluación pertenece al profesor
            if (!eval.getProfesorId().equals(profesorId)) {
                JOptionPane.showMessageDialog(this, "No puede asignar evaluaciones que no son de su propiedad", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar que el grupo pertenece al profesor
            if (!grupo.getProfesorId().equals(profesorId)) {
                JOptionPane.showMessageDialog(this, "No puede asignar evaluaciones a grupos que no son de su propiedad", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            EvaluacionAsignada asignacion = new EvaluacionAsignada(eval, grupo, LocalDateTime.now());
            asignacionService.agregar(asignacion);
            JOptionPane.showMessageDialog(this, "Evaluación asignada correctamente al grupo", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarAsignaciones();
            
            // Limpiar selección
            cmbEvaluacion.setSelectedIndex(0);
            cmbGrupo.setSelectedIndex(0);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al asignar evaluación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarAsignaciones() {
        modeloTabla.setRowCount(0);
        try {
            for (EvaluacionAsignada ea : asignacionService.listarAsignacionesPorProfesor(profesorId)) {
                modeloTabla.addRow(new Object[]{
                    ea.getEvaluacion().getTitulo(),
                    ea.getGrupo().getCodigo() + " - " + ea.getGrupo().getCurso().getNombre(),
                    ea.getFechaInicio().toString()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}