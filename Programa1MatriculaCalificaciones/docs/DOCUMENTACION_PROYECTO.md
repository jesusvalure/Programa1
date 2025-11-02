# DOCUMENTACIÓN DEL PROYECTO
## Programa 1 - Matrícula y Calificaciones

---

## I. TEMAS INVESTIGADOS

### 1. Programación Orientada a Objetos en Java

La implementación del sistema utiliza conceptos fundamentales de POO como encapsulación, herencia, polimorfismo y abstracción. Se utilizan clases abstractas para el modelo de preguntas y herencia para especializar los diferentes tipos de preguntas.

**Referencias:**
- Oracle Corporation. (2024). *The Java™ Tutorials - Object-Oriented Programming Concepts*. Oracle Documentation. https://docs.oracle.com/javase/tutorial/java/concepts/

### 2. Serialización de Objetos en Java

El sistema utiliza serialización Java para persistir los datos en archivos binarios. Se implementa mediante `ObjectOutputStream` y `ObjectInputStream` para guardar y cargar listas de objetos.

**Referencias:**
- Oracle Corporation. (2024). *Java Object Serialization Specification*. Oracle Documentation. https://docs.oracle.com/javase/8/docs/platform/serialization/spec/serialTOC.html

### 3. Interfaces Gráficas con Java Swing

La aplicación utiliza la biblioteca Swing para crear una interfaz gráfica de usuario completa con ventanas, formularios, tablas y componentes interactivos.

**Referencias:**
- Oracle Corporation. (2024). *Creating a GUI with Swing*. Oracle Documentation. https://docs.oracle.com/javase/tutorial/uiswing/

### 4. Generación de Documentos PDF

Se utiliza Apache PDFBox para generar reportes en formato PDF, permitiendo la exportación de información sobre evaluaciones y resultados.

**Referencias:**
- Apache Software Foundation. (2024). *Apache PDFBox - A Java PDF Library*. Apache PDFBox Documentation. https://pdfbox.apache.org/
- Apache Software Foundation. (2024). *PDFBox 2.0 API Documentation*. https://pdfbox.apache.org/docs/2.0.30/

### 5. Algoritmos de Aleatorización

Se implementan algoritmos para aleatorizar el orden de opciones, columnas en preguntas de pareo y posiciones de palabras en sopas de letras, utilizando `Collections.shuffle()` y generación de números aleatorios.

**Referencias:**
- Oracle Corporation. (2024). *Class Collections*. Oracle Java Documentation. https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html
- Oracle Corporation. (2024). *Class Random*. Oracle Java Documentation. https://docs.oracle.com/javase/8/docs/api/java/util/Random.html

### 6. Manejo de Archivos y Persistencia de Datos

Implementación de un servicio genérico para el manejo de archivos que permite guardar y cargar listas de objetos serializados, proporcionando persistencia de datos entre sesiones.

**Referencias:**
- Oracle Corporation. (2024). *Basic I/O - File I/O*. Oracle Java Documentation. https://docs.oracle.com/javase/tutorial/essential/io/fileio.html

### 7. Patrones de Diseño - Servicios y Modelo-Vista

El proyecto utiliza una arquitectura basada en servicios que separa la lógica de negocio de la presentación, facilitando el mantenimiento y la escalabilidad.

**Referencias:**
- Gamma, E., Helm, R., Johnson, R., & Vlissides, J. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley Professional.

---

## II. ASPECTOS DE INTELIGENCIA ARTIFICIAL

| Aspecto Investigado | Descripción | Herramienta/Modelo Utilizado | Referencia |
|---------------------|-------------|------------------------------|------------|
| Generación de código asistido | Uso de herramientas de IA para asistencia en la implementación y resolución de problemas | GitHub Copilot / ChatGPT | Tabla completada manualmente durante el desarrollo |
| Análisis de código | Revisión automática de código para detectar errores y mejorar la calidad | Cursor IDE / IntelliSense | Tabla completada manualmente durante el desarrollo |
| Optimización de algoritmos | Sugerencias para mejorar algoritmos de aleatorización y estructuras de datos | Asistente de IA integrado | Tabla completada manualmente durante el desarrollo |

---

## III. SOLUCIÓN

### Arquitectura del Sistema

El sistema está diseñado utilizando una arquitectura de tres capas:
- **Capa de Presentación**: Interfaces gráficas construidas con Swing (`gui` package)
- **Capa de Negocio**: Servicios que implementan la lógica de negocio (`servicio` package)
- **Capa de Datos**: Modelos de dominio y persistencia en archivos (`modelo` package)

### Lista de Archivos de Datos Utilizados

| Nombre del Archivo | Descripción | Ubicación |
|-------------------|-------------|-----------|
| `cursos.dat` | Almacena información de los cursos disponibles en el sistema. Contiene datos serializados de objetos `Curso` incluyendo código, nombre, descripción y otros atributos relacionados. | `datos/matriculaycalificaciones/cursos.dat` |
| `estudiantes.dat` | Contiene la información de todos los estudiantes registrados en el sistema. Almacena objetos `Estudiante` serializados con datos personales, identificación, matrículas y estado académico. | `datos/matriculaycalificaciones/estudiantes.dat` |
| `evaluaciones.dat` | Guarda todas las evaluaciones creadas por los profesores. Incluye objetos `Evaluacion` con su título, tipo, configuración de orden aleatorio y la lista completa de preguntas asociadas. | `datos/matriculaycalificaciones/evaluaciones.dat` |
| `grupos.dat` | Almacena información de los grupos de estudiantes. Contiene objetos `Grupo` serializados con código, horario, relación con cursos y profesores asignados. | `datos/matriculaycalificaciones/grupos.dat` |
| `resultados.dat` | Guarda los resultados de las evaluaciones realizadas por los estudiantes. Contiene objetos `ResultadoEvaluacion` con información del estudiante, evaluación, puntajes obtenidos y fecha de realización. | `datos/matriculaycalificaciones/resultados.dat` |

### Funcionalidades Principales

1. **Gestión de Evaluaciones (CRUD)**: Los profesores pueden crear, leer, actualizar y eliminar evaluaciones con diferentes tipos de preguntas.

2. **Orden Aleatorio**: Implementación de orden aleatorio que se aplica según el tipo de pregunta:
   - **Opciones de respuesta**: Se aleatorizan para preguntas de selección única y múltiple
   - **Columnas de Pareo**: Se aleatoriza el orden de los elementos dentro de cada columna
   - **Sopa de Letras**: Las palabras se colocan aleatoriamente en posiciones dentro de la grilla

3. **Persistencia de Datos**: Todos los datos se guardan automáticamente en archivos binarios utilizando serialización Java.

4. **Reportes PDF**: Generación de reportes en formato PDF para visualización y exportación de información.

---

## IV. ESTRUCTURA DEL PROYECTO

```
Programa1MatriculaCalificaciones/
├── src/main/java/com/mycompany/programa1matriculacalificaciones/
│   ├── app/                          # Punto de entrada de la aplicación
│   ├── gui/                          # Interfaces gráficas
│   │   ├── admin/                    # Ventanas para administradores
│   │   ├── profesor/                 # Ventanas para profesores
│   │   └── estudiante/               # Ventanas para estudiantes
│   ├── modelo/                       # Modelos de dominio
│   │   └── pregunta/                 # Tipos de preguntas
│   ├── servicio/                      # Lógica de negocio
│   └── util/                         # Utilidades
├── datos/matriculaycalificaciones/   # Archivos de datos persistentes
└── docs/                             # Documentación del proyecto
```

---

*Documentación generada para el Programa 1 - Sistema de Matrícula y Calificaciones*
*Fecha de última actualización: 2025*

