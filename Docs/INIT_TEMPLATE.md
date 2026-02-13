# 🚀 Inicialización del Template - Guía de Uso

## 📋 Resumen

Este proyecto es un **template reutilizable** para desarrollo de aplicaciones Android con Jetpack Compose, MVI, Koin, y muchas otras tecnologías preconfiguradas. 

El sistema de inicialización automatiza completamente el proceso de transformar este template en tu nuevo proyecto, sin necesidad de renombrar manualmente carpetas, packages o configuraciones.

---

## ✨ Características del Sistema de Inicialización

- ✅ **Sin fricción**: Configura todo automáticamente en segundos
- ✅ **Interactivo**: Te guía paso a paso solicitando la información necesaria
- ✅ **Seguro**: Opción de crear backup antes de proceder
- ✅ **Inteligente**: Valida nombres de packages y detecta si ya fue inicializado
- ✅ **Completo**: Actualiza todos los archivos necesarios (Gradle, XML, Kotlin)
- ✅ **Organizado**: Reorganiza la estructura de packages automáticamente

---

## 🎯 ¿Qué se Configura Automáticamente?

El script de inicialización actualiza los siguientes elementos:

### 1. **Archivos de Configuración de Gradle**
- `settings.gradle.kts` → Nombre del proyecto
- `app/build.gradle.kts` → Namespace y Application ID

### 2. **Archivos Android**
- `AndroidManifest.xml` → Nombre del tema
- `res/values/strings.xml` → Nombre visible de la app
- `res/values/themes.xml` → Nombre del tema (todas las variantes)

### 3. **Código Kotlin**
- Reorganiza toda la estructura de packages
- Actualiza declaraciones de `package`
- Actualiza todos los `import`
- Actualiza referencias hardcodeadas (database, etc.)

---

## 🚀 Proceso de Inicialización

### Paso 1: Clonar o Descargar el Template

```bash
git clone <tu-repo-template> MiNuevoProyecto
cd MiNuevoProyecto
```

### Paso 2: Ejecutar el Script de Inicialización

#### Opción A: Usando Bash (Linux/Mac)

```bash
chmod +x Scripts/init-project.sh
./Scripts/init-project.sh
```

#### Opción B: Usando Kotlin directamente

```bash
kotlin Scripts/init-project.main.kts
```

#### Opción C: Desde Android Studio/IntelliJ IDEA

1. Abre el archivo `Scripts/init-project.main.kts`
2. Click derecho → "Run init-project.main.kts"

### Paso 3: Proporcionar la Información del Proyecto

El script te solicitará:

```
Nombre del proyecto (ej: MyAwesomeApp): MiApp
Package base (ej: com.mycompany.myapp): com.miempresa.miapp
Application ID [com.miempresa.miapp]: 
Nombre visible de la app [MiApp]: Mi Aplicación
```

**Ejemplo de valores:**
- **Nombre del proyecto**: `TaskManager`
- **Package base**: `com.acme.taskmanager`
- **Application ID**: `com.acme.taskmanager` (o diferente si lo requieres)
- **Nombre visible**: `Task Manager Pro`

### Paso 4: Confirmar y Ejecutar

El script mostrará un resumen de la configuración:

```
Confirma la configuración:
  Nombre del proyecto: TaskManager
  Package base:        com.acme.taskmanager
  Application ID:      com.acme.taskmanager
  Nombre visible:      Task Manager Pro

¿Es correcta esta configuración? (S/n):
```

### Paso 5: Sincronizar y Construir

Después de la inicialización exitosa:

```bash
# Sincronizar Gradle
./gradlew clean

# Construir el proyecto
./gradlew build

# O desde Android Studio: File → Sync Project with Gradle Files
```

---

## 📝 Información Solicitada

### 1. **Nombre del Proyecto**
- **Qué es**: El nombre técnico del proyecto (usado en `settings.gradle.kts`)
- **Formato**: PascalCase, sin espacios (ej: `MyAwesomeApp`)
- **Uso**: Identificación interna del proyecto en Gradle

### 2. **Package Base**
- **Qué es**: El package raíz de tu aplicación
- **Formato**: Lowercase con puntos (ej: `com.company.app`)
- **Validación**: Debe seguir la convención de Java/Kotlin
- **Uso**: Namespace de la aplicación y estructura de directorios

### 3. **Application ID**
- **Qué es**: Identificador único en Google Play Store
- **Formato**: Igual que el package (puede ser diferente)
- **Default**: Si no lo especificas, usa el mismo que el package base
- **Uso**: Identificación única en dispositivos Android

### 4. **Nombre Visible de la App**
- **Qué es**: El nombre que verán los usuarios (launcher, configuración)
- **Formato**: Texto libre, puede incluir espacios
- **Default**: Si no lo especificas, usa el nombre del proyecto
- **Uso**: `app_name` en `strings.xml`

---

## 🔒 Características de Seguridad

### Prevención de Reinicialización Accidental

El script detecta si el proyecto ya fue inicializado mediante un archivo marcador (`.template-initialized`). Si intenta reinicializar, te preguntará primero.

### Backup Automático

Antes de proceder, el script ofrece crear un backup completo del proyecto:

```
¿Deseas crear un backup antes de continuar? (S/n):
```

El backup se crea en el directorio padre con timestamp:
```
Tamplete_backup_2026-02-12T14-30-45/
```

### Validación de Datos

- ✅ Verifica que el package sea válido (formato Java/Kotlin)
- ✅ Verifica que no haya campos vacíos
- ✅ Confirma la configuración antes de proceder

---

## 🎨 Ejemplo Completo

```bash
$ ./Scripts/init-project.sh

╔═══════════════════════════════════════════════════════════════╗
║      INICIALIZADOR DE TEMPLATE - PROYECTO ANDROID            ║
╚═══════════════════════════════════════════════════════════════╝

Configuración del nuevo proyecto:

Nombre del proyecto (ej: MyAwesomeApp): TaskManager
Package base (ej: com.mycompany.myapp): com.acme.taskmanager
Application ID [com.acme.taskmanager]: 
Nombre visible de la app [TaskManager]: Task Manager Pro

Confirma la configuración:
  Nombre del proyecto: TaskManager
  Package base:        com.acme.taskmanager
  Application ID:      com.acme.taskmanager
  Nombre visible:      Task Manager Pro

¿Es correcta esta configuración? (S/n): s

¿Deseas crear un backup antes de continuar? (S/n): s

📦 Creando backup...
✅ Backup creado en: /home/user/AndroidStudioProjects/Tamplete_backup_2026-02-12T14-30-45

🚀 Iniciando transformación del template...

📝 Actualizando settings.gradle.kts...
📝 Actualizando app/build.gradle.kts...
📝 Actualizando AndroidManifest.xml...
📝 Actualizando themes.xml...
📝 Actualizando strings.xml...
📦 Reorganizando packages de Kotlin...
✅ Todos los archivos actualizados

✅ ¡Template inicializado exitosamente!

Próximos pasos:
  1. Sincroniza el proyecto con Gradle
  2. Limpia y reconstruye: ./gradlew clean build
  3. ¡Comienza a desarrollar!
```

---

## 🛠️ Requisitos

### Para ejecutar el script necesitas:

1. **Kotlin instalado** (cualquiera de estas opciones):
   - Kotlin Compiler (`kotlinc`)
   - Android Studio / IntelliJ IDEA
   - JDK + Kotlin instalado manualmente

2. **Bash** (para Linux/Mac) o ejecutar directamente el `.kts` en Windows

### Instalación de Kotlin (si no lo tienes)

#### Linux (Ubuntu/Debian)
```bash
sudo snap install kotlin --classic
```

#### macOS
```bash
brew install kotlin
```

#### Manual
Descarga desde: https://kotlinlang.org/docs/command-line.html

---

## 📁 Archivos Generados

Después de la inicialización, se crea:

```
.template-initialized       # Marcador de inicialización
```

**Contenido del archivo:**
```
Template Version: 1.0.0
Initialized: 2026-02-12T14:30:45.123456
Project Name: TaskManager
Package: com.acme.taskmanager
```

---

## 🔧 Solución de Problemas

### Error: "kotlinc no está disponible"

**Solución**: Instala Kotlin siguiendo la sección de requisitos, o ejecuta el script desde Android Studio.

### Error: "Package inválido"

**Causa**: El package no sigue el formato correcto.

**Solución**: Usa el formato `com.company.app` (todo en minúsculas, separado por puntos).

### Error: "Este proyecto ya ha sido inicializado"

**Causa**: Ya ejecutaste el script anteriormente.

**Solución**: Responde 's' si deseas reinicializar (perderás cambios personalizados) o clona nuevamente el template.

### El proyecto no compila después de la inicialización

**Solución**:
1. Sincroniza con Gradle: `./gradlew clean`
2. Invalida cachés en Android Studio: `File → Invalidate Caches / Restart`
3. Verifica que no haya caracteres especiales en los nombres

---

## 🎯 Mejores Prácticas

### Nombres de Package

✅ **Correcto:**
- `com.mycompany.myapp`
- `io.github.username.project`
- `dev.myapp.android`

❌ **Incorrecto:**
- `MyApp` (falta dominio)
- `com.My-Company.app` (guiones no permitidos)
- `com.mycompany` (muy corto, debe tener al menos 3 niveles)

### Nombres de Proyecto

✅ **Correcto:**
- `TaskManager`
- `MyAwesomeApp`
- `WeatherForecast`

❌ **Incorrecto:**
- `My App` (espacios)
- `task-manager` (guiones)
- `my_app` (underscores)

### Application ID

- Usa el mismo que el package base a menos que tengas una razón específica
- Debe ser único en Google Play Store
- No puede cambiarse después de publicar

---

## 📚 Referencias

- [Documentación de Arquitectura MVI](../Docs/ARQUITECTURA_MVI.md)
- [Configuración de Firebase](../Docs/FIREBASE_SETUP.md)
- [Configuración de ProGuard](../Docs/PROGUARD.md)
- [Sistema de Temas](../Docs/THEME_SYSTEM.md)
- [Dependencias del Proyecto](../Docs/DEPENDENCIAS.md)

---

## 🤝 Contribuir

Si encuentras problemas o tienes sugerencias para mejorar el sistema de inicialización, por favor:

1. Reporta el issue
2. Propón mejoras
3. Contribuye con código

---

## 📄 Licencia

Este template y su sistema de inicialización están bajo la misma licencia del proyecto principal.

---

**¡Feliz desarrollo! 🚀**

