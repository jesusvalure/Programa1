package com.mycompany.programa1matriculacalificaciones.gui.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import com.mycompany.programa1matriculacalificaciones.servicio.CursoService;
import com.mycompany.programa1matriculacalificaciones.modelo.Curso;

public class FrmCursoCRUD extends JFrame {
    private CursoService cursoService = new CursoService();
    private JTextField txtCodigo, txtNombre, txtCreditos;
    private JTable tabla;
    private DefaultTableModel model;
    private Curso cursoSeleccionado;

    public FrmCursoCRUD() {
        setTitle("CRUD Cursos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        txtCodigo = new JTextField();
        txtNombre = new JTextField();
        txtCreditos = new JTextField();

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Créditos:"));
        panel.add(txtCreditos);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panel.add(btnAgregar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);

        model = new DefaultTableModel(new Object[]{"Código", "Nombre", "Créditos"}, 0);
        tabla = new JTable(model);

        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarCursoSeleccionado();
            }
        });

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
        
        listar(); // Cargar lista al iniciar
    }

    private void agregar() {
        try {
            String cod = txtCodigo.getText().trim();
            String nom = txtNombre.getText().trim();
            String credStr = txtCreditos.getText().trim();
            
            if (cod.isEmpty() || nom.isEmpty() || credStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int cred = Integer.parseInt(credStr);
            
            if (cred <= 0) {
                JOptionPane.showMessageDialog(this, "Los créditos deben ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cursoService.buscar(cod) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un curso con este código", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cursoService.agregar(new Curso(cod, nom, cred));
            JOptionPane.showMessageDialog(this, "Curso agregado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listar();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los créditos deben ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarCursoSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            cursoSeleccionado = null;
            return;
        }
        String codigo = (String) model.getValueAt(fila, 0);
        cursoSeleccionado = cursoService.buscar(codigo);
        if (cursoSeleccionado != null) {
            txtCodigo.setText(cursoSeleccionado.getCodigo());
            txtNombre.setText(cursoSeleccionado.getNombre());
            txtCreditos.setText(String.valueOf(cursoSeleccionado.getCreditos()));
        }
    }
    
    private void editar() {
        if (cursoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso para editar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String cod = txtCodigo.getText().trim();
            String nom = txtNombre.getText().trim();
            String credStr = txtCreditos.getText().trim();
            
            if (cod.isEmpty() || nom.isEmpty() || credStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int cred = Integer.parseInt(credStr);
            
            if (cred <= 0) {
                JOptionPane.showMessageDialog(this, "Los créditos deben ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String codigoOriginal = cursoSeleccionado.getCodigo();
            if (!cod.equals(codigoOriginal) && cursoService.buscar(cod) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un curso con este código", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!cod.equals(codigoOriginal)) {
                // Si cambió el código, eliminar el viejo y agregar el nuevo
                cursoService.eliminar(codigoOriginal);
                cursoService.agregar(new Curso(cod, nom, cred));
            } else {
                // Si no cambió el código, solo actualizar
                cursoService.actualizar(new Curso(cod, nom, cred));
            }
            JOptionPane.showMessageDialog(this, "Curso actualizado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listar();
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Los créditos deben ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCreditos.setText("");
        cursoSeleccionado = null;
    }

    private void listar() {
        model.setRowCount(0);
        for (Curso c : cursoService.listar()) {
            model.addRow(new Object[]{c.getCodigo(), c.getNombre(), c.getCreditos()});
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un curso para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este curso?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            String codigo = (String) model.getValueAt(fila, 0);
            if (cursoService.eliminar(codigo)) {
                JOptionPane.showMessageDialog(this, "Curso eliminado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                listar();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar curso", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
