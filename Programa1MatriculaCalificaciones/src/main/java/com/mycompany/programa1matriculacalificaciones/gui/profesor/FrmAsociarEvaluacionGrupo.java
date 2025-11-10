package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        setTitle("Programar Evaluación para Grupo");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        initUI();
        cargarAsignaciones();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Programar Evaluación para Grupo - Profesor: " + profesorId, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(39, 174, 96));
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));

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

        // Panel para botones - SOLO botón de programar
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.setBackground(panel.getBackground());
        botonesPanel.setBorder(new EmptyBorder(15, 0, 10, 0));

        // ÚNICO botón: Programar Evaluación
        JButton btnProgramar = crearBoton("Programar Evaluación", new Color(52, 152, 219));
        btnProgramar.addActionListener(e -> programarEvaluacion());
        
        // Botón para eliminar asignación
        JButton btnEliminar = crearBoton("Eliminar Asignación", new Color(231, 76, 60));
        btnEliminar.addActionListener(e -> eliminarAsignacion());
        
        JButton btnActualizar = crearBoton("Actualizar", new Color(243, 156, 18));
        btnActualizar.addActionListener(e -> {
            cargarEvaluaciones();
            cargarGrupos();
            cargarAsignaciones();
        });
        
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> dispose());

        botonesPanel.add(btnProgramar);
        botonesPanel.add(btnEliminar);
        botonesPanel.add(btnActualizar);
        botonesPanel.add(btnRegresar);

        // Configuración de la tabla
        modeloTabla = new DefaultTableModel(new Object[]{
            "Evaluación", 
            "Grupo", 
            "Fecha Inicio", 
            "Fecha Cierre", 
            "Estado"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(39, 174, 96));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        // Ajustar anchos de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(200); // Evaluación
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150); // Grupo
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150); // Fecha Inicio
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150); // Fecha Cierre
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100); // Estado

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Evaluaciones Programadas"));
        scroll.setPreferredSize(new Dimension(900, 250));

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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 35));
        
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

    // MÉTODO PRINCIPAL: Para programar evaluación con fechas específicas
    private void programarEvaluacion() {
        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        Grupo grupo = (Grupo) cmbGrupo.getSelectedItem();

        if (eval == null || grupo == null) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una evaluación y un grupo", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Verificar que la evaluación pertenece al profesor
            if (!eval.getProfesorId().equals(profesorId)) {
                JOptionPane.showMessageDialog(this, 
                    "No puede programar evaluaciones que no son de su propiedad", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar que el grupo pertenece al profesor
            if (!grupo.getProfesorId().equals(profesorId)) {
                JOptionPane.showMessageDialog(this, 
                    "No puede programar evaluaciones a grupos que no son de su propiedad", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si ya existe esta asignación
            if (asignacionService.existeAsignacion(eval.getId(), grupo.getCodigo())) {
                JOptionPane.showMessageDialog(this, 
                    "Esta evaluación ya está programada para este grupo", 
                    "Programación duplicada", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Dialogo para ingresar fechas
            JPanel fechaPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            fechaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String fechaActual = LocalDateTime.now().format(formatter);
            String fechaMasUnaHora = LocalDateTime.now().plusHours(1).format(formatter);
            
            JTextField txtFechaInicio = new JTextField(fechaActual);
            JTextField txtFechaCierre = new JTextField(fechaMasUnaHora);
            
            fechaPanel.add(new JLabel("Fecha y Hora de Inicio:"));
            fechaPanel.add(txtFechaInicio);
            fechaPanel.add(new JLabel("Fecha y Hora de Cierre:"));
            fechaPanel.add(txtFechaCierre);
            fechaPanel.add(new JLabel("Formato: YYYY-MM-DD HH:MM"));
            fechaPanel.add(new JLabel("Ejemplo: 2024-01-15 14:30"));
            
            int resultado = JOptionPane.showConfirmDialog(this, fechaPanel, 
                "Programar Evaluación - " + eval.getTitulo(), 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (resultado != JOptionPane.OK_OPTION) {
                return;
            }
            
            // Validar y parsear fechas
            LocalDateTime fechaInicio;
            LocalDateTime fechaCierre;
            
            try {
                fechaInicio = LocalDateTime.parse(txtFechaInicio.getText().trim(), formatter);
                fechaCierre = LocalDateTime.parse(txtFechaCierre.getText().trim(), formatter);
                
                // Validar que la fecha de cierre sea posterior a la de inicio
                if (!fechaCierre.isAfter(fechaInicio)) {
                    JOptionPane.showMessageDialog(this, 
                        "La fecha de cierre debe ser posterior a la fecha de inicio", 
                        "Error en fechas", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar que la fecha de inicio no sea en el pasado (opcional)
                if (fechaInicio.isBefore(LocalDateTime.now().minusMinutes(1))) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                        "La fecha de inicio es en el pasado. ¿Desea continuar?",
                        "Confirmar fecha pasada",
                        JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, 
                    "Formato de fecha inválido. Use el formato: YYYY-MM-DD HH:MM\nEjemplo: 2024-01-15 14:30", 
                    "Error en formato", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmar programación
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Confirmar la programación de la evaluación?\n\n" +
                "Evaluación: " + eval.getTitulo() + "\n" +
                "Grupo: " + grupo.getCodigo() + "\n" +
                "Curso: " + grupo.getCurso().getNombre() + "\n" +
                "Fecha Inicio: " + fechaInicio.format(formatter) + "\n" +
                "Fecha Cierre: " + fechaCierre.format(formatter) + "\n" +
                "Estudiantes en el grupo: " + grupo.getEstudiantes().size(),
                "Confirmar programación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            // Crear y guardar la asignación programada
            EvaluacionAsignada asignacion = new EvaluacionAsignada(eval, grupo, fechaInicio, fechaCierre);
            asignacionService.agregar(asignacion);
            
            JOptionPane.showMessageDialog(this, 
                "Evaluación programada correctamente\n\n" +
                "Inicio: " + fechaInicio.format(formatter) + "\n" +
                "Cierre: " + fechaCierre.format(formatter), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarAsignaciones();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al programar evaluación: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarAsignacion() {
        int selectedRow = tabla.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una asignación de la tabla para eliminar", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String evaluacionNombre = (String) modeloTabla.getValueAt(selectedRow, 0);
        String grupoInfo = (String) modeloTabla.getValueAt(selectedRow, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar esta programación?\n\n" +
            "Evaluación: " + evaluacionNombre + "\n" +
            "Grupo: " + grupoInfo + "\n\n" +
            "Los estudiantes ya no podrán realizar esta evaluación.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = asignacionService.eliminarAsignacion(evaluacionNombre, grupoInfo);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, 
                        "Programación eliminada correctamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarAsignaciones();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No se pudo eliminar la programación", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar programación: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarAsignaciones() {
        modeloTabla.setRowCount(0);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            
            for (EvaluacionAsignada ea : asignacionService.listarAsignacionesPorProfesor(profesorId)) {
                String fechaInicio = ea.getFechaInicio() != null ? 
                    ea.getFechaInicio().format(formatter) : "No definida";
                
                String fechaCierre = ea.getFechaCierre() != null ? 
                    ea.getFechaCierre().format(formatter) : "Sin cierre";
                
                String estado = ea.estaVigente() ? "Vigente" : "Expirada";
                
                modeloTabla.addRow(new Object[]{
                    ea.getEvaluacion().getTitulo(),
                    ea.getGrupo().getCodigo() + " - " + ea.getGrupo().getCurso().getNombre(),
                    fechaInicio,
                    fechaCierre,
                    estado
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar programaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}