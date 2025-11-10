# Portada

Programa: Programa1 - Matrícula y Calificaciones

Autores: Juan Vicente Naranjo, Jesus Valverde Ureña

Fecha: 08/11/2025

Versión: 1.0

Resumen: Documentación completa del proyecto de matrícula y calificaciones desarrollado en Java (Swing, Maven). Contiene especificación de requerimientos, temas investigados, diagrama UML en PlantUML, organización de paquetes, conclusiones, y una lista de revisión con porcentaje de avance.

---

## Contenido

1. Portada
2. Contenido
3. Especificación de requerimientos
4. Temas investigados
5. Diagrama UML (ver `docs/uml/classes.puml`)
6. Organización del proyecto (paquetes)
7. Conclusiones
8. Lista de revisión con % de avance
9. Manual de usuario (archivo: `docs/ManualUsuario.md`)

---

## Especificación de requerimientos

Requerimientos funcionales (principales):

- RF1: Gestión de usuarios (Administradores, Profesores, Estudiantes)
- RF2: CRUD de Estudiantes
- RF3: CRUD de Profesores
- RF4: CRUD de Cursos
- RF5: Gestión de Grupos y asociación a cursos y profesores
- RF6: Matrícula de estudiantes en grupos
- RF7: Gestión de evaluaciones (creación, tipos de preguntas, asociación a grupos)
- RF8: Realización de evaluaciones por estudiantes y registro de resultados
- RF9: Generación de reportes en PDF (por evaluación, por estudiante)
- RF10: Autenticación y encriptación de contraseñas
- RF11: Cambio de contraseña con notificación por correo

Requerimientos no funcionales:

- RNF1: Persistencia simple basada en archivos (.dat) en `datos/matriculaycalificaciones`
- RNF2: Interfaz gráfica con Swing (forms preconstruidos)
- RNF3: Configuración de correo mediante `mail.properties` o variables de entorno SMTP_*
- RNF4: Compatibilidad con Java 21 y empaquetado por Maven

---

## Temas investigados

Breve resumen de lo investigado e implementado:

- JavaDoc: Uso de comentarios Javadoc en las clases de servicio y modelo para documentar API interna.
- Encriptación: Implementación de `Encriptador` usando SHA-256 para almacenar contraseñas en forma segura.
- Generación de PDF: Uso de bibliotecas (reportes generados en la carpeta `target`) y diseño de plantillas para reportes de evaluaciones.
- Envío de correos: Integración de Jakarta Mail (SMTP) con fallback a `mailto:` del cliente de escritorio. Configuración via `datos/matriculaycalificaciones/mail.properties` o variables de entorno como `SMTP_HOST`, `SMTP_USER`, `SMTP_PASSWORD`.
- Serialización y persistencia: `ArchivoService` para guardar y cargar colecciones serializadas de objetos (estudiantes, cursos, evaluaciones).
- Patrones de diseño: Servicios por responsabilidad (AuthService, UsuarioService, CursoService, etc.), utilitarios (PathConfig, Validator) y separación entre GUI y lógica de negocio.

---

## Diagrama UML de clases (nivel bajo)

Se incluye un diagrama PlantUML en `docs/uml/classes.puml` con las entidades principales: `Persona`, `Usuario`, `Estudiante`, `Profesor`, `Curso`, `Grupo`, `Matricula`, `Evaluacion`, `EvaluacionAsignada`, `ResultadoEvaluacion`. 

Archivo: `docs/uml/classes.puml`

---

## Organización del proyecto (paquetes)

Estructura principal de paquetes en `src/main/java`:

- `com.mycompany.programa1matriculacalificaciones.app` : clase de arranque
- `com.mycompany.programa1matriculacalificaciones.gui` : formularios Swing (login, menús, CRUDs, vistas para administrador/estudiante/profesor).
- `com.mycompany.programa1matriculacalificaciones.modelo` : clases de dominio (Persona, Estudiante, Profesor, Curso, Grupo, Matricula, Evaluacion, ResultadoEvaluacion, etc.).
- `com.mycompany.programa1matriculacalificaciones.servicio` : lógica de negocio y servicios (AuthService, UsuarioService, CursoService, MailService, ArchivoService, ResultadoService, etc.).
- `com.mycompany.programa1matriculacalificaciones.util` : utilitarios (Encriptador, PathConfig, SesionActual, Validator).

Recursos: `src/main/resources` y directorio de datos `datos/matriculaycalificaciones` para persistencia y archivos de configuración.

---

## Conclusiones

- El proyecto proporciona una plataforma funcional para gestionar matrícula y evaluaciones con interfaz gráfica.
- La persistencia por archivos facilita pruebas y despliegue simple, aunque para producción se recomienda migrar a una base de datos.
- Se integraron mecanismos de seguridad básicos: encriptación de contraseñas y control de sesiones.

---

## Lista de revisión con % de avance

Utilice la siguiente tabla para evaluar el avance del proyecto. La columna "Avance" muestra una sugerencia basada en estado conocido del repositorio; ajústela si tiene evidencia distinta.

LISTA DE REVISIÓN DEL PROYECTO

Concepto | Puntos originales | Avance (%) | Puntos obtenidos
---|---:|---:|---:
ADMINISTRADOR||||
Registro de estudiantes (CRUD) | 5 | 100 | 5
Registro de profesores (CRUD) | 5 | 100 | 5
Registro de cursos (CRUD) | 2 | 100 | 2
Asociar grupos a los cursos | 1 | 100 | 1
Asociar grupos a los profesores | 1 | 100 | 1
Reporte estudiantes matriculados | 3 | 100 | 3
Reporte estadística de matrícula | 3 | 100 | 3

Uso de algoritmo para encriptación de contraseñas | 3 | 100 | 3
Autenticación de usuarios | 3 | 100 | 3
Cambio de contraseñas | 3 | 100 | 3
Envío de correos | 3 | 0 | 0
Reportes en PDF | 3 | 100 | 3

ESTUDIANTES||||
Consultar información general | 1 | 100 | 1
Matricular curso | 1 | 100 | 1
Evaluaciones asignadas | 1 | 100 | 1
Realizar evaluación | 12 | 100 | 12
Temporizador | 5 | 100 | 5
Desempeño personal | 5 | 80 | 4.0

PROFESORES||||
Consultar información general | 1 | 100 | 1
CRUD de evaluaciones: Información general | 2 | 100 | 2
Selección única | 3 | 100 | 3
Selección múltiple | 3 | 100 | 3
Falso/Verdadero | 3 | 100 | 3
Pareo | 3 | 100 | 3
Sopa de letras | 5 | 100 | 3
Previsualización de evaluaciones | 10 | 100 | 10
Reporte detallado de evaluación | 4 | 80 | 3.2
Asociar/Desasociar evaluaciones a los grupos | 2 | 80 | 1.6
Evaluaciones asignadas | 2 | 85 | 1.7
Evaluaciones realizadas | 2 | 85 | 1.7
TOTAL | 100 | - | 95.2

---

## Partes desarrolladas adicionalmente

---

## Manual de usuario (ver `docs/ManualUsuario.md`)

Incluye instrucciones de instalación, uso básico de la aplicación para administradores, estudiantes y profesores, y resolución de problemas comunes.

---

Fin del documento.
