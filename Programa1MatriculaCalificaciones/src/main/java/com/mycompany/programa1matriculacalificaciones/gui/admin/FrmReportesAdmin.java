package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmReportesAdmin extends JFrame {
    private AdministradorService adminService = new AdministradorService();
    private CursoService cursoService = new CursoService();
    private ProfesorCRUDService profesorService = new ProfesorCRUDService();
    private GrupoService grupoService = new GrupoService();
    private ResultadoService resultadoService = new ResultadoService();

    public FrmReportesAdmin() {
        setTitle("Reportes Administrativos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Reportes Administrativos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(41, 128, 185));

        JPanel botonesPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        botonesPanel.setBackground(panel.getBackground());

        JButton btnReporteEstudiantes = crearBoton("Reporte Estudiantes", new Color(52, 152, 219));
        JButton btnReporteCursos = crearBoton("Reporte Cursos", new Color(52, 152, 219));
        JButton btnReporteProfesores = crearBoton("Reporte Profesores", new Color(52, 152, 219));
        JButton btnReporteGrupos = crearBoton("Reporte Grupos", new Color(52, 152, 219));
        JButton btnReporteResultados = crearBoton("Reporte Resultados", new Color(52, 152, 219));
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

        btnReporteEstudiantes.addActionListener(e -> mostrarReporteEstudiantes());
        btnReporteCursos.addActionListener(e -> mostrarReporteCursos());
        btnReporteProfesores.addActionListener(e -> mostrarReporteProfesores());
        btnReporteGrupos.addActionListener(e -> mostrarReporteGrupos());
        btnReporteResultados.addActionListener(e -> mostrarReporteResultados());
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        botonesPanel.add(btnReporteEstudiantes);
        botonesPanel.add(btnReporteCursos);
        botonesPanel.add(btnReporteProfesores);
        botonesPanel.add(btnReporteGrupos);
        botonesPanel.add(btnReporteResultados);
        botonesPanel.add(btnRegresar);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(botonesPanel, BorderLayout.CENTER);

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
        return btn;
    }

    private void mostrarReporteEstudiantes() {
        StringBuilder reporte = new StringBuilder("REPORTE DE ESTUDIANTES\n");
        reporte.append("================================\n\n");
        
        List<Estudiante> estudiantes = adminService.listarEstudiantes();
        if (estudiantes == null || estudiantes.isEmpty()) {
            reporte.append("No hay estudiantes registrados.\n");
        } else {
            for (Estudiante e : estudiantes) {
                if (e != null) {
                    String id = e.getIdentificacion() != null ? e.getIdentificacion() : "N/A";
                    String nombre = e.getNombre() != null ? e.getNombre() : "N/A";
                    String apellido = e.getApellido1() != null ? e.getApellido1() : "N/A";
                    reporte.append(String.format("ID: %s\nNombre: %s %s\n\n", id, nombre, apellido));
                }
            }
        }
        
        if (reporte.toString().equals("REPORTE DE ESTUDIANTES\n================================\n\n")) {
            reporte.append("No hay estudiantes para mostrar.\n");
        }
        
        mostrarReporte("Estudiantes", reporte.toString());
    }

    private void mostrarReporteCursos() {
        StringBuilder reporte = new StringBuilder("REPORTE DE CURSOS\n");
        reporte.append("================================\n\n");
        for (Curso c : cursoService.listar()) {
            reporte.append(String.format("Código: %s\nNombre: %s\nCréditos: %d\n\n", 
                c.getCodigo(), c.getNombre(), c.getCreditos()));
        }
        mostrarReporte("Cursos", reporte.toString());
    }

    private void mostrarReporteProfesores() {
        StringBuilder reporte = new StringBuilder("REPORTE DE PROFESORES\n");
        reporte.append("================================\n\n");
        for (Profesor p : profesorService.listar()) {
            reporte.append(String.format("ID: %s\nNombre: %s %s\n\n", 
                p.getIdentificacion(), p.getNombre(), p.getApellido1()));
        }
        mostrarReporte("Profesores", reporte.toString());
    }

    private void mostrarReporteGrupos() {
        StringBuilder reporte = new StringBuilder("REPORTE DE GRUPOS\n");
        reporte.append("================================\n\n");
        for (Grupo g : grupoService.listar()) {
            reporte.append(String.format("Código: %s\nCurso: %s\nProfesor: %s %s\nEstudiantes: %d\n\n", 
                g.getCodigo(), g.getCurso().getNombre(), 
                g.getProfesor().getNombre(), g.getProfesor().getApellido1(),
                g.getEstudiantes().size()));
        }
        mostrarReporte("Grupos", reporte.toString());
    }

    private void mostrarReporteResultados() {
        StringBuilder reporte = new StringBuilder("REPORTE DE RESULTADOS\n");
        reporte.append("================================\n\n");
        for (ResultadoEvaluacion r : resultadoService.listarResultados()) {
            reporte.append(String.format("Estudiante: %s\nEvaluación: %s\nPuntaje: %.1f / %.1f\nNota: %.1f%%\n\n",
                r.getEstudiante(), r.getTituloEvaluacion(),
                r.getPuntajeObtenido(), r.getPuntajeTotal(), r.getNotaPorcentaje()));
        }
        mostrarReporte("Resultados", reporte.toString());
    }

    private void mostrarReporte(String titulo, String contenido) {
        JTextArea areaTexto = new JTextArea(contenido);
        areaTexto.setFont(new Font("Courier New", Font.PLAIN, 12));
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(this, scroll, "Reporte de " + titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
