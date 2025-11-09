package com.mycompany.programa1matriculacalificaciones.app;

import com.mycompany.programa1matriculacalificaciones.servicio.*;
import com.mycompany.programa1matriculacalificaciones.modelo.*;
import com.mycompany.programa1matriculacalificaciones.modelo.pregunta.*;
import com.mycompany.programa1matriculacalificaciones.util.Validator;

import java.util.*;
import java.time.LocalDateTime;

public class Simulator {
    public static void main(String[] args) {
        System.out.println("Iniciando simulación automática...");

        AdministradorService admin = new AdministradorService();
        ProfesorCRUDService profesorCrud = new ProfesorCRUDService();
        CursoService cursoService = new CursoService();
        GrupoService grupoService = new GrupoService();
        MatriculaService matriculaService = new MatriculaService();
        ProfesorService profesorService = new ProfesorService();
        EvaluacionAsignadaService asignadaService = new EvaluacionAsignadaService();
        ResultadoService resultadoService = new ResultadoService();

        // 1) Crear cursos
        Curso c1 = new Curso("MAT101", "Matemáticas I", 4);
        Curso c2 = new Curso("PROG1", "Programación I", 3);
    if (!cursoService.agregar(c1)) System.out.println("Advertencia: curso ya existente: " + c1.getCodigo());
    if (!cursoService.agregar(c2)) System.out.println("Advertencia: curso ya existente: " + c2.getCodigo());
        System.out.println("Cursos creados.");

        // 2) Crear profesores con datos completos y validarlos
        AuthService auth = new AuthService();

        Profesor p1 = new Profesor("Ana", "Gómez", "Gomez", "P001", "+50688880001", "ana.gomez@example.com", "Calle 1", "1980-05-10", "Femenino", "Matemáticas", "PhD", 10);
        Profesor p2 = new Profesor("Luis", "Ramírez", "Ramírez", "P002", "+50688880002", "luis.ramirez@example.com", "Avenida 2", "1975-03-20", "Masculino", "Programación", "MSc", 12);

        // Validar datos y agregar
        if (!Validator.isIdValid(p1.getIdentificacion()) || !Validator.isEmailValid(p1.getCorreo()) || !Validator.isPhoneValid(p1.getTelefono())) {
            System.out.println("Profesor p1 tiene datos inválidos y no se agregará.");
        } else {
            if (!profesorCrud.agregar(p1)) System.out.println("Advertencia: profesor ya existente: " + p1.getIdentificacion());
            else {
                try { auth.crearUsuario(p1.getIdentificacion(), "1234", "Profesor"); } catch (Exception ex) { System.out.println("No se pudo crear usuario profesor: " + ex.getMessage()); }
            }
        }

        if (!Validator.isIdValid(p2.getIdentificacion()) || !Validator.isEmailValid(p2.getCorreo()) || !Validator.isPhoneValid(p2.getTelefono())) {
            System.out.println("Profesor p2 tiene datos inválidos y no se agregará.");
        } else {
            if (!profesorCrud.agregar(p2)) System.out.println("Advertencia: profesor ya existente: " + p2.getIdentificacion());
            else {
                try { auth.crearUsuario(p2.getIdentificacion(), "1234", "Profesor"); } catch (Exception ex) { System.out.println("No se pudo crear usuario profesor: " + ex.getMessage()); }
            }
        }

        System.out.println("Profesores procesados.");

        // 3) Crear estudiantes con datos completos y validarlos
        Estudiante s1 = new Estudiante("Carlos", "Pérez", "Lopez", "E001", "+50688881111", "carlos.perez@example.com", "Casa 1", "2000-01-15", "Masculino", "Ingeniería", "Universitario", "Colegio A");
        Estudiante s2 = new Estudiante("María", "Santos", "González", "E002", "+50688882222", "maria.santos@example.com", "Casa 2", "2001-02-20", "Femenino", "Ingeniería", "Universitario", "Colegio B");
        Estudiante s3 = new Estudiante("Jorge", "Molina", "Torres", "E003", "+50688883333", "jorge.molina@example.com", "Casa 3", "1999-07-10", "Masculino", "Matemáticas", "Universitario", "Colegio C");

        List<Estudiante> estudiantes = Arrays.asList(s1, s2, s3);
        for (Estudiante st : estudiantes) {
            if (!Validator.isIdValid(st.getIdentificacion()) || !Validator.isEmailValid(st.getCorreo()) || !Validator.isPhoneValid(st.getTelefono())) {
                System.out.println("Estudiante " + st.getNombre() + " tiene datos inválidos y no se agregará.");
                continue;
            }
            if (!admin.agregarEstudiante(st)) {
                System.out.println("Advertencia: estudiante ya existente: " + st.getIdentificacion());
            } else {
                try { auth.crearUsuario(st.getIdentificacion(), "1234", "Estudiante"); } catch (Exception ex) { System.out.println("No se pudo crear usuario estudiante: " + ex.getMessage()); }
            }
        }
        System.out.println("Estudiantes procesados.");

        // 4) Crear grupo y asignar profesor y estudiantes
    Grupo g1 = new Grupo("G01", c1, p1);
    // Asignar los primeros dos estudiantes al grupo
    g1.agregarEstudiante(s1);
    g1.agregarEstudiante(s2);
    if (!grupoService.agregar(g1)) System.out.println("Advertencia: grupo ya existente: " + g1.getCodigo());
    // crear matrículas (evitar duplicados)
    boolean a1 = matriculaService.agregar(new Matricula(s1, g1));
    if (!a1) System.out.println("Advertencia: matrícula duplicada para " + s1.getNombre());
    boolean a2 = matriculaService.agregar(new Matricula(s2, g1));
    if (!a2) System.out.println("Advertencia: matrícula duplicada para " + s2.getNombre());
        System.out.println("Grupo y matrículas creadas.");

        // 5) Crear una evaluación con 3 preguntas
        List<String> opciones1 = Arrays.asList("2", "4", "6");
        SeleccionUnica q1 = new SeleccionUnica("¿Cuánto es 2+2?", 2.0, opciones1, 1);

        List<String> opciones2 = Arrays.asList("Java", "Python", "C++");
        Set<Integer> indicesCorrectos = new HashSet<>(); indicesCorrectos.add(0); indicesCorrectos.add(2);
        SeleccionMultiple q2 = new SeleccionMultiple("Seleccione dos lenguajes compilados:", 3.0, opciones2, indicesCorrectos);

    FalsoVerdadero q3 = new FalsoVerdadero("El Sol es una estrella.", 1.0, true);

    // PAREO
    Map<String, String> pares = new LinkedHashMap<>();
    pares.put("Costa Rica", "San José");
    pares.put("España", "Madrid");
    pares.put("Francia", "París");
    Pareo q4 = new Pareo("Empareje país con su capital:", 4.0, pares);

    // SOPA DE LETRAS
    List<String> palabras = Arrays.asList("JAVA", "PDF", "MATRICULA");
    SopaDeLetras q5 = new SopaDeLetras("Busque las palabras en la sopa:", 5.0, palabras, 10);

    Evaluacion eval = new Evaluacion("Parcial 1", "Examen", true);
    eval.setTiempoMinutos(5);
    eval.agregarPregunta(q1);
    eval.agregarPregunta(q2);
    eval.agregarPregunta(q3);
    eval.agregarPregunta(q4);
    eval.agregarPregunta(q5);

        if (!profesorService.agregarEvaluacion(eval)) {
            System.out.println("Advertencia: evaluación ya existente (ID): " + eval.getId());
        }
        System.out.println("Evaluación creada con ID: " + eval.getId());

        // 6) Asignar evaluación al grupo
        EvaluacionAsignada ea = new EvaluacionAsignada(eval, g1, LocalDateTime.now());
        asignadaService.agregar(ea);
        System.out.println("Evaluación asignada al grupo " + g1.getCodigo());

        // 7) Simular que los estudiantes realizan la evaluación
        // Respuestas: e1 responde todo correcto, e2 responde mal la selección múltiple
    double total = q1.getValor() + q2.getValor() + q3.getValor() + q4.getValor() + q5.getValor();

    // Estudiante 1 - respuestas correctas en todas
    double obtenido1 = q1.getValor() + q2.getValor() + q3.getValor() + q4.getValor() + q5.getValor();
    ResultadoEvaluacion r1 = new ResultadoEvaluacion(s1.getNombre() + " " + s1.getApellido1(), eval, obtenido1, total);
        resultadoService.registrarResultado(r1);
        ea.registrarResultado(r1);

        // Estudiante 2 - falla en selección múltiple (obtiene 0 en q2)
    // y además falla en pareo (0.0) y encuentra solo 2 de 3 palabras en la sopa
    double obtenido2 = q1.getValor() + 0 + q3.getValor() + 0 + (q5.getValor() * 2.0 / 3.0);
    ResultadoEvaluacion r2 = new ResultadoEvaluacion(s2.getNombre() + " " + s2.getApellido1(), eval, obtenido2, total);
        resultadoService.registrarResultado(r2);
        ea.registrarResultado(r2);

        System.out.println("Simulación de alumnos realizada. Resultados registrados:");
        for (ResultadoEvaluacion r : resultadoService.listarResultados()) {
            System.out.println(r);
        }

        // 8) Generar reporte de notas en PDF (resumen de resultados de la evaluación)
        StringBuilder reporte = new StringBuilder();
        reporte.append("REPORTE DE NOTAS - ").append(eval.getTitulo()).append("\n\n");
        for (ResultadoEvaluacion r : ea.getResultados()) {
            reporte.append(String.format("%s - %.1f%% (%.2f/%.2f) - %s\n", r.getEstudiante(), r.getNotaPorcentaje(), r.getPuntajeObtenido(), r.getPuntajeTotal(), r.getFechaFormateada()));
        }

        java.io.File out = new java.io.File("target/reporte_evaluacion_" + eval.getId() + ".pdf");
        // Asegurar que el directorio destino existe
        java.io.File parent = out.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                System.err.println("No se pudo crear el directorio de salida: " + parent.getAbsolutePath());
            }
        }
        boolean ok = generarPDF(reporte.toString(), out);
        System.out.println(ok ? "Reporte PDF generado: " + out.getAbsolutePath() : "Error al generar PDF");

        System.out.println("Simulación completada.");
    }

    private static boolean generarPDF(String texto, java.io.File destino) {
        try (org.apache.pdfbox.pdmodel.PDDocument doc = new org.apache.pdfbox.pdmodel.PDDocument()) {
            String[] lineas = texto.split("\\n");
            org.apache.pdfbox.pdmodel.font.PDType1Font font = org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
            float fontSize = 12;
            float leading = 1.2f * fontSize;

            org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
            doc.addPage(page);
            org.apache.pdfbox.pdmodel.PDPageContentStream contents = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float x = margin;
            float y = yStart;

            contents.beginText();
            contents.setFont(font, fontSize);
            contents.newLineAtOffset(x, y);

            for (String linea : lineas) {
                if (y - leading < margin) {
                    contents.endText();
                    contents.close();
                    page = new org.apache.pdfbox.pdmodel.PDPage();
                    doc.addPage(page);
                    contents = new org.apache.pdfbox.pdmodel.PDPageContentStream(doc, page);
                    y = page.getMediaBox().getHeight() - margin;
                    contents.beginText();
                    contents.setFont(font, fontSize);
                    contents.newLineAtOffset(x, y);
                }
                contents.showText(linea);
                contents.newLineAtOffset(0, -leading);
                y -= leading;
            }

            contents.endText();
            contents.close();
            // Asegurar que el archivo destino es accesible
            java.io.File destParent = destino.getParentFile();
            if (destParent != null && !destParent.exists()) {
                destParent.mkdirs();
            }
            doc.save(destino);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
