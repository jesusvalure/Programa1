package com.mycompany.programa1matriculacalificaciones.servicio;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion;

import java.io.IOException;
import java.util.List;

public class PDFService {

    public void generarReporteEvaluaciones(List<Evaluacion> evaluaciones, String ruta) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                // Título
                content.setFont(PDType1Font.HELVETICA_BOLD, 18);
                content.beginText();
                content.newLineAtOffset(220, 770);
                content.showText("Reporte de Evaluaciones");
                content.endText();

                // Encabezados de tabla
                content.setFont(PDType1Font.HELVETICA_BOLD, 12);
                float y = 730;
                float x = 50;
                float rowHeight = 20;

                content.beginText();
                content.newLineAtOffset(x, y);
                content.showText(String.format("%-15s %-35s %-20s %-10s", "ID", "Título", "Tipo", "Aleatorio"));
                content.endText();

                // Contenido
                content.setFont(PDType1Font.HELVETICA, 11);
                y -= rowHeight;

                for (Evaluacion e : evaluaciones) {
                    if (y < 50) {
                        content.close();
                        page = new PDPage(PDRectangle.A4);
                        doc.addPage(page);
                        y = 770;
                    }

                    content.beginText();
                    content.newLineAtOffset(x, y);
                    content.showText(String.format("%-15s %-35s %-20s %-10s",
                            e.getId(),
                            e.getTitulo(),
                            e.getTipo(),
                            e.isOrdenAleatorio() ? "Sí" : "No"));
                    content.endText();

                    y -= rowHeight;
                }
            }

            doc.save(ruta);
            System.out.println("PDF generado exitosamente en: " + ruta);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
