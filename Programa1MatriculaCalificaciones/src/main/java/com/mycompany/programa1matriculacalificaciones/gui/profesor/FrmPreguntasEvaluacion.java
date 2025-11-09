package com.mycompany.programa1matriculacalificaciones.gui.profesor;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;
import com.mycompany.programa1matriculacalificaciones.modelo.*;
import com.mycompany.programa1matriculacalificaciones.modelo.pregunta.*;
import com.mycompany.programa1matriculacalificaciones.servicio.ProfesorService;
import com.mycompany.programa1matriculacalificaciones.util.SesionActual;

public class FrmPreguntasEvaluacion extends JFrame {

    private JComboBox<Evaluacion> cmbEvaluacion;
    private JTable tablaPreguntas;
    private DefaultTableModel modeloTabla;
    private ProfesorService profesorService = new ProfesorService();
    private String profesorId;

    public FrmPreguntasEvaluacion() {
        this.profesorId = SesionActual.getUsuarioId();
        setTitle("Gestión de Preguntas - Profesor: " + profesorId);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));
        panel.setBackground(new Color(245, 245, 245));

        JLabel lblTitulo = new JLabel("Gestión de Preguntas por Evaluación - Profesor: " + profesorId, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(39, 174, 96));

        // Cargar solo evaluaciones del profesor
        cmbEvaluacion = new JComboBox<>(profesorService.listarEvaluacionesPorProfesor(profesorId).toArray(new Evaluacion[0]));
        cmbEvaluacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbEvaluacion.setBackground(Color.WHITE);
        cmbEvaluacion.setBorder(BorderFactory.createTitledBorder("Seleccione Evaluación"));
        cmbEvaluacion.addActionListener(e -> cargarPreguntas());

        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Tipo", "Enunciado", "Valor"}, 0);
        tablaPreguntas = new JTable(modeloTabla);
        tablaPreguntas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaPreguntas.setRowHeight(25);
        tablaPreguntas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPreguntas.setGridColor(new Color(220, 220, 220));

        JScrollPane scroll = new JScrollPane(tablaPreguntas);
        scroll.setBorder(BorderFactory.createTitledBorder("Preguntas de la Evaluación"));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(panel.getBackground());

        JButton btnAgregar = crearBoton("Agregar", new Color(46, 204, 113));
        JButton btnEditar = crearBoton("Editar", new Color(52, 152, 219));
        JButton btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        JButton btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));

        btnAgregar.addActionListener(e -> agregarPregunta());
        btnEditar.addActionListener(e -> editarPregunta());
        btnEliminar.addActionListener(e -> eliminarPregunta());
        btnRegresar.addActionListener(e -> {
            dispose();
        });

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnRegresar);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(cmbEvaluacion, BorderLayout.PAGE_START);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.PAGE_END);

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
        btn.setPreferredSize(new Dimension(130, 35));
        return btn;
    }

    private void cargarPreguntas() {
        modeloTabla.setRowCount(0);
        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        if (eval == null) return;

        // Verificar que la evaluación pertenece al profesor
        if (!eval.getProfesorId().equals(profesorId)) {
            JOptionPane.showMessageDialog(this, "No puede acceder a evaluaciones que no son de su propiedad", "Error", JOptionPane.ERROR_MESSAGE);
            cmbEvaluacion.setSelectedIndex(0);
            return;
        }

        for (Pregunta p : eval.getPreguntas()) {
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getTipo(),
                p.getEnunciado(),
                p.getValor()
            });
        }
    }

    private void agregarPregunta() {
        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        if (eval == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación primero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar que la evaluación pertenece al profesor
        if (!eval.getProfesorId().equals(profesorId)) {
            JOptionPane.showMessageDialog(this, "No puede modificar evaluaciones que no son de su propiedad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] tiposPregunta = {
            "Selección única", 
            "Selección múltiple", 
            "Falso/verdadero", 
            "Pareo", 
            "Sopa de letras"
        };
        
        String tipoSeleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione el tipo de pregunta:",
            "Tipo de Pregunta",
            JOptionPane.QUESTION_MESSAGE,
            null,
            tiposPregunta,
            tiposPregunta[0]
        );
        
        if (tipoSeleccionado == null) {
            return;
        }

        String enunciado = JOptionPane.showInputDialog(this, "Ingrese el enunciado de la pregunta:");
        if (enunciado == null || enunciado.trim().isEmpty()) {
            return;
        }

        double valor = pedirValor();
        if (valor <= 0) return;

        Pregunta nueva;

        try {
            switch (tipoSeleccionado.toLowerCase()) {
                case "selección única":
                    nueva = crearSeleccionUnica(enunciado, valor);
                    break;
                case "selección múltiple":
                    nueva = crearSeleccionMultiple(enunciado, valor);
                    break;
                case "falso/verdadero":
                    nueva = crearFalsoVerdadero(enunciado, valor);
                    break;
                case "pareo":
                    nueva = crearPareo(enunciado, valor);
                    break;
                case "sopa de letras":
                    nueva = crearSopaDeLetras(enunciado, valor);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de pregunta no compatible", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

            if (nueva != null) {
                eval.agregarPregunta(nueva);
                profesorService.actualizarEvaluacion(eval);
                cargarPreguntas();
                JOptionPane.showMessageDialog(this, "Pregunta agregada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear la pregunta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private SeleccionUnica crearSeleccionUnica(String enunciado, double valor) {
        String opcionesStr = JOptionPane.showInputDialog(this, 
            "Ingrese las opciones separadas por comas (ej: Opción A, Opción B, Opción C, Opción D):");
        if (opcionesStr == null || opcionesStr.trim().isEmpty()) {
            opcionesStr = "A, B, C, D";
        }
        java.util.List<String> opcionesList = Arrays.asList(opcionesStr.split(","));
        java.util.List<String> opciones = opcionesList.stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        
        String correctaStr = JOptionPane.showInputDialog(this, 
            "¿Cuál es la opción correcta? (1-" + opciones.size() + "):");
        int indiceCorrecto = 0;
        try {
            int opcion = Integer.parseInt(correctaStr.trim());
            if (opcion >= 1 && opcion <= opciones.size()) {
                indiceCorrecto = opcion - 1;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número inválido, se usará la primera opción", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
        return new SeleccionUnica(enunciado, valor, opciones, indiceCorrecto);
    }
    
    private SeleccionMultiple crearSeleccionMultiple(String enunciado, double valor) {
        String opcionesStr = JOptionPane.showInputDialog(this, 
            "Ingrese las opciones separadas por comas (ej: Opción A, Opción B, Opción C, Opción D):");
        if (opcionesStr == null || opcionesStr.trim().isEmpty()) {
            opcionesStr = "A, B, C, D";
        }
        java.util.List<String> opcionesList = Arrays.asList(opcionesStr.split(","));
        java.util.List<String> opciones = opcionesList.stream().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        
        String correctasStr = JOptionPane.showInputDialog(this, 
            "Ingrese los números de las opciones correctas separados por comas (ej: 1,2):");
        Set<Integer> indicesCorrectos = new HashSet<>();
        if (correctasStr != null && !correctasStr.trim().isEmpty()) {
            try {
                String[] nums = correctasStr.split(",");
                for (String num : nums) {
                    int idx = Integer.parseInt(num.trim()) - 1;
                    if (idx >= 0 && idx < opciones.size()) {
                        indicesCorrectos.add(idx);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Números inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (indicesCorrectos.isEmpty()) {
            indicesCorrectos.add(0);
        }
        
        return new SeleccionMultiple(enunciado, valor, opciones, indicesCorrectos);
    }
    
    private FalsoVerdadero crearFalsoVerdadero(String enunciado, double valor) {
        int respuesta = JOptionPane.showConfirmDialog(this, 
            "¿La respuesta es Verdadero?", 
            "Seleccione respuesta", 
            JOptionPane.YES_NO_OPTION);
        boolean esVerdadero = (respuesta == JOptionPane.YES_OPTION);
        return new FalsoVerdadero(enunciado, valor, esVerdadero);
    }
    
    private Pareo crearPareo(String enunciado, double valor) {
        String paresStr = JOptionPane.showInputDialog(this, 
            "Ingrese los pares en formato 'Izquierda: Derecha' separados por punto y coma\n" +
            "Ejemplo: Costa Rica:San José;Francia:París;España:Madrid");
        Map<String, String> pares = new LinkedHashMap<>();
        if (paresStr != null && !paresStr.trim().isEmpty()) {
            String[] paresArray = paresStr.split(";");
            for (String par : paresArray) {
                String[] partes = par.split(":");
                if (partes.length == 2) {
                    pares.put(partes[0].trim(), partes[1].trim());
                }
            }
        }
        if (pares.isEmpty()) {
            pares.put("Costa Rica", "San José");
            pares.put("Francia", "París");
        }
        return new Pareo(enunciado, valor, pares);
    }
    
    private SopaDeLetras crearSopaDeLetras(String enunciado, double valor) {
        String palabrasStr = JOptionPane.showInputDialog(this, 
            "Ingrese las palabras a buscar separadas por comas (ej: JAVA, POO, HERENCIA):");
        java.util.List<String> palabras = new ArrayList<>();
        if (palabrasStr != null && !palabrasStr.trim().isEmpty()) {
            java.util.List<String> palabrasTemp = Arrays.asList(palabrasStr.split(","));
            palabras = palabrasTemp.stream().map(s -> s.trim().toUpperCase()).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        }
        if (palabras.isEmpty()) {
            palabras = Arrays.asList("JAVA", "POO");
        }
        
        String tamanoStr = JOptionPane.showInputDialog(this, 
            "Ingrese el tamaño de la sopa de letras (ej: 10 para 10x10):", "10");
        int tamano = 10;
        try {
            tamano = Integer.parseInt(tamanoStr.trim());
            if (tamano < 5) tamano = 5;
            if (tamano > 30) tamano = 30;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tamaño inválido, se usará 10", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        
        return new SopaDeLetras(enunciado, valor, palabras, tamano);
    }

    private void editarPregunta() {
        int fila = tablaPreguntas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una pregunta para editar");
            return;
        }

        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        
        // Verificar que la evaluación pertenece al profesor
        if (!eval.getProfesorId().equals(profesorId)) {
            JOptionPane.showMessageDialog(this, "No puede modificar evaluaciones que no son de su propiedad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);

        for (Pregunta p : eval.getPreguntas()) {
            if (p.getId().equals(id)) {
                String nuevoEnunciado = JOptionPane.showInputDialog(this, "Nuevo enunciado:", p.getEnunciado());
                if (nuevoEnunciado != null && !nuevoEnunciado.trim().isEmpty()) {
                    p.setEnunciado(nuevoEnunciado);
                }
                double nuevoValor = pedirValor();
                if (nuevoValor > 0) p.setValor(nuevoValor);
                break;
            }
        }

        profesorService.actualizarEvaluacion(eval);
        cargarPreguntas();
        JOptionPane.showMessageDialog(this, "Pregunta actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarPregunta() {
        int fila = tablaPreguntas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una pregunta para eliminar");
            return;
        }

        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        
        // Verificar que la evaluación pertenece al profesor
        if (!eval.getProfesorId().equals(profesorId)) {
            JOptionPane.showMessageDialog(this, "No puede modificar evaluaciones que no son de su propiedad", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = (String) modeloTabla.getValueAt(fila, 0);

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de que desea eliminar esta pregunta?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
            
        if (confirmacion == JOptionPane.YES_OPTION) {
            eval.eliminarPregunta(id);
            profesorService.actualizarEvaluacion(eval);
            cargarPreguntas();
            JOptionPane.showMessageDialog(this, "Pregunta eliminada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private double pedirValor() {
        try {
            String input = JOptionPane.showInputDialog(this, "Ingrese el valor (puntos):");
            if (input == null) return 0;
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido");
            return 0;
        }
    }
}