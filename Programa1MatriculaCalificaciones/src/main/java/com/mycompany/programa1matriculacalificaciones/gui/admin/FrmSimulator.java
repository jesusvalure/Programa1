package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import com.mycompany.programa1matriculacalificaciones.app.Simulator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame sencillo para ejecutar la simulación desde la GUI y mostrar resultados.
 */
public class FrmSimulator extends JFrame {
    private JTextArea area;
    private JButton btnRun, btnExportPdf, btnClose;

    public FrmSimulator() {
        setTitle("Simulador integrado");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(new EmptyBorder(10,10,10,10));

        JLabel lbl = new JLabel("Simulación automática (Poblado de datos, evaluaciones y generación de reporte PDF)", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(area);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnRun = new JButton("Ejecutar simulación");
        btnExportPdf = new JButton("Abrir último PDF");
        btnClose = new JButton("Cerrar");

        btnExportPdf.setEnabled(false);

        botones.add(btnRun);
        botones.add(btnExportPdf);
        botones.add(btnClose);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        add(panel);

        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runSimulation();
            }
        });

        btnExportPdf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openLastPdf();
            }
        });

        btnClose.addActionListener(ev -> dispose());
    }

    private void runSimulation() {
        btnRun.setEnabled(false);
        area.setText("Ejecutando simulación...\n");
        // Run in background to avoid freezing UI
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            private String lastPdfPath = null;

            @Override
            protected Void doInBackground() throws Exception {
                // Call the Simulator main logic but capture console output by running its methods indirectly.
                // The Simulator prints to System.out; we will run it as a process-like call and capture output by redirecting System.out temporarily.
                java.io.PrintStream originalOut = System.out;
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                try {
                    System.setOut(ps);
                    Simulator.main(new String[0]);
                    ps.flush();
                    String out = baos.toString();
                    publish(out);
                    // Try to find generated PDF in target directory (most recent file matching pattern)
                    File target = new File("target");
                    File[] files = target.listFiles((dir, name) -> name.startsWith("reporte_evaluacion_") && name.endsWith(".pdf"));
                    if (files != null && files.length > 0) {
                        // pick the newest
                        File newest = files[0];
                        for (File f : files) if (f.lastModified() > newest.lastModified()) newest = f;
                        lastPdfPath = newest.getAbsolutePath();
                    }
                } finally {
                    System.setOut(originalOut);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String s : chunks) area.append(s + "\n");
            }

            @Override
            protected void done() {
                area.append("\nSimulación finalizada.\n");
                if (lastPdfPath != null) {
                    area.append("Último PDF: " + lastPdfPath + "\n");
                    btnExportPdf.setEnabled(true);
                    btnExportPdf.putClientProperty("pdfPath", lastPdfPath);
                } else {
                    area.append("No se encontró PDF generado.\n");
                }
                btnRun.setEnabled(true);
            }
        };
        worker.execute();
    }

    private void openLastPdf() {
        Object p = btnExportPdf.getClientProperty("pdfPath");
        if (p == null) return;
        try {
            Desktop.getDesktop().open(new File(p.toString()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el PDF: " + ex.getMessage());
        }
    }
}
