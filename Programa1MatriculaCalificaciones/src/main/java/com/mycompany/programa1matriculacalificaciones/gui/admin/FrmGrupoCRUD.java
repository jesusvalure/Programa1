package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;

public class FrmGrupoCRUD extends JFrame {
    private GrupoService grupoService = new GrupoService();
    private CursoService cursoService = new CursoService();
    private ProfesorCRUDService profesorService = new ProfesorCRUDService();

    private JTextField txtCodigo;
    private JComboBox<Curso> cmbCurso;
    private JComboBox<Profesor> cmbProfesor;
    private JTable tabla;
    private DefaultTableModel model;
    private Grupo grupoSeleccionado;

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
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        panel.add(btnAgregar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        model = new DefaultTableModel(new Object[]{"Código", "Curso", "Profesor"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarGrupoSeleccionado();
            }
        });
        
        listar(); // Cargar lista al iniciar

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            dispose();
            new MenuAdministradorFrame().setVisible(true);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegresar);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void agregar() {
        String codigo = txtCodigo.getText().trim();
        Curso curso = (Curso) cmbCurso.getSelectedItem();
        Profesor prof = (Profesor) cmbProfesor.getSelectedItem();

        if (codigo.isEmpty() || curso == null || prof == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (grupoService.buscar(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un grupo con este código", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        grupoService.agregar(new Grupo(codigo, curso, prof));
        JOptionPane.showMessageDialog(this, "Grupo agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        listar();
        limpiarCampos();
    }
    
    private void cargarGrupoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            grupoSeleccionado = null;
            return;
        }
        String codigo = (String) model.getValueAt(fila, 0);
        grupoSeleccionado = grupoService.buscar(codigo);
        if (grupoSeleccionado != null) {
            txtCodigo.setText(grupoSeleccionado.getCodigo());
            // Buscar y seleccionar el curso y profesor en los combos
            for (int i = 0; i < cmbCurso.getItemCount(); i++) {
                if (cmbCurso.getItemAt(i).getCodigo().equals(grupoSeleccionado.getCurso().getCodigo())) {
                    cmbCurso.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < cmbProfesor.getItemCount(); i++) {
                if (cmbProfesor.getItemAt(i).getIdentificacion().equals(grupoSeleccionado.getProfesor().getIdentificacion())) {
                    cmbProfesor.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    private void editar() {
        if (grupoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un grupo para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codigo = txtCodigo.getText().trim();
        Curso curso = (Curso) cmbCurso.getSelectedItem();
        Profesor prof = (Profesor) cmbProfesor.getSelectedItem();
        
        if (codigo.isEmpty() || curso == null || prof == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codigoOriginal = grupoSeleccionado.getCodigo();
        if (!codigo.equals(codigoOriginal) && grupoService.buscar(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un grupo con este código", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Grupo actualizado = new Grupo(codigo, curso, prof);
        // Preservar estudiantes si los hay
        if (grupoSeleccionado.getEstudiantes() != null && !grupoSeleccionado.getEstudiantes().isEmpty()) {
            for (Estudiante e : grupoSeleccionado.getEstudiantes()) {
                actualizado.agregarEstudiante(e);
            }
        }
        
        if (!codigo.equals(codigoOriginal)) {
            grupoService.eliminar(codigoOriginal);
            grupoService.agregar(actualizado);
        } else {
            grupoService.actualizar(actualizado);
        }
        
        JOptionPane.showMessageDialog(this, "Grupo actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        listar();
        limpiarCampos();
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        cmbCurso.setSelectedIndex(-1);
        cmbProfesor.setSelectedIndex(-1);
        grupoSeleccionado = null;
    }

    private void listar() {
        model.setRowCount(0);
        for (Grupo g : grupoService.listar()) {
            model.addRow(new Object[]{g.getCodigo(), g.getCurso().getNombre(), g.getProfesor().getNombre()});
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un grupo para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este grupo?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String codigo = (String) model.getValueAt(fila, 0);
            if (grupoService.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Grupo eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar grupo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
