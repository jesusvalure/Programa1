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
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
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

        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBackground(panel.getBackground());

        cmbGrupo = new JComboBox<>(grupoService.listar().toArray(new Grupo[0]));
        cmbGrupo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        formPanel.add(new JLabel("Grupo:"));
        formPanel.add(cmbGrupo);

        JButton btnMatricular = crearBoton("Matricular", new Color(243, 156, 18));
        btnMatricular.addActionListener(e -> matricular());

        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> {
            dispose();
            new MenuEstudianteFrame().setVisible(true);
        });

        formPanel.add(btnMatricular);
        formPanel.add(btnRegresar);

        modeloTabla = new DefaultTableModel(new Object[]{"Grupo", "Curso", "Profesor", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Matrículas Realizadas"));

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
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

        // Verificar si ya está matriculado en este grupo
        for (Matricula m : matriculaService.listar()) {
            if (m.getEstudiante().getIdentificacion().equals(estudianteId) && 
                m.getGrupo().getCodigo().equals(grupo.getCodigo())) {
                JOptionPane.showMessageDialog(this, 
                    "Ya está matriculado en este grupo", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Matricula matricula = new Matricula(estudiante, grupo);
        matriculaService.agregar(matricula);
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
