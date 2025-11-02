# Guía de Primer Inicio - Sistema de Matrícula y Calificaciones

## 📋 Cómo Ingresar por Primera Vez

Cuando ejecutas el programa por primera vez y **no existe ningún usuario**, el sistema crea automáticamente un usuario administrador por defecto.

### 🔑 Credenciales del Administrador por Defecto

**Usuario:** `admin`  
**Contraseña:** `1234`  
**Rol:** Administrador

### 📝 Pasos para Ingresar

1. **Ejecuta el programa**
   - Al iniciar, el sistema detecta que no hay usuarios
   - Se crea automáticamente el archivo `usuarios.dat` con el usuario admin

2. **En la pantalla de inicio de sesión:**
   - **Campo Usuario:** Ingresa `admin`
   - **Campo Contraseña:** Ingresa `1234`
   - Haz clic en **"Ingresar"**

3. **Serás redirigido al Panel del Administrador**
   - Desde aquí podrás:
     - Crear nuevos profesores (y sus usuarios automáticamente)
     - Crear nuevos estudiantes (y sus usuarios automáticamente)
     - Gestionar cursos y grupos
     - Ver reportes

### ⚙️ Crear Más Usuarios

Una vez que ingreses como administrador, puedes crear nuevos usuarios de dos formas:

#### 1. Crear Profesores
1. Ve a **"CRUD Profesores"** en el menú del administrador
2. Completa el formulario:
   - Nombre
   - Apellido
   - Identificación (será el usuario para iniciar sesión)
3. Haz clic en **"Agregar"**
4. El sistema te pedirá una contraseña para el profesor
5. El usuario se crea automáticamente con rol **"Profesor"**

#### 2. Crear Estudiantes
1. Ve a **"CRUD Estudiantes"** en el menú del administrador
2. Completa el formulario:
   - Nombre
   - Apellido
   - Identificación (será el usuario para iniciar sesión)
3. Haz clic en **"Agregar"**
4. El sistema te pedirá una contraseña para el estudiante
5. El usuario se crea automáticamente con rol **"Estudiante"**

### 🔒 Cambiar Contraseña del Administrador

Si deseas cambiar la contraseña del administrador por defecto:

1. Puedes modificar el código en `UsuarioService.java` método `crearUsuarioAdminPorDefecto()`
2. O simplemente eliminar el archivo `datos/matriculaycalificaciones/usuarios.dat` y el sistema lo recreará (pero perderás todos los usuarios creados)

### 📂 Archivos Creados Automáticamente

En el primer inicio, el sistema crea:
- `datos/matriculaycalificaciones/usuarios.dat` - Contiene el usuario admin y todos los usuarios creados posteriormente

### ⚠️ Importante

- **No elimines** el usuario `admin` a menos que hayas creado otro usuario con rol "Administrador"
- Los profesores y estudiantes **deben** tener una identificación (ID) en el sistema para poder iniciar sesión
- La identificación del profesor/estudiante será el **nombre de usuario** para iniciar sesión
- Cada profesor/estudiante debe tener una cuenta de usuario creada antes de poder iniciar sesión

### 🔐 Seguridad

- Todas las contraseñas se almacenan encriptadas en el archivo `usuarios.dat`
- El sistema usa el `Encriptador` para proteger las contraseñas
- La contraseña por defecto `1234` es solo para facilitar el primer inicio - **se recomienda cambiarla**

