package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmGrupoCRUD extends JFrame {
    private GrupoService grupoService = new GrupoService();
    private CursoService cursoService = new CursoService();
    private ProfesorService profesorService = new ProfesorService();

    private JTextField txtCodigo;
    private JComboBox<Curso> cmbCurso;
    private JComboBox<Profesor> cmbProfesor;
    private JTable tabla;
    private DefaultTableModel model;

    public FrmGrupoCRUD() {
        setTitle("CRUD Grupos");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        txtCodigo = new JTextField();
        cmbCurso = new JComboBox<>(cursoService.listar().toArray(new Curso[0]));
        cmbProfesor = new JComboBox<>(profesorService.listar().toArray(new Profesor[0]));

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Curso:"));
        panel.add(cmbCurso);
        panel.add(new JLabel("Profesor:"));
        panel.add(cmbProfesor);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        panel.add(btnAgregar);
        panel.add(btnListar);
        panel.add(btnEliminar);

        model = new DefaultTableModel(new Object[]{"Código", "Curso", "Profesor"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> agregar());
        btnListar.addActionListener(e -> listar());
        btnEliminar.addActionListener(e -> eliminar());

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    private void agregar() {
        String codigo = txtCodigo.getText().trim();
        Curso curso = (Curso) cmbCurso.getSelectedItem();
        Profesor prof = (Profesor) cmbProfesor.getSelectedItem();

        if (codigo.isEmpty() || curso == null || prof == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos");
            return;
        }

        grupoService.agregar(new Grupo(codigo, curso, prof));
        JOptionPane.showMessageDialog(this, "Grupo agregado");
        listar();
    }

    private void listar() {
        model.setRowCount(0);
        for (Grupo g : grupoService.listar()) {
            model.addRow(new Object[]{g.getCodigo(), g.getCurso().getNombre(), g.getProfesor().getNombre()});
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;
        String codigo = (String) model.getValueAt(fila, 0);
        if (grupoService.eliminar(codigo)) {
            JOptionPane.showMessageDialog(this, "Grupo eliminado");
            listar();
        }
    }
}
