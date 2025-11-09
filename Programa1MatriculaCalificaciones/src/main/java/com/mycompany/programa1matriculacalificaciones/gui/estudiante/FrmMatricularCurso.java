package com.mycompany.programa1matriculacalificaciones.gui.estudiante;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmMatricularCurso extends JFrame {
    private GrupoService grupoService = new GrupoService();
    private AdministradorService estudianteService = new AdministradorService();
    private MatriculaService matriculaService = new MatriculaService();
    private String estudianteId;

    private JComboBox<Grupo> cmbGrupo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public FrmMatricularCurso(String estudianteId) {
        this.estudianteId = estudianteId != null ? estudianteId : 
            com.mycompany.programa1matriculacalificaciones.util.SesionActual.getUsuarioId();
        setTitle("Matricular Curso");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(950, 650));
        initUI();
        cargarMatriculas();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Matricular en Curso", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(230, 126, 34));

        // Panel de formulario mejorado
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(panel.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Inicializar combo con renderer personalizado
        java.util.List<Grupo> grupos = grupoService.listar();
        cmbGrupo = new JComboBox<>(grupos.toArray(new Grupo[0]));
        cmbGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbGrupo.setPreferredSize(new Dimension(350, 30));
        
        if (grupos.isEmpty()) {
            cmbGrupo.setEnabled(false);
            cmbGrupo.addItem(null);
        }
        
        cmbGrupo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Grupo) {
                    Grupo g = (Grupo) value;
                    setText(g.getCodigo() + " - " + g.getCurso().getNombre() + " (" + g.getProfesor().getNombre() + ")");
                } else {
                    setText("No hay grupos disponibles");
                }
                return this;
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Seleccione un grupo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbGrupo, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(panel.getBackground());

        JButton btnMatricular = crearBoton("Matricular", new Color(243, 156, 18));
        btnMatricular.addActionListener(e -> matricular());

        JButton btnActualizar = crearBoton("Actualizar Lista", new Color(52, 152, 219));
        btnActualizar.addActionListener(e -> actualizarComboGrupos());

        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        panelBotones.add(btnMatricular);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnRegresar);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(panelBotones, gbc);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Grupo", "Curso", "Profesor", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);
        tabla.setPreferredScrollableViewportSize(new Dimension(0, 200));
        
        // Ajustar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Matrículas Realizadas"));
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
    
    private void actualizarComboGrupos() {
        if (cmbGrupo == null) {
            return;
        }
        
        Grupo seleccionado = (Grupo) cmbGrupo.getSelectedItem();
        cmbGrupo.removeAllItems();
        
        java.util.List<Grupo> grupos = grupoService.listar();
        if (grupos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay grupos disponibles. Contacte al administrador.", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (Grupo g : grupos) {
            cmbGrupo.addItem(g);
        }
        
        // Restaurar selección si existe
        if (seleccionado != null) {
            for (int i = 0; i < cmbGrupo.getItemCount(); i++) {
                Grupo g = cmbGrupo.getItemAt(i);
                if (g.getCodigo().equals(seleccionado.getCodigo())) {
                    cmbGrupo.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        // Si no hay selección, seleccionar el primero
        if (cmbGrupo.getItemCount() > 0 && cmbGrupo.getSelectedItem() == null) {
            cmbGrupo.setSelectedIndex(0);
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

    private void matricular() {
        Grupo grupo = (Grupo) cmbGrupo.getSelectedItem();
        if (grupo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un grupo", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar estudiante por ID del usuario logueado
        Estudiante estudiante = estudianteService.buscarPorId(estudianteId);
        
        if (estudiante == null) {
            JOptionPane.showMessageDialog(this, 
                "Estudiante no encontrado con ID: " + estudianteId + "\nContacte al administrador.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Matricula matricula = new Matricula(estudiante, grupo);
        boolean agregado = matriculaService.agregar(matricula);
        if (!agregado) {
            JOptionPane.showMessageDialog(this, 
                "Ya está matriculado en este grupo", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        grupo.agregarEstudiante(estudiante);
        grupoService.actualizar(grupo);
        
        JOptionPane.showMessageDialog(this, 
            "Matriculado correctamente en el grupo: " + grupo.getCodigo() + 
            "\nCurso: " + grupo.getCurso().getNombre(), 
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cargarMatriculas();
    }

    private void cargarMatriculas() {
        modeloTabla.setRowCount(0);
        // Solo mostrar matrículas del estudiante logueado
        for (Matricula m : matriculaService.listar()) {
            if (m.getEstudiante().getIdentificacion().equals(estudianteId)) {
                modeloTabla.addRow(new Object[]{
                    m.getGrupo().getCodigo(),
                    m.getGrupo().getCurso().getNombre(),
                    m.getGrupo().getProfesor().getNombre(),
                    m.getFechaMatricula().toString()
                });
            }
        }
    }
}
