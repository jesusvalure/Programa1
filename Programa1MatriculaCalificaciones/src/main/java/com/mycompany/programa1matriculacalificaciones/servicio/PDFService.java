package com.mycompany.programa1matriculacalificaciones.servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.List;
import com.mycompany.programa1matriculacalificaciones.modelo.Evaluacion;

public class PDFService {

    public void generarReporteEvaluaciones(List<Evaluacion> evaluaciones, String ruta) {
        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            Paragraph titulo = new Paragraph("Reporte de Evaluaciones\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("ID");
            tabla.addCell("Título");
            tabla.addCell("Tipo");
            tabla.addCell("Aleatorio");

            for (Evaluacion e : evaluaciones) {
                tabla.addCell(e.getId());
                tabla.addCell(e.getTitulo());
                tabla.addCell(e.getTipo());
                tabla.addCell(e.isOrdenAleatorio() ? "Sí" : "No");
            }

            doc.add(tabla);
            doc.close();

            System.out.println("PDF generado en: " + ruta);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
