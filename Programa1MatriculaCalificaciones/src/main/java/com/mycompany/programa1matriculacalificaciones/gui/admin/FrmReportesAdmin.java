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
    JButton btnExportarPDF = crearBoton("Exportar PDF", new Color(149, 165, 166));
    JButton btnEnviarCorreo = crearBoton("Enviar por correo", new Color(142, 68, 173));
    JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

        btnReporteEstudiantes.addActionListener(e -> mostrarReporteEstudiantes());
        btnReporteCursos.addActionListener(e -> mostrarReporteCursos());
        btnReporteProfesores.addActionListener(e -> mostrarReporteProfesores());
        btnReporteGrupos.addActionListener(e -> mostrarReporteGrupos());
        btnReporteResultados.addActionListener(e -> mostrarReporteResultados());
        btnExportarPDF.addActionListener(e -> {
            String contenido = obtenerUltimoReporte();
            if (contenido == null || contenido.isBlank()) {
                JOptionPane.showMessageDialog(this, "Genere primero un reporte para exportar.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new java.io.File("reporte.pdf"));
            int sel = fc.showSaveDialog(this);
            if (sel == JFileChooser.APPROVE_OPTION) {
                java.io.File f = fc.getSelectedFile();
                boolean ok = exportarTextoAPDF(contenido, f);
                JOptionPane.showMessageDialog(this, ok ? "PDF guardado en: " + f.getAbsolutePath() : "Error al generar PDF", "Exportar PDF", ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            }
        });
        btnEnviarCorreo.addActionListener(e -> {
            String contenido = obtenerUltimoReporte();
            if (contenido == null || contenido.isBlank()) {
                JOptionPane.showMessageDialog(this, "Genere primero un reporte para enviar.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String destino = JOptionPane.showInputDialog(this, "Ingrese correo destino:");
            if (destino == null || destino.isBlank()) return;
            com.mycompany.programa1matriculacalificaciones.servicio.MailService ms = new com.mycompany.programa1matriculacalificaciones.servicio.MailService();
            boolean enviado = ms.enviarCorreo(destino, "Reporte del sistema", contenido);
            JOptionPane.showMessageDialog(this, enviado ? "Correo enviado correctamente" : "Error al enviar correo (revise configuración)", "Enviar correo", enviado ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        });
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        botonesPanel.add(btnReporteEstudiantes);
        botonesPanel.add(btnReporteCursos);
        botonesPanel.add(btnReporteProfesores);
        botonesPanel.add(btnReporteGrupos);
        botonesPanel.add(btnReporteResultados);
    botonesPanel.add(btnExportarPDF);
    botonesPanel.add(btnEnviarCorreo);
        botonesPanel.add(btnRegresar);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(botonesPanel, BorderLayout.CENTER);

        add(panel);
    }

    // Guarda el último reporte mostrado en memoria para exportar/enviar
    private String ultimoReporte = null;

    private String obtenerUltimoReporte() {
        return ultimoReporte;
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
        ultimoReporte = reporte.toString();
    }

    private void mostrarReporteCursos() {
        StringBuilder reporte = new StringBuilder("REPORTE DE CURSOS\n");
        reporte.append("================================\n\n");
        for (Curso c : cursoService.listar()) {
            reporte.append(String.format("Código: %s\nNombre: %s\nCréditos: %d\n\n", 
                c.getCodigo(), c.getNombre(), c.getCreditos()));
        }
        mostrarReporte("Cursos", reporte.toString());
        ultimoReporte = reporte.toString();
    }

    private void mostrarReporteProfesores() {
        StringBuilder reporte = new StringBuilder("REPORTE DE PROFESORES\n");
        reporte.append("================================\n\n");
        for (Profesor p : profesorService.listar()) {
            reporte.append(String.format("ID: %s\nNombre: %s %s\n\n", 
                p.getIdentificacion(), p.getNombre(), p.getApellido1()));
        }
        mostrarReporte("Profesores", reporte.toString());
        ultimoReporte = reporte.toString();
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
        ultimoReporte = reporte.toString();
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
        ultimoReporte = reporte.toString();
    }

    private boolean exportarTextoAPDF(String texto, java.io.File destino) {
        try (org.apache.pdfbox.pdmodel.PDDocument doc = new org.apache.pdfbox.pdmodel.PDDocument()) {
            String[] lineas = texto.split("\n");
            org.apache.pdfbox.pdmodel.font.PDType1Font font = org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
            float fontSize = 12;
            float leading = 1.2f * fontSize;

            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            doc.addPage(page);
            org.apache.pdfbox.pdmodel.PDPageContentStream contents = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float x = margin;
            float y = yStart;

            contents.beginText();
            contents.setFont(font, fontSize);
            contents.newLineAtOffset(x, y);

            for (String linea : lineas) {
                // Salto de página si es necesario
                if (y - leading < margin) {
                    contents.endText();
                    contents.close();
                    page = new org.apache.pdfbox.pdmodel.PDPage();
                    doc.addPage(page);
                    contents = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
                    y = page.getMediaBox().getHeight() - margin;
                    contents.beginText();
                    contents.setFont(font, fontSize);
                    contents.newLineAtOffset(x, y);
                }
                contents.showText(linea);
                contents.newLineAtOffset(0, -leading);
                y -= leading;
            }

            contents.endText();
            contents.close();
            doc.save(destino);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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
