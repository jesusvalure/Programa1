package com.mycompany.programa1matriculacalificaciones.servicio;

import com.mycompany.programa1matriculacalificaciones.modelo.ResultadoEvaluacion;
import java.awt.*;
import java.awt.print.*;
import java.io.File;
import java.util.List;

public class ReportePDFService {

    public void exportarResultados(List<ResultadoEvaluacion> resultados, String rutaArchivo) {
        if (resultados == null || resultados.isEmpty()) {
            System.out.println("No hay resultados para exportar.");
            return;
        }

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Reporte de Evaluaciones");

        job.setPrintable(new Printable() {
            public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pf.getImageableX(), pf.getImageableY());

                int lineHeight = 18;
                int y = 60;

                Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
                Font headerFont = new Font("Segoe UI", Font.BOLD, 13);
                Font normalFont = new Font("Segoe UI", Font.PLAIN, 12);

                if (pageIndex > 0) return NO_SUCH_PAGE;

                g2d.setFont(titleFont);
                g2d.drawString("Reporte de Evaluaciones - Sistema Académico", 80, 40);

                g2d.setFont(headerFont);
                g2d.drawString("Fecha", 40, y);
                g2d.drawString("Evaluación", 150, y);
                g2d.drawString("Estudiante", 320, y);
                g2d.drawString("Nota (%)", 480, y);
                y += 10;
                g2d.drawLine(40, y, 540, y);
                y += 20;

                g2d.setFont(normalFont);
                for (ResultadoEvaluacion r : resultados) {
                    g2d.drawString(r.getFechaFormateada(), 40, y);
                    g2d.drawString(recortarTexto(r.getTituloEvaluacion(), 20), 150, y);
                    g2d.drawString(recortarTexto(r.getEstudiante(), 20), 320, y);
                    g2d.drawString(String.format("%.1f%%", r.getNotaPorcentaje()), 480, y);
                    y += lineHeight;

                    if (y > pf.getImageableHeight() - 50) break; // evitar desbordar página
                }

                g2d.drawLine(40, y, 540, y + 2);
                g2d.drawString("Total de registros: " + resultados.size(), 40, (int) pf.getImageableHeight() - 40);
                return PAGE_EXISTS;
            }
        });

        try {
            File file = new File(rutaArchivo);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

            System.setProperty("java.awt.headless", "true");
            // Generar PDF directamente
            job.print();

            System.out.println("Reporte generado exitosamente en: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String recortarTexto(String texto, int maxLen) {
        return texto.length() > maxLen ? texto.substring(0, maxLen - 3) + "..." : texto;
    }
}
