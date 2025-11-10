# Manual de usuario - Programa1 (Matrícula y Calificaciones)

Este manual es una guía paso a paso para que un usuario pruebe cada función de la aplicación. En cada sección verás los pasos exactos a seguir, resultados esperados.

Nota: las rutas y botones mencionados corresponden a la interfaz incluida en este proyecto. Si tu instalación cambia nombres de menús o formularios, adáptalo mínimamente.

## Requisitos previos

- Java 21 instalado
- Carpeta de datos: `datos/matriculaycalificaciones/` (la aplicación crea archivos si no existen)

## Ejecutar

- Ejecutar la aplicación:

Clase principal: `Programa1MatriculaCalificaciones.java`

- Deberías ver la ventana de Login.

[imagen: login.png]

---

## 1. Prueba básica: login y menú principal

Objetivo: verificar autenticación y acceso a menús según rol.

Pasos:

1. En la pantalla de Login introduce:
    - Usuario: `admin`
    - Contraseña: `1234`
2. Pulsa "Ingresar".

Resultado esperado:

- Acceso al menú de Administrador.

[imagen: menu_admin.png]

---

## 2. Módulo Administrador — CRUD Estudiantes

Objetivo: crear, editar y eliminar un estudiante.

Pasos (crear estudiante):

1. Desde el menú Administrador elige "Estudiantes" o "Registrar Estudiante".
2. Rellena el formulario con datos de ejemplo:
    - Nombre: Carlos
    - Apellido1: Pérez
    - Apellido2: Gómez
    - Identificación: E001
    - Teléfono: 3001234567
    - Correo: carlos.perez @example.com
    - Dirección: Calle Falsa 123
    - Fecha de nacimiento: 01/01/2000
    - Género: Masculino
    - Carrera: Ingeniería de Sistemas
    - Nivel educativo: Pregrado
    - Institución de procedencia: Instituto XYZ
3. Guarda.

Resultado esperado:

- Verás una confirmación y el estudiante aparecerá en la lista de estudiantes.

[imagen: admin_crear_estudiante.png]

Pasos (editar estudiante):

1. En la lista, selecciona `E001` y pulsa "Editar".
2. Cambia el teléfono a `3000000000` y guarda.

Resultado esperado:

- La ficha del estudiante muestra el teléfono actualizado.

[imagen: admin_editar_estudiante.png]

Pasos (eliminar estudiante):

1. Selecciona `E001` y pulsa "Eliminar".
2. Confirma la eliminación.

Resultado esperado:

- El estudiante ya no aparecerá en la lista.

[imagen: admin_eliminar_estudiante.png]

---

## 3. Módulo Administrador — CRUD Profesores

Pasos (crear profesor):

1. Menú Administrador → Profesores → Nuevo
2. Rellena:
    - Nombre: Ana
    - Apellido1: Martínez
    - Identificación: P001
    - Correo: ana.martinez @example.com
3. Guardar.

Resultado esperado:

- Profesor aparece en la lista.

[imagen: admin_crear_profesor.png]

---

## 4. CRUD Cursos y Grupos

Objetivo: crear un curso y un grupo, asociarlo a un profesor.

Pasos:

1. Administrador → Cursos → Nuevo
    - Código: C001
    - Nombre: Programación Orientada a Objetos
    - Créditos: 4
2. Guardar.

[imagen: admin_crear_curso.png]

3. Administrador → Grupos → Nuevo
    - Código de grupo: G01
    - Asociar al curso C001
    - Asociar al profesor P001
4. Guardar.

Resultado esperado:

- Grupo G01 creado y listado con su curso y profesor asignado.

[imagen: admin_crear_grupo.png]

---

## 5. Matrícula de estudiantes

Objetivo: matricular un estudiante en un grupo.

Pasos:

1. Administrador → Matrícula → Nueva
2. Selecciona Estudiante `E001` y Grupo `G01`.
3. Guardar.

Resultado esperado:

- El estudiante aparece matriculado en el grupo.

[imagen: admin_matricular.png]

---

## 6. Crear evaluaciones (Profesor)

Objetivo: crear una evaluación con diferentes tipos de preguntas.

Pasos:

1. Inicia sesión como profesor (`P001`).
2. Menú Profesor → Evaluaciones → Nuevo
3. Rellena título y descripción, añade preguntas de ejemplo:
    - Selección única: Pregunta A, opciones [a,b,c], respuesta correcta a
    - Selección múltiple: Pregunta B, opciones [a,b,c,d], respuestas correctas a,c
    - Verdadero/Falso: Pregunta C
    - Pareo: pregunta con parejas. (Añade pares en la UI)
    - Sopa de letras: añade palabras y el grilla será generada
4. Guarda la evaluación.

Resultado esperado:

- Evaluación creada y disponible para asociar a un grupo.

[imagen: prof_crear_evaluacion.png]

---

## 7. Asociar evaluación a grupo

Pasos:

1. Profesor → Evaluaciones → Selecciona la evaluación → Asociar a grupo
2. Selecciona G01 y confirma.

Resultado esperado:

- La evaluación aparece asignada al grupo G01.

[imagen: prof_asociar_evaluacion.png]

---

## 8. Realizar una evaluación (Estudiante)

Objetivo: que el estudiante realice la evaluación y se guarde el resultado.

Pasos:

1. Inicia sesión con `E001`.
2. Menú Estudiante → Evaluaciones asignadas
3. Selecciona la evaluación asignada y pulsa "Realizar".
4. Responde las preguntas (la UI maneja los diferentes tipos) y envía.

Resultado esperado:

- Se muestra un resumen con puntaje y porcentaje.
- El resultado queda guardado en `resultados.dat`.

[imagen: estudiante_realizar_evaluacion.png]

---

## 9. Ver desempeño personal

Pasos:

1. Inicia sesión como `E001`.
2. Menú Estudiante → Desempeño Personal.
3. Pulsa el botón "Mi información" para ver la ficha personal en pantalla separada.

Resultado esperado:

- La tabla muestra solo los resultados de `E001`.
- Al pulsar "Mi información" se abre una ventana con todos los datos personales.

[imagen: estudiante_desempeno.png]
[imagen: estudiante_perfil.png]

---

## 10. Reportes en PDF

Objetivo: generar reportes por evaluación y listas de estudiantes matriculados.

Pasos:

1. Administrador → Reportes → seleccionar tipo de reporte (por evaluación o matrícula)
2. Generar → El sistema crea un PDF en su dispositivo con el nombre `reporte_*.pdf`.

Resultado esperado:

- Archivo PDF generado y guardado en el dispositivo del usuario.

[imagen: admin_reporte_pdf.png]

---

## 11. Cambio de contraseña y notificaciones por correo

Pasos:

1. En cualquier perfil (Administrador/Profesor/Estudiante) abrir la opción "Cambiar contraseña".
2. Introducir contraseña actual y nueva. Guardar.

Resultado esperado:

- La contraseña se actualiza.

[imagen: cambiar_contrasena.png]

---

## 12. Solución de problemas frecuentes

- La aplicación no inicia: revisar que Java 21 esté instalado y que el jar se haya generado correctamente.
- Resultados no aparecen: comprueba que `resultados.dat` exista en `datos/matriculaycalificaciones` y que el usuario esté logueado con el mismo identificador usado al registrar resultados.

---

Fin del Manual de Usuario.
