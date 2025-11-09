# Manual de usuario - Programa1 (Matrícula y Calificaciones)

Este manual describe cómo instalar, configurar y usar la aplicación.

## Requisitos previos

- Java 21 instalado
- Archivo de datos: `datos/matriculaycalificaciones/` (la aplicación crea archivos si no existen)
- (Opcional) Para envío SMTP real: crear `datos/matriculaycalificaciones/mail.properties` o configurar variables de entorno `SMTP_*`.

## Instalación y ejecución

1. Abrir PowerShell en la carpeta del proyecto (donde está `pom.xml`).
2. Construir el proyecto:

```powershell
mvn clean package
```

3. Ejecutar el jar con dependencias:

```powershell
java -jar .\target\Programa1MatriculaCalificaciones-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Uso - Administrador

- Iniciar sesión con credenciales de administrador.
- Menú Administrador: crear/editar/eliminar estudiantes, profesores y cursos.
- Asociar grupos a cursos y profesores desde la interfaz de Curso/Grupo.
- Generar reportes en PDF desde la vista de reportes.

## Uso - Estudiante

- Iniciar sesión con credenciales de estudiante.
- Consultar información personal.
- Matricularse en cursos (si está habilitado).
- Ver evaluaciones asignadas y realizar evaluaciones.

## Uso - Profesor

- Iniciar sesión con credenciales de profesor.
- Crear y gestionar evaluaciones con tipos de preguntas (selección única, múltiple, V/F, pareo, sopa de letras).
- Asociar evaluaciones a grupos y previsualizarlas.
- Ver reporte detallado de evaluaciones y resultados.

## Cambio de contraseña y notificación

- Desde la opción de cambiar contraseña, después de un cambio exitoso la aplicación intentará enviar una notificación por correo al email asociado con el usuario. Si no existe correo para ese usuario, se intentará enviar al destinatario por defecto configurado en `mail.properties` o en `NOTIFICATION_DEFAULT_TO`.

## Solución de problemas comunes

- Si la aplicación no envía correos: verifique `datos/matriculaycalificaciones/mail.properties` o las variables SMTP_*. Revise logs en la consola.
- Si la aplicación falla al abrir el cliente de correo, asegúrese de que el sistema soporte Desktop.mail() y que exista un cliente de correo por defecto.
- Para problemas de persistencia, verifique permisos de escritura en `datos/matriculaycalificaciones`.

---

Fin del Manual de Usuario.
