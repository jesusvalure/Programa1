package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.EvaluacionService;
import com.mycompany.programa1matriculacalificaciones.servicio.PDFService;

public class FrmReporteEvaluacion extends JFrame {
    private EvaluacionService service = new EvaluacionService();
    private PDFService pdf = new PDFService();

    public FrmReporteEvaluacion() {
        setTitle("Generar Reporte de Evaluaciones");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JButton btnGenerar = new JButton("Generar PDF");
        btnGenerar.addActionListener(e -> generar());

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(btnGenerar);
        add(panel);
    }

    private void generar() {
        pdf.generarReporteEvaluaciones(service.listar(), "reporte_evaluaciones.pdf");
        JOptionPane.showMessageDialog(this, "Reporte generado como reporte_evaluaciones.pdf");
    }
}
