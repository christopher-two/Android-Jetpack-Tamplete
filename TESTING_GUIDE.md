# 🧪 Guía de Prueba del Sistema de Inicialización

## Objetivo

Verificar que el sistema de inicialización funcione correctamente antes de usarlo en un proyecto real.

---

## 📋 Pre-requisitos

Antes de probar, asegúrate de tener:

- ✅ Kotlin instalado (`kotlin --version`) o Android Studio
- ✅ Git (para clonar el template)
- ✅ Bash (Linux/Mac) o Git Bash (Windows)

---

## 🔍 Paso 1: Verificar el Template

Antes de inicializar, verifica que el template esté en buen estado:

```bash
cd /ruta/al/template
./Scripts/verify-template.sh
```

**Salida esperada:**
```
✅ VERIFICACIÓN COMPLETADA: TODO OK
El template está listo para ser inicializado.
```

Si hay errores, revisa los archivos indicados.

---

## 🎬 Paso 2: Preparar una Copia de Prueba

**IMPORTANTE**: No pruebes directamente en el template original. Haz una copia:

```bash
# Opción A: Copiar el directorio
cp -r Tamplete Tamplete_TEST
cd Tamplete_TEST

# Opción B: Clonar desde Git
git clone <url> Tamplete_TEST
cd Tamplete_TEST
```

---

## 🚀 Paso 3: Ejecutar el Inicializador

```bash
./Scripts/init-project.sh
```

### Datos de Prueba Sugeridos

```
Nombre del proyecto: TestApp
Package base: com.test.myapp
Application ID: [presiona Enter]
Nombre visible de la app: [presiona Enter]
```

### Confirmaciones

```
¿Es correcta esta configuración? (S/n): S
¿Deseas crear un backup antes de continuar? (S/n): S
```

**Salida esperada:**

```
✅ ¡Template inicializado exitosamente!

Próximos pasos:
  1. Sincroniza el proyecto con Gradle
  2. Limpia y reconstruye: ./gradlew clean build
  3. ¡Comienza a desarrollar!
```

---

## ✅ Paso 4: Verificar los Cambios

### 4.1 Verificar settings.gradle.kts

```bash
grep "rootProject.name" settings.gradle.kts
```

**Esperado:**
```kotlin
rootProject.name = "TestApp"
```

### 4.2 Verificar app/build.gradle.kts

```bash
grep "val nameProject" app/build.gradle.kts
grep "namespace" app/build.gradle.kts | head -1
grep "applicationId" app/build.gradle.kts | head -1
```

**Esperado:**
```kotlin
val nameProject = "testapp"
    namespace = "com.test.myapp"
        applicationId = "com.test.myapp"
```

### 4.3 Verificar strings.xml

```bash
grep "app_name" app/src/main/res/values/strings.xml
```

**Esperado:**
```xml
<string name="app_name">TestApp</string>
```

### 4.4 Verificar AndroidManifest.xml

```bash
grep "Theme\." app/src/main/AndroidManifest.xml
```

**Esperado:**
```xml
android:theme="@style/Theme.TestApp">
android:theme="@style/Theme.TestApp">
```

### 4.5 Verificar themes.xml

```bash
grep "style name" app/src/main/res/values/themes.xml | head -1
```

**Esperado:**
```xml
<style name="Theme.TestApp" parent="android:Theme.Material.Light.NoActionBar">
```

### 4.6 Verificar estructura de packages

```bash
ls -R app/src/main/java/
```

**Esperado:**
```
app/src/main/java/:
com

app/src/main/java/com:
test

app/src/main/java/com/test:
myapp

app/src/main/java/com/test/myapp:
MainActivity.kt  MainApp.kt  core  di  feature  main
...
```

### 4.7 Verificar archivo marcador

```bash
cat .template-initialized
```

**Esperado:**
```
Template Version: 1.0.0
Initialized: 2026-02-13T...
Project Name: TestApp
Package: com.test.myapp
```

### 4.8 Verificar un archivo Kotlin

```bash
head -5 app/src/main/java/com/test/myapp/MainActivity.kt
```

**Esperado:**
```kotlin
package com.test.myapp

import android.os.Bundle
...
```

---

## 🏗️ Paso 5: Construir el Proyecto

```bash
./gradlew clean
./gradlew build
```

**Salida esperada (al final):**
```
BUILD SUCCESSFUL in XXs
```

Si hay errores de compilación, revisa:
1. Los imports en los archivos Kotlin
2. El namespace en build.gradle.kts
3. Los nombres de los themes en XML

---

## 🔄 Paso 6: Probar Reinicialización

Intenta ejecutar el script nuevamente:

```bash
./Scripts/init-project.sh
```

**Salida esperada:**
```
⚠️  Este proyecto ya ha sido inicializado.
¿Deseas reinicializar? Esto SOBRESCRIBIRÁ los cambios (s/N):
```

Prueba ambas opciones:
- Escribir `n` → debe cancelar
- Escribir `s` → debe permitir reinicializar

---

## 📊 Checklist de Verificación

Marca cada item después de verificarlo:

### Archivos Actualizados
- [ ] `settings.gradle.kts` - Nombre del proyecto correcto
- [ ] `app/build.gradle.kts` - Variable nameProject correcta
- [ ] `app/build.gradle.kts` - Namespace correcto
- [ ] `app/build.gradle.kts` - Application ID correcto
- [ ] `app/src/main/AndroidManifest.xml` - Tema correcto
- [ ] `app/src/main/res/values/strings.xml` - Nombre de app correcto
- [ ] `app/src/main/res/values/themes.xml` - Tema correcto
- [ ] `app/src/main/res/values-night/themes.xml` - Tema correcto
- [ ] `app/src/main/res/values-v31/themes.xml` - Tema correcto

### Archivos Kotlin
- [ ] Packages actualizados en `app/src/main/java`
- [ ] Packages actualizados en `app/src/test/java`
- [ ] Packages actualizados en `app/src/androidTest/java`
- [ ] Imports actualizados correctamente
- [ ] Referencias a strings actualizadas
- [ ] Nombre de base de datos actualizado

### Estructura
- [ ] Directorios antiguos eliminados
- [ ] Nuevos directorios creados correctamente
- [ ] No hay directorios vacíos
- [ ] Todos los archivos .kt movidos

### Sistema
- [ ] Archivo `.template-initialized` creado
- [ ] Backup creado (si se solicitó)
- [ ] Prevención de reinicialización funciona
- [ ] Build exitoso sin errores

### Scripts
- [ ] `verify-template.sh` funciona
- [ ] `init-project.sh` funciona
- [ ] `example-usage.sh` funciona
- [ ] Todos los scripts son ejecutables

---

## 🐛 Problemas Comunes y Soluciones

### Error: "kotlin: command not found"

**Solución**: Ejecuta el script desde Android Studio o instala Kotlin:
```bash
# Ubuntu/Debian
sudo snap install kotlin --classic

# macOS
brew install kotlin
```

### Error: "Package inválido"

**Causa**: Formato incorrecto del package.

**Solución**: Usa el formato `com.company.app` (todo minúsculas, separado por puntos).

### Build falla después de inicializar

**Posibles causas:**
1. Gradle cache corrupto → `./gradlew clean --refresh-dependencies`
2. Imports incorrectos → Verifica manualmente los archivos Kotlin
3. Namespace incorrecto → Verifica `app/build.gradle.kts`

**Solución general:**
```bash
# Limpiar todo
./gradlew clean
rm -rf .gradle
rm -rf app/build

# Sincronizar
./gradlew build --refresh-dependencies
```

### Los archivos no se actualizaron

**Causa**: Posible error en el script o permisos.

**Solución:**
1. Verifica que el script sea ejecutable: `ls -la Scripts/init-project.sh`
2. Revisa los logs del script
3. Verifica que tienes permisos de escritura en el directorio

---

## 📸 Capturas de Pantalla Esperadas

### 1. Verificación Exitosa
```
✅ VERIFICACIÓN COMPLETADA: TODO OK
```

### 2. Inicialización Exitosa
```
✅ ¡Template inicializado exitosamente!
```

### 3. Build Exitoso
```
BUILD SUCCESSFUL in XXs
```

---

## 🎯 Criterios de Éxito

El test es exitoso si:

✅ La verificación inicial pasa sin errores
✅ El inicializador completa sin errores
✅ Todos los archivos se actualizan correctamente
✅ La estructura de packages se reorganiza
✅ El proyecto compila sin errores
✅ La prevención de reinicialización funciona
✅ El backup se crea correctamente (si se solicita)

---

## 📝 Reportar Problemas

Si encuentras problemas:

1. **Documenta el error**
   - Comando ejecutado
   - Salida completa del error
   - Versión de Kotlin
   - Sistema operativo

2. **Verifica los logs**
   ```bash
   ./Scripts/init-project.sh 2>&1 | tee init-log.txt
   ```

3. **Revisa los archivos**
   - ¿Qué archivos se actualizaron?
   - ¿Qué archivos NO se actualizaron?
   - ¿Hay archivos corruptos?

4. **Abre un issue** con toda la información

---

## 🔄 Limpiar después de Probar

Para volver al estado original:

```bash
# Opción 1: Si hiciste backup
rm -rf Tamplete_TEST

# Opción 2: Restaurar desde backup
cp -r Tamplete_backup_* Tamplete_RESTORED

# Opción 3: Si usas Git
git reset --hard HEAD
git clean -fd
rm .template-initialized
```

---

## ✅ Conclusión

Si todos los pasos pasaron exitosamente:

🎉 **¡El sistema de inicialización está listo para producción!**

Puedes usarlo con confianza para crear nuevos proyectos a partir del template.

---

**Última actualización**: 2026-02-13

