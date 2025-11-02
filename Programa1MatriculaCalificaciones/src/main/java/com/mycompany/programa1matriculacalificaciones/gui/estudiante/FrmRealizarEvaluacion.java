package com.mycompany.programa1matriculacalificaciones.gui.estudiante;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import com.mycompany.programa1matriculacalificaciones.modelo.*;
import com.mycompany.programa1matriculacalificaciones.modelo.pregunta.*;
import com.mycompany.programa1matriculacalificaciones.servicio.ProfesorService;

public class FrmRealizarEvaluacion extends JFrame {

    private JComboBox<Evaluacion> cmbEvaluacion;
    private JPanel panelPreguntas;
    private JButton btnEnviar, btnRegresar;

    private ProfesorService profesorService = new ProfesorService();

    // Guarda respuestas temporales
    private Map<String, Object> respuestas = new HashMap<>();
    
    // Guarda mapeos de índices aleatorizados para preguntas de selección
    private Map<String, List<Integer>> mapeosAleatorios = new HashMap<>();
    
    // Guarda versiones aleatorizadas de Pareo
    private Map<String, PareoAleatorio> pareosAleatorios = new HashMap<>();
    
    // Guarda posiciones aleatorias de palabras en SopaDeLetras
    private Map<String, SopaAleatoria> sopasAleatorias = new HashMap<>();

    public FrmRealizarEvaluacion() {
        setTitle("Realizar Evaluación");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Realizar Evaluación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(230, 126, 34));

        cmbEvaluacion = new JComboBox<>(profesorService.listarEvaluaciones().toArray(new Evaluacion[0]));
        cmbEvaluacion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbEvaluacion.setBackground(Color.WHITE);
        cmbEvaluacion.setBorder(BorderFactory.createTitledBorder("Seleccione Evaluación"));
        cmbEvaluacion.addActionListener(e -> mostrarPreguntas());

        panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new BoxLayout(panelPreguntas, BoxLayout.Y_AXIS));
        panelPreguntas.setBackground(Color.WHITE);
        panelPreguntas.setBorder(BorderFactory.createTitledBorder("Preguntas"));

        JScrollPane scroll = new JScrollPane(panelPreguntas);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        btnEnviar = crearBoton("Enviar Evaluación", new Color(46, 204, 113));
        btnEnviar.addActionListener(e -> calcularNota());

        btnRegresar = crearBoton("Regresar", new Color(127, 140, 141));
        btnRegresar.addActionListener(e -> {
            dispose();
            new MenuEstudianteFrame().setVisible(true);
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(panel.getBackground());
        botones.add(btnEnviar);
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
        btn.setPreferredSize(new Dimension(170, 40));
        return btn;
    }

    private void mostrarPreguntas() {
        panelPreguntas.removeAll();
        respuestas.clear();
        mapeosAleatorios.clear();
        pareosAleatorios.clear();
        sopasAleatorias.clear();

        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        if (eval == null || eval.getPreguntas().isEmpty()) {
            panelPreguntas.add(new JLabel("Esta evaluación no tiene preguntas."));
            panelPreguntas.revalidate();
            panelPreguntas.repaint();
            return;
        }

        List<Pregunta> preguntas = new ArrayList<>(eval.getPreguntas());
        if (eval.isOrdenAleatorio()) {
            Collections.shuffle(preguntas);
        }

        for (Pregunta p : preguntas) {
            JPanel pPanel = crearPanelPregunta(p);
            panelPreguntas.add(pPanel);
            panelPreguntas.add(Box.createVerticalStrut(10));
        }

        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    private JPanel crearPanelPregunta(Pregunta p) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lbl = new JLabel(p.getEnunciado() + "  (" + p.getValor() + " pts)");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));

        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        boolean esAleatorio = eval != null && eval.isOrdenAleatorio();
        
        switch (p.getTipo().toLowerCase()) {
            case "selección única" -> {
                SeleccionUnica su = (SeleccionUnica) p;
                ButtonGroup group = new ButtonGroup();
                List<String> opciones = new ArrayList<>(su.getOpciones());
                List<Integer> mapeoIndices = new ArrayList<>();
                for (int i = 0; i < opciones.size(); i++) {
                    mapeoIndices.add(i);
                }
                
                // Aleatorizar opciones si está habilitado
                if (esAleatorio) {
                    Collections.shuffle(mapeoIndices);
                    List<String> opcionesAleatorias = new ArrayList<>();
                    for (int idx : mapeoIndices) {
                        opcionesAleatorias.add(opciones.get(idx));
                    }
                    opciones = opcionesAleatorias;
                    mapeosAleatorios.put(p.getId(), mapeoIndices);
                }
                
                for (int i = 0; i < opciones.size(); i++) {
                    final int indexOriginal = esAleatorio ? mapeoIndices.get(i) : i;
                    String opcion = opciones.get(i);
                    JRadioButton rb = new JRadioButton(opcion);
                    rb.setBackground(Color.WHITE);
                    rb.addActionListener(e -> respuestas.put(p.getId(), indexOriginal));
                    group.add(rb);
                    panel.add(rb);
                }
            }
            case "selección múltiple" -> {
                SeleccionMultiple sm = (SeleccionMultiple) p;
                List<String> opciones = new ArrayList<>(sm.getOpciones());
                List<Integer> mapeoIndices = new ArrayList<>();
                for (int i = 0; i < opciones.size(); i++) {
                    mapeoIndices.add(i);
                }
                
                // Aleatorizar opciones si está habilitado
                if (esAleatorio) {
                    Collections.shuffle(mapeoIndices);
                    List<String> opcionesAleatorias = new ArrayList<>();
                    for (int idx : mapeoIndices) {
                        opcionesAleatorias.add(opciones.get(idx));
                    }
                    opciones = opcionesAleatorias;
                    mapeosAleatorios.put(p.getId(), mapeoIndices);
                }
                
                for (int i = 0; i < opciones.size(); i++) {
                    final int indexOriginal = esAleatorio ? mapeoIndices.get(i) : i;
                    String opcion = opciones.get(i);
                    JCheckBox cb = new JCheckBox(opcion);
                    cb.setBackground(Color.WHITE);
                    cb.addActionListener(e -> {
                        @SuppressWarnings("unchecked")
                        Set<Integer> seleccion = (Set<Integer>) respuestas.getOrDefault(p.getId(), new HashSet<Integer>());
                        if (cb.isSelected()) seleccion.add(indexOriginal);
                        else seleccion.remove(indexOriginal);
                        respuestas.put(p.getId(), seleccion);
                    });
                    panel.add(cb);
                }
            }
            case "falso/verdadero" -> {
                JRadioButton rbVerdadero = new JRadioButton("Verdadero");
                JRadioButton rbFalso = new JRadioButton("Falso");
                ButtonGroup grupo = new ButtonGroup();
                grupo.add(rbVerdadero);
                grupo.add(rbFalso);
                rbVerdadero.setBackground(Color.WHITE);
                rbFalso.setBackground(Color.WHITE);

                rbVerdadero.addActionListener(e -> respuestas.put(p.getId(), true));
                rbFalso.addActionListener(e -> respuestas.put(p.getId(), false));

                panel.add(rbVerdadero);
                panel.add(rbFalso);
            }
            case "pareo" -> {
                Pareo pr = (Pareo) p;
                Map<String, String> paresOriginales = pr.getPares();
                List<String> columnaIzq = new ArrayList<>(paresOriginales.keySet());
                List<String> columnaDer = new ArrayList<>(paresOriginales.values());
                
                // Aleatorizar columnas si está habilitado
                if (esAleatorio) {
                    Collections.shuffle(columnaIzq);
                    Collections.shuffle(columnaDer);
                    pareosAleatorios.put(p.getId(), new PareoAleatorio(columnaIzq, columnaDer, paresOriginales));
                }
                
                panel.add(new JLabel("Empareje los siguientes elementos:"));
                JPanel panelPareo = new JPanel(new GridLayout(0, 2, 10, 5));
                panelPareo.setBackground(Color.WHITE);
                
                List<String> izq = esAleatorio ? columnaIzq : new ArrayList<>(paresOriginales.keySet());
                List<String> der = esAleatorio ? columnaDer : new ArrayList<>(paresOriginales.values());
                
                for (int i = 0; i < izq.size(); i++) {
                    panelPareo.add(new JLabel((i + 1) + ". " + izq.get(i)));
                    if (i < der.size()) {
                        panelPareo.add(new JLabel(der.get(i)));
                    }
                }
                panel.add(panelPareo);
            }
            case "sopa de letras" -> {
                SopaDeLetras sl = (SopaDeLetras) p;
                List<String> palabras = sl.getPalabras();
                int tamano = sl.getTamaño();
                
                // Generar sopa de letras con posiciones aleatorias
                char[][] grid = generarSopaLetras(palabras, tamano, esAleatorio);
                SopaAleatoria sopa = new SopaAleatoria(grid, palabras, tamano);
                sopasAleatorias.put(p.getId(), sopa);
                
                panel.add(new JLabel("Palabras a buscar: " + palabras));
                panel.add(Box.createVerticalStrut(5));
                
                // Mostrar grid
                JPanel gridPanel = new JPanel(new GridLayout(tamano, tamano, 2, 2));
                gridPanel.setBackground(Color.WHITE);
                for (int i = 0; i < tamano; i++) {
                    for (int j = 0; j < tamano; j++) {
                        JLabel celda = new JLabel(String.valueOf(grid[i][j]), SwingConstants.CENTER);
                        celda.setFont(new Font("Courier New", Font.BOLD, 14));
                        celda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                        celda.setPreferredSize(new Dimension(25, 25));
                        gridPanel.add(celda);
                    }
                }
                panel.add(gridPanel);
                // Para sopa de letras, almacenar las palabras encontradas (por ahora se asume que todas están)
                respuestas.put(p.getId(), palabras);
            }
        }

        return panel;
    }
    
    private char[][] generarSopaLetras(List<String> palabras, int tamano, boolean aleatorio) {
        char[][] grid = new char[tamano][tamano];
        Random rand = new Random();
        
        // Llenar con letras aleatorias
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                grid[i][j] = (char) ('A' + rand.nextInt(26));
            }
        }
        
        // Colocar palabras si está habilitado el aleatorio
        if (aleatorio) {
            for (String palabra : palabras) {
                palabra = palabra.toUpperCase();
                boolean colocada = false;
                int intentos = 0;
                while (!colocada && intentos < 100) {
                    int fila = rand.nextInt(tamano);
                    int col = rand.nextInt(tamano);
                    // Intentar horizontal izquierda a derecha
                    if (col + palabra.length() <= tamano) {
                        boolean puedeColocar = true;
                        for (int k = 0; k < palabra.length(); k++) {
                            if (grid[fila][col + k] != 0 && grid[fila][col + k] != palabra.charAt(k)) {
                                puedeColocar = false;
                                break;
                            }
                        }
                        if (puedeColocar) {
                            for (int k = 0; k < palabra.length(); k++) {
                                grid[fila][col + k] = palabra.charAt(k);
                            }
                            colocada = true;
                        }
                    }
                    intentos++;
                }
            }
        } else {
            // Sin aleatorio: colocar palabras en orden
            int fila = 0;
            for (String palabra : palabras) {
                palabra = palabra.toUpperCase();
                if (fila < tamano && palabra.length() <= tamano) {
                    for (int j = 0; j < palabra.length() && j < tamano; j++) {
                        grid[fila][j] = palabra.charAt(j);
                    }
                    fila++;
                }
            }
        }
        
        // Llenar espacios vacíos con letras aleatorias
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = (char) ('A' + rand.nextInt(26));
                }
            }
        }
        
        return grid;
    }

    private void calcularNota() {
        Evaluacion eval = (Evaluacion) cmbEvaluacion.getSelectedItem();
        if (eval == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una evaluación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (eval.getPreguntas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Esta evaluación no tiene preguntas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String estudiante = JOptionPane.showInputDialog(this, "Ingrese su nombre completo:");
        if (estudiante == null || estudiante.trim().isEmpty()) {
            return;
        }

        double total = 0, obtenido = 0;
        int preguntasSinResponder = 0;

        for (Pregunta p : eval.getPreguntas()) {
            total += p.getValor();
            Object r = respuestas.get(p.getId());
            
            if (r == null) {
                preguntasSinResponder++;
                continue;
            }

            boolean esCorrecta = false;
            switch (p.getTipo().toLowerCase()) {
                case "selección única" -> {
                    SeleccionUnica su = (SeleccionUnica) p;
                    esCorrecta = ((int) r == su.getIndiceCorrecto());
                }
                case "selección múltiple" -> {
                    SeleccionMultiple sm = (SeleccionMultiple) p;
                    @SuppressWarnings("unchecked")
                    Set<Integer> respuesta = (Set<Integer>) r;
                    esCorrecta = sm.getIndicesCorrectos().equals(respuesta);
                }
                case "falso/verdadero" -> {
                    FalsoVerdadero fv = (FalsoVerdadero) p;
                    esCorrecta = ((boolean) r == fv.isRespuestaCorrecta());
                }
                case "pareo" -> {
                    Pareo pr = (Pareo) p;
                    @SuppressWarnings("unchecked")
                    Map<String, String> respuestaEstudiante = (Map<String, String>) r;
                    Map<String, String> paresCorrectos = pr.getPares();
                    // Verificar que todos los pares sean correctos
                    esCorrecta = paresCorrectos.equals(respuestaEstudiante);
                }
                case "sopa de letras" -> {
                    SopaDeLetras sl = (SopaDeLetras) p;
                    @SuppressWarnings("unchecked")
                    List<String> palabrasEncontradas = (List<String>) r;
                    List<String> palabrasCorrectas = sl.getPalabras();
                    // Verificar que todas las palabras estén presentes
                    esCorrecta = palabrasCorrectas.size() == palabrasEncontradas.size() &&
                                 palabrasCorrectas.containsAll(palabrasEncontradas);
                }
            }
            
            if (esCorrecta) {
                obtenido += p.getValor();
            }
        }

        if (total == 0) {
            JOptionPane.showMessageDialog(this, "Error: La evaluación no tiene valor asignado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double nota = (obtenido / total) * 100.0;
        
        if (preguntasSinResponder > 0) {
            int opcion = JOptionPane.showConfirmDialog(this,
                "Tiene " + preguntasSinResponder + " pregunta(s) sin responder.\n" +
                "¿Desea enviar la evaluación de todas formas?",
                "Preguntas sin responder",
                JOptionPane.YES_NO_OPTION);
            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // --- Guardar resultado ---
        ResultadoEvaluacion resultado = new ResultadoEvaluacion(estudiante, eval, obtenido, total);
        new com.mycompany.programa1matriculacalificaciones.servicio.ResultadoService().registrarResultado(resultado);

        int preguntasCorrectas = 0;
        double valorPorPregunta = total / eval.getPreguntas().size();
        if (valorPorPregunta > 0) {
            preguntasCorrectas = (int) Math.round(obtenido / valorPorPregunta);
        }

        String mensaje = String.format(
            "Evaluación finalizada.\n\n" +
            "Puntaje obtenido: %.2f de %.2f puntos\n" +
            "Nota final: %.1f%%\n" +
            "Preguntas correctas: %d de %d\n\n" +
            "Resultado guardado exitosamente.",
            obtenido, total, nota, preguntasCorrectas, eval.getPreguntas().size()
        );

        JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpiar y refrescar
        respuestas.clear();
        mostrarPreguntas();
    }
    
    // Clases auxiliares para manejar aleatorización
    private static class PareoAleatorio {
        List<String> columnaIzq;
        List<String> columnaDer;
        Map<String, String> paresOriginales;
        
        PareoAleatorio(List<String> izq, List<String> der, Map<String, String> originales) {
            this.columnaIzq = izq;
            this.columnaDer = der;
            this.paresOriginales = originales;
        }
    }
    
    private static class SopaAleatoria {
        char[][] grid;
        List<String> palabras;
        int tamano;
        
        SopaAleatoria(char[][] grid, List<String> palabras, int tamano) {
            this.grid = grid;
            this.palabras = palabras;
            this.tamano = tamano;
        }
    }

}
