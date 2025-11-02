# Validación de Persistencia de Datos

## Archivos de Datos Verificados

Todos los archivos de datos se crean y persisten correctamente en la ruta:
```
datos/matriculaycalificaciones/
```

### Archivos de Datos Implementados:

1. **estudiantes.dat** - Almacena lista de estudiantes
   - Servicio: `AdministradorService`
   - Se guarda automáticamente al agregar o eliminar estudiantes

2. **profesores.dat** - Almacena lista de profesores
   - Servicio: `ProfesorCRUDService`
   - Se guarda automáticamente al agregar, editar o eliminar profesores

3. **cursos.dat** - Almacena lista de cursos
   - Servicio: `CursoService`
   - Se guarda automáticamente al agregar o eliminar cursos

4. **grupos.dat** - Almacena lista de grupos
   - Servicio: `GrupoService`
   - Se guarda automáticamente al agregar, actualizar o eliminar grupos

5. **evaluaciones.dat** - Almacena lista de evaluaciones
   - Servicio: `ProfesorService` y `EvaluacionService`
   - Se guarda automáticamente al agregar, actualizar o eliminar evaluaciones

6. **resultados.dat** - Almacena resultados de evaluaciones
   - Servicio: `ResultadoService`
   - Se guarda automáticamente al registrar un resultado

7. **evaluaciones_asignadas.dat** - Almacena asignaciones de evaluaciones a grupos
   - Servicio: `EvaluacionAsignadaService`
   - Se guarda automáticamente al asignar una evaluación

8. **matriculas.dat** - Almacena matrículas de estudiantes
   - Servicio: `MatriculaService`
   - Se guarda automáticamente al matricular un estudiante

## Características de Persistencia Implementadas:

### ✅ Garantías de Persistencia:

1. **Creación Automática de Directorios**: 
   - Si el directorio `datos/matriculaycalificaciones/` no existe, se crea automáticamente

2. **Inicialización Segura**:
   - Todos los servicios verifican si el archivo existe
   - Si no existe o hay error al cargar, se inicializa con una lista vacía
   - Protección contra NullPointerException

3. **Guardado Inmediato**:
   - Cada operación de modificación (agregar, editar, eliminar) guarda inmediatamente en disco
   - Se usa `flush()` para asegurar que los datos se escriban al disco

4. **Manejo de Errores**:
   - Errores de lectura/escritura se capturan y registran
   - El sistema continúa funcionando incluso si hay errores de archivo

5. **Serialización Completa**:
   - Todos los modelos implementan `Serializable`
   - Se incluyen `serialVersionUID` para compatibilidad

## Verificación de Funcionamiento:

### Pruebas de Persistencia:

1. **Crear datos**: Al ejecutar el programa y crear estudiantes, cursos, etc.
   - Los archivos `.dat` deben aparecer en `datos/matriculaycalificaciones/`

2. **Cerrar y reabrir**: Al cerrar el programa y volver a ejecutarlo
   - Los datos deben estar disponibles inmediatamente
   - No se deben perder datos al cerrar

3. **Múltiples sesiones**: Los datos persisten entre diferentes inicios de sesión
   - Los datos no dependen del usuario que ejecuta el programa
   - Se mantienen entre reinicios del sistema

## Notas Técnicas:

- **Formato de almacenamiento**: Serialización binaria de Java (`ObjectOutputStream`/`ObjectInputStream`)
- **Ubicación**: Relativa al directorio de ejecución del programa
- **Durabilidad**: Los datos persisten hasta que se eliminen manualmente o se modifique el código

## Troubleshooting:

Si los datos no persisten:

1. Verificar permisos de escritura en el directorio `datos/`
2. Revisar la consola por mensajes de error
3. Verificar que los modelos implementen `Serializable`
4. Comprobar que los servicios llamen a `guardar()` o `guardarLista()` después de cada modificación

