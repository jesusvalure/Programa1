package com.mycompany.programa1matriculacalificaciones.gui.estudiante;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Timer;

import com.mycompany.programa1matriculacalificaciones.modelo.*;
import com.mycompany.programa1matriculacalificaciones.modelo.pregunta.*;
import com.mycompany.programa1matriculacalificaciones.servicio.ProfesorService;

public class FrmRealizarEvaluacion extends JFrame {

    private JComboBox<Evaluacion> cmbEvaluacion;
    private JPanel panelPreguntas;
    private JButton btnEnviar, btnRegresar, btnLimpiarSeleccion;

    private ProfesorService profesorService = new ProfesorService();

    // Guarda respuestas temporales
    private Map<String, Object> respuestas = new HashMap<>();
    
    // Guarda mapeos de índices aleatorizados para preguntas de selección
    private Map<String, List<Integer>> mapeosAleatorios = new HashMap<>();
    
    // Guarda versiones aleatorizadas de Pareo
    private Map<String, PareoAleatorio> pareosAleatorios = new HashMap<>();
    
    // Guarda posiciones aleatorias de palabras en SopaDeLetras
    private Map<String, SopaAleatoria> sopasAleatorias = new HashMap<>();

    // Lista para almacenar los grids activos de sopa de letras
    private List<JPanel> gridsSopaActivos = new ArrayList<>();

    // Temporizador
    private javax.swing.Timer temporizador;
    private int segundosRestantes = 0;
    private JLabel lblTimer;
    private boolean envioForzadoPorTimer = false;

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

    lblTimer = new JLabel("");
    lblTimer.setFont(new Font("Segoe UI", Font.BOLD, 14));
    lblTimer.setForeground(new Color(192, 57, 43));
    lblTimer.setHorizontalAlignment(SwingConstants.CENTER);

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
        });

        // Botón para limpiar selección
        btnLimpiarSeleccion = crearBoton("Limpiar Selección", new Color(241, 196, 15));
        btnLimpiarSeleccion.addActionListener(e -> limpiarSeleccionSopa());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        botones.setBackground(panel.getBackground());
        botones.add(btnEnviar);
        botones.add(btnLimpiarSeleccion);
        botones.add(btnRegresar);

    JPanel norte = new JPanel(new BorderLayout());
    norte.setBackground(panel.getBackground());
    norte.add(lblTitulo, BorderLayout.NORTH);
    norte.add(cmbEvaluacion, BorderLayout.CENTER);
    norte.add(lblTimer, BorderLayout.SOUTH);
    panel.add(norte, BorderLayout.NORTH);
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
        // Reiniciar temporizador si estaba corriendo
        detenerTemporizador();
        panelPreguntas.removeAll();
        respuestas.clear();
        mapeosAleatorios.clear();
        pareosAleatorios.clear();
        sopasAleatorias.clear();
        gridsSopaActivos.clear(); // Limpiar la lista de grids activos

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

        // Si la evaluación tiene tiempo límite, iniciar temporizador
        if (eval != null && eval.getTiempoMinutos() > 0) {
            segundosRestantes = eval.getTiempoMinutos() * 60;
            actualizarEtiquetaTemporizador();
            temporizador = new javax.swing.Timer(1000, e -> {
                segundosRestantes--;
                actualizarEtiquetaTemporizador();
                if (segundosRestantes <= 0) {
                    // Evitar reentradas múltiples
                    envioForzadoPorTimer = true;
                    detenerTemporizador();
                    calcularNota();
                }
            });
            temporizador.start();
        } else {
            lblTimer.setText("");
        }

        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    private void actualizarEtiquetaTemporizador() {
        int minutos = segundosRestantes / 60;
        int segundos = segundosRestantes % 60;
        lblTimer.setText(String.format("Tiempo restante: %02d:%02d", minutos, segundos));
    }

    private void detenerTemporizador() {
        if (temporizador != null) {
            temporizador.stop();
            temporizador = null;
        }
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
                char[][] grid = generarSopaLetras(palabras, tamano, true);
                SopaAleatoria sopa = new SopaAleatoria(grid, palabras, tamano);
                sopasAleatorias.put(p.getId(), sopa);

                // Panel para la sopa de letras interactiva
                JPanel sopaPanel = new JPanel(new BorderLayout());
                sopaPanel.setBackground(Color.WHITE);

                // Grid interactivo
                JPanel gridPanel = new JPanel(new GridLayout(tamano, tamano, 2, 2));
                gridPanel.setBackground(Color.WHITE);
                gridPanel.setBorder(BorderFactory.createTitledBorder("Sopa de Letras - Selecciona las palabras"));

                // Guardar referencia al grid activo
                gridsSopaActivos.add(gridPanel);

                // Matriz para almacenar los botones
                JButton[][] botonesGrid = new JButton[tamano][tamano];

                for (int i = 0; i < tamano; i++) {
                    for (int j = 0; j < tamano; j++) {
                        JButton celda = new JButton(String.valueOf(grid[i][j]));
                        celda.setFont(new Font("Courier New", Font.BOLD, 14));
                        celda.setBackground(Color.WHITE);
                        celda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                        celda.setPreferredSize(new Dimension(30, 30));
                        celda.setOpaque(true);

                        final int fila = i;
                        final int columna = j;
                        botonesGrid[i][j] = celda;

                        celda.addMouseListener(new java.awt.event.MouseAdapter() {
                            private boolean arrastrando = false;

                            public void mousePressed(java.awt.event.MouseEvent evt) {
                                arrastrando = true;
                                // Solo permitir selección si no es verde (ya encontrada)
                                if (celda.getBackground() != Color.GREEN) {
                                    celda.setBackground(Color.YELLOW);
                                }
                            }

                            public void mouseReleased(java.awt.event.MouseEvent evt) {
                                arrastrando = false;
                                // Verificar palabra solo si hay suficientes celdas seleccionadas
                                List<CeldaSeleccionada> seleccionadas = obtenerCeldasSeleccionadas(botonesGrid, tamano);
                                if (seleccionadas.size() >= 2) {
                                    verificarPalabraSeleccionada(botonesGrid, p.getId(), palabras, tamano);
                                }
                            }

                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                if (arrastrando && celda.getBackground() != Color.GREEN) {
                                    celda.setBackground(Color.YELLOW);
                                }
                            }
                        });

                        gridPanel.add(celda);
                    }
                }

                sopaPanel.add(gridPanel, BorderLayout.CENTER);

                // Panel para palabras encontradas (SOLO las encontradas)
                JPanel palabrasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                palabrasPanel.setBackground(Color.WHITE);
                palabrasPanel.setBorder(BorderFactory.createTitledBorder("Palabras encontradas"));
                palabrasPanel.setName("palabrasEncontradas"); // Para identificarlo fácilmente

                // Inicialmente vacío - solo se mostrarán las encontradas
                JLabel lblInicial = new JLabel("Ninguna palabra encontrada aún");
                lblInicial.setForeground(Color.GRAY);
                palabrasPanel.add(lblInicial);

                sopaPanel.add(palabrasPanel, BorderLayout.SOUTH);
                panel.add(sopaPanel);

                // Inicializar lista de palabras encontradas como vacía
                respuestas.put(p.getId(), new ArrayList<String>());
            }
        }

        return panel;
    }
    
    private char[][] generarSopaLetras(List<String> palabras, int tamano, boolean aleatorio) {
        char[][] grid = new char[tamano][tamano];
        Random rand = new Random();

        // Inicializar grid con espacios
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                grid[i][j] = ' ';
            }
        }

        // Colocar palabras si está habilitado el aleatorio
        if (aleatorio) {
            for (String palabra : palabras) {
                palabra = palabra.toUpperCase().replace(" ", "");
                boolean colocada = false;
                int intentos = 0;

                while (!colocada && intentos < 100) {
                    int direccion = rand.nextInt(8); // 8 direcciones posibles
                    int fila = rand.nextInt(tamano);
                    int col = rand.nextInt(tamano);

                    colocada = intentarColocarPalabra(grid, palabra, fila, col, direccion, tamano);
                    intentos++;
                }

                // Si no se pudo colocar después de 100 intentos, mostrar advertencia
                if (!colocada) {
                    System.out.println("Advertencia: No se pudo colocar la palabra: " + palabra);
                }
            }
        } else {
            // Sin aleatorio: colocar palabras en posiciones fijas
            int fila = 0;
            for (String palabra : palabras) {
                palabra = palabra.toUpperCase().replace(" ", "");
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
                if (grid[i][j] == ' ') {
                    grid[i][j] = (char) ('A' + rand.nextInt(26));
                }
            }
        }

        return grid;
    }

    private boolean intentarColocarPalabra(char[][] grid, String palabra, int fila, int col, int direccion, int tamano) {
        // Verificar si la palabra cabe en la dirección especificada
        if (!verificarEspacio(grid, palabra, fila, col, direccion, tamano)) {
            return false;
        }

        // Colocar la palabra
        for (int i = 0; i < palabra.length(); i++) {
            int nuevaFila = fila;
            int nuevaCol = col;

            switch (direccion) {
                case 0: // Horizontal izquierda a derecha
                    nuevaCol = col + i;
                    break;
                case 1: // Horizontal derecha a izquierda
                    nuevaCol = col - i;
                    break;
                case 2: // Vertical arriba a abajo
                    nuevaFila = fila + i;
                    break;
                case 3: // Vertical abajo a arriba
                    nuevaFila = fila - i;
                    break;
                case 4: // Diagonal abajo-derecha
                    nuevaFila = fila + i;
                    nuevaCol = col + i;
                    break;
                case 5: // Diagonal arriba-izquierda
                    nuevaFila = fila - i;
                    nuevaCol = col - i;
                    break;
                case 6: // Diagonal abajo-izquierda
                    nuevaFila = fila + i;
                    nuevaCol = col - i;
                    break;
                case 7: // Diagonal arriba-derecha
                    nuevaFila = fila - i;
                    nuevaCol = col + i;
                    break;
            }

            // Verificar límites
            if (nuevaFila < 0 || nuevaFila >= tamano || nuevaCol < 0 || nuevaCol >= tamano) {
                return false;
            }

            // Verificar si la celda está vacía o tiene la misma letra
            if (grid[nuevaFila][nuevaCol] != ' ' && grid[nuevaFila][nuevaCol] != palabra.charAt(i)) {
                return false;
            }
        }

        // Si pasa todas las verificaciones, colocar la palabra
        for (int i = 0; i < palabra.length(); i++) {
            int nuevaFila = fila;
            int nuevaCol = col;

            switch (direccion) {
                case 0: nuevaCol = col + i; break;
                case 1: nuevaCol = col - i; break;
                case 2: nuevaFila = fila + i; break;
                case 3: nuevaFila = fila - i; break;
                case 4: nuevaFila = fila + i; nuevaCol = col + i; break;
                case 5: nuevaFila = fila - i; nuevaCol = col - i; break;
                case 6: nuevaFila = fila + i; nuevaCol = col - i; break;
                case 7: nuevaFila = fila - i; nuevaCol = col + i; break;
            }

            grid[nuevaFila][nuevaCol] = palabra.charAt(i);
        }

        return true;
    }

    private boolean verificarEspacio(char[][] grid, String palabra, int fila, int col, int direccion, int tamano) {
        for (int i = 0; i < palabra.length(); i++) {
            int nuevaFila = fila;
            int nuevaCol = col;

            switch (direccion) {
                case 0: nuevaCol = col + i; break;
                case 1: nuevaCol = col - i; break;
                case 2: nuevaFila = fila + i; break;
                case 3: nuevaFila = fila - i; break;
                case 4: nuevaFila = fila + i; nuevaCol = col + i; break;
                case 5: nuevaFila = fila - i; nuevaCol = col - i; break;
                case 6: nuevaFila = fila + i; nuevaCol = col - i; break;
                case 7: nuevaFila = fila - i; nuevaCol = col + i; break;
            }

            // Verificar límites
            if (nuevaFila < 0 || nuevaFila >= tamano || nuevaCol < 0 || nuevaCol >= tamano) {
                return false;
            }

            // Verificar si la celda está vacía o tiene la misma letra
            if (grid[nuevaFila][nuevaCol] != ' ' && grid[nuevaFila][nuevaCol] != palabra.charAt(i)) {
                return false;
            }
        }
        return true;
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

            switch (p.getTipo().toLowerCase()) {
                case "selección única" -> {
                    SeleccionUnica su = (SeleccionUnica) p;
                    if ((int) r == su.getIndiceCorrecto()) {
                        obtenido += p.getValor();
                    }
                }
                case "selección múltiple" -> {
                    SeleccionMultiple sm = (SeleccionMultiple) p;
                    @SuppressWarnings("unchecked")
                    Set<Integer> respuesta = (Set<Integer>) r;
                    if (sm.getIndicesCorrectos().equals(respuesta)) {
                        obtenido += p.getValor();
                    }
                }
                case "falso/verdadero" -> {
                    FalsoVerdadero fv = (FalsoVerdadero) p;
                    if ((boolean) r == fv.isRespuestaCorrecta()) {
                        obtenido += p.getValor();
                    }
                }
                case "pareo" -> {
                    Pareo pr = (Pareo) p;
                    @SuppressWarnings("unchecked")
                    Map<String, String> respuestaEstudiante = (Map<String, String>) r;
                    Map<String, String> paresCorrectos = pr.getPares();
                    if (paresCorrectos.equals(respuestaEstudiante)) {
                        obtenido += p.getValor();
                    }
                }
                case "sopa de letras" -> {
                    SopaDeLetras sl = (SopaDeLetras) p;
                    @SuppressWarnings("unchecked")
                    List<String> palabrasEncontradas = (List<String>) r;
                    List<String> palabrasCorrectas = sl.getPalabras();

                    // Calcular porcentaje de palabras encontradas
                    int encontradas = 0;
                    for (String palabra : palabrasCorrectas) {
                        if (palabrasEncontradas.contains(palabra)) {
                            encontradas++;
                        }
                    }

                    // Asignar puntaje proporcional
                    double porcentaje = (double) encontradas / palabrasCorrectas.size();
                    obtenido += p.getValor() * porcentaje;
                }
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
        envioForzadoPorTimer = false;
        mostrarPreguntas();
    }

    @Override
    public void dispose() {
        detenerTemporizador();
        super.dispose();
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
    
    // MÉTODOS NUEVOS PARA SOPA DE LETRAS MEJORADA
    
    // Método para obtener celdas seleccionadas
    private List<CeldaSeleccionada> obtenerCeldasSeleccionadas(JButton[][] botonesGrid, int tamano) {
        List<CeldaSeleccionada> seleccionadas = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (botonesGrid[i][j].getBackground() == Color.YELLOW) {
                    seleccionadas.add(new CeldaSeleccionada(i, j, botonesGrid[i][j].getText().charAt(0)));
                }
            }
        }
        return seleccionadas;
    }

    // Método para limpiar selección de sopa de letras - SIMPLIFICADO Y FUNCIONAL
    private void limpiarSeleccionSopa() {
        for (JPanel gridPanel : gridsSopaActivos) {
            for (Component celdaComp : gridPanel.getComponents()) {
                if (celdaComp instanceof JButton) {
                    JButton celda = (JButton) celdaComp;
                    if (celda.getBackground() == Color.YELLOW) {
                        celda.setBackground(Color.WHITE);
                    }
                }
            }
        }
    }

    private void verificarPalabraSeleccionada(JButton[][] botonesGrid, String preguntaId, List<String> palabras, int tamano) {
        List<CeldaSeleccionada> seleccionadas = obtenerCeldasSeleccionadas(botonesGrid, tamano);

        if (seleccionadas.size() < 2) return;

        // Ordenar las celdas seleccionadas para determinar la dirección
        Collections.sort(seleccionadas, (a, b) -> {
            if (a.fila != b.fila) return Integer.compare(a.fila, b.fila);
            return Integer.compare(a.columna, b.columna);
        });

        // Verificar si las celdas forman una línea recta
        if (!esLineaRecta(seleccionadas)) {
            // NO hacer nada - la selección se mantiene amarilla hasta que el usuario la limpie manualmente
            return;
        }

        // Construir palabra en ambas direcciones
        String palabraNormal = construirPalabraDesdeSeleccion(seleccionadas);
        Collections.reverse(seleccionadas);
        String palabraReversa = construirPalabraDesdeSeleccion(seleccionadas);
        Collections.reverse(seleccionadas); // Volver al orden original

        // Verificar si alguna de las palabras existe en la lista
        String palabraEncontrada = null;
        for (String palabra : palabras) {
            if (palabra.equalsIgnoreCase(palabraNormal) || palabra.equalsIgnoreCase(palabraReversa)) {
                palabraEncontrada = palabra;
                break;
            }
        }

        if (palabraEncontrada != null) {
            @SuppressWarnings("unchecked")
            List<String> palabrasEncontradas = (List<String>) respuestas.get(preguntaId);
            if (!palabrasEncontradas.contains(palabraEncontrada)) {
                palabrasEncontradas.add(palabraEncontrada);
                respuestas.put(preguntaId, palabrasEncontradas);

                // Marcar como encontrada (verde permanente)
                for (CeldaSeleccionada celda : seleccionadas) {
                    botonesGrid[celda.fila][celda.columna].setBackground(Color.GREEN);
                    botonesGrid[celda.fila][celda.columna].setEnabled(false);
                }

                JOptionPane.showMessageDialog(this, "¡Palabra encontrada: " + palabraEncontrada + "!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
                // Actualizar lista de palabras encontradas
                actualizarListaPalabrasEncontradas(palabrasEncontradas);
            } else {
                JOptionPane.showMessageDialog(this, "Ya encontraste la palabra: " + palabraEncontrada, "Palabra duplicada", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        // Si no es una palabra válida, NO hacer nada - la selección se mantiene amarilla
    }

    // Método auxiliar para verificar si las celdas forman una línea recta
    private boolean esLineaRecta(List<CeldaSeleccionada> seleccionadas) {
        if (seleccionadas.size() <= 1) return true;

        int diffFila = seleccionadas.get(1).fila - seleccionadas.get(0).fila;
        int diffColumna = seleccionadas.get(1).columna - seleccionadas.get(0).columna;

        // Normalizar la dirección (-1, 0, 1)
        diffFila = Integer.compare(diffFila, 0);
        diffColumna = Integer.compare(diffColumna, 0);

        for (int i = 1; i < seleccionadas.size(); i++) {
            int currentDiffFila = seleccionadas.get(i).fila - seleccionadas.get(i-1).fila;
            int currentDiffColumna = seleccionadas.get(i).columna - seleccionadas.get(i-1).columna;

            currentDiffFila = Integer.compare(currentDiffFila, 0);
            currentDiffColumna = Integer.compare(currentDiffColumna, 0);

            if (currentDiffFila != diffFila || currentDiffColumna != diffColumna) {
                return false;
            }
        }

        return true;
    }

    // Método para actualizar la lista visual de palabras encontradas
    private void actualizarListaPalabrasEncontradas(List<String> palabrasEncontradas) {
        // Buscar el panel de palabras en la jerarquía de componentes
        Component[] components = panelPreguntas.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                actualizarPanelPalabras((JPanel) comp, palabrasEncontradas);
            }
        }
    }

    private void actualizarPanelPalabras(JPanel panel, List<String> palabrasEncontradas) {
        Component[] components = panel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel subPanel = (JPanel) comp;
                // Buscar por nombre o por texto del border
                if ("palabrasEncontradas".equals(subPanel.getName()) || 
                    (subPanel.getBorder() != null && 
                     subPanel.getBorder().toString().toLowerCase().contains("palabras encontradas"))) {
                    
                    subPanel.removeAll();
                    
                    if (palabrasEncontradas.isEmpty()) {
                        JLabel lblVacio = new JLabel("Ninguna palabra encontrada aún");
                        lblVacio.setForeground(Color.GRAY);
                        subPanel.add(lblVacio);
                    } else {
                        for (String palabra : palabrasEncontradas) {
                            JLabel lblPalabra = new JLabel(palabra);
                            lblPalabra.setForeground(Color.GREEN);
                            lblPalabra.setFont(new Font("Segoe UI", Font.BOLD, 12));
                            lblPalabra.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                            subPanel.add(lblPalabra);
                        }
                    }
                    
                    subPanel.revalidate();
                    subPanel.repaint();
                    break;
                }
            }
        }
    }

    private String construirPalabraDesdeSeleccion(List<CeldaSeleccionada> seleccionadas) {
        StringBuilder palabra = new StringBuilder();
        for (CeldaSeleccionada celda : seleccionadas) {
            palabra.append(celda.letra);
        }
        return palabra.toString();
    }

    // Clase auxiliar para celdas seleccionadas
    private static class CeldaSeleccionada {
        int fila;
        int columna;
        char letra;

        CeldaSeleccionada(int fila, int columna, char letra) {
            this.fila = fila;
            this.columna = columna;
            this.letra = letra;
        }
    }

}