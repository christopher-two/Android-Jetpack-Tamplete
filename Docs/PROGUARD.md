# 📦 Guía de ProGuard/R8 - Tamplete Project

## 🎯 Resumen

Este proyecto utiliza **R8** (el optimizador de código moderno de Android que reemplaza a ProGuard) para:

- ✅ **Minificar** el código (reducir el tamaño de la APK)
- ✅ **Ofuscar** el código (proteger contra ingeniería inversa)
- ✅ **Optimizar** el código (mejorar el rendimiento)
- ✅ **Eliminar código no utilizado** (reducir el tamaño final)

---

## ⚙️ Configuración Actual

### En `build.gradle.kts`:

```kotlin
buildTypes {
    release {
        isMinifyEnabled = true          // Activar minificación
        isShrinkResources = true         // Eliminar recursos no usados
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

---

## 📋 Librerías Cubiertas

El archivo `proguard-rules.pro` incluye reglas específicas para:

### Core Android & Kotlin
- ✅ Kotlin Standard Library
- ✅ Kotlinx Coroutines
- ✅ Kotlinx Serialization
- ✅ Kotlinx DateTime

### Jetpack
- ✅ Jetpack Compose (UI, Material3, Adaptive)
- ✅ Navigation 3
- ✅ Room Database
- ✅ DataStore
- ✅ WorkManager
- ✅ Lifecycle & ViewModel
- ✅ Splash Screen
- ✅ Biometric

### Inyección de Dependencias
- ✅ Koin (Core, Android, Compose, WorkManager)

### Networking
- ✅ Ktor Client (Core, OkHttp, Serialization, Logging)
- ✅ OkHttp3

### Firebase & Google
- ✅ Firebase Auth
- ✅ Firebase Firestore
- ✅ Firebase Vertex AI
- ✅ Google Play Services (Auth)
- ✅ Credentials API

### UI & Media
- ✅ Coil 3 (Compose, Network)
- ✅ Material Kolor
- ✅ Material Icons Extended
- ✅ Accompanist Permissions
- ✅ Haze (Blur Effects)
- ✅ FileKit
- ✅ QRose (QR Codes)
- ✅ RichText

---

## 🛡️ Reglas del Proyecto

### Arquitectura MVI
```proguard
# Mantener States
-keep class org.override.tamplete.**.presentation.**State { *; }

# Mantener Actions (interfaces y clases)
-keep interface org.override.tamplete.**.presentation.**Action { *; }
-keep class org.override.tamplete.**.presentation.**Action* { *; }

# Mantener ViewModels
-keep class * extends androidx.lifecycle.ViewModel { <init>(...); }
```

### Modelos de Datos
```proguard
# Domain Models
-keep class org.override.tamplete.feature.**.domain.model.** { *; }

# DTOs
-keep class org.override.tamplete.feature.**.data.**.dto.** { *; }

# Entities (Room)
-keep class org.override.tamplete.feature.**.data.**.entity.** { *; }
```

### Repositorios
```proguard
# Interfaces de repositorios
-keep interface org.override.tamplete.**.domain.repository.** { *; }

# Implementaciones
-keep class org.override.tamplete.**.data.**Repository { *; }
-keep class org.override.tamplete.**.data.**RepositoryImpl { *; }
```

### DataStore Serializers
```proguard
# Serializer personalizado para User
-keep class org.override.tamplete.feature.auth.data.local.UserSerializer { *; }
```

---

## 🧪 Testing en Release

### Antes de publicar, SIEMPRE:

1. **Generar APK Release**:
   ```bash
   ./gradlew assembleRelease
   ```

2. **Revisar el Mapping File**:
   ```
   app/build/outputs/mapping/release/mapping.txt
   ```
   Este archivo mapea los nombres ofuscados a los originales.

3. **Instalar y Probar**:
   ```bash
   adb install app/build/outputs/apk/release/app-release.apk
   ```

4. **Verificar que funcionen**:
   - [ ] Login/Logout
   - [ ] Navegación
   - [ ] Base de datos (Room)
   - [ ] Llamadas de red (Ktor)
   - [ ] DataStore
   - [ ] Firebase

---

## 🐛 Debugging de Problemas

### Si algo falla en Release pero funciona en Debug:

1. **Revisar los logs**:
   ```bash
   adb logcat | grep -i "ClassNotFoundException\|NoSuchMethodException\|NoSuchFieldException"
   ```

2. **Agregar regla específica** en `proguard-rules.pro`:
   ```proguard
   # Si falla una clase específica
   -keep class com.example.MiClase { *; }
   
   # Si falla un paquete completo
   -keep class com.example.mipaquete.** { *; }
   ```

3. **Verificar reflexión**:
   Si usas reflexión, agrega:
   ```proguard
   -keepattributes *Annotation*,Signature,InnerClasses
   ```

4. **Desactivar ofuscación temporalmente**:
   ```kotlin
   buildTypes {
       release {
           isMinifyEnabled = true
           isShrinkResources = true
           // Desactivar ofuscación para debug
           proguardFiles(
               getDefaultProguardFile("proguard-android.txt"), // Sin optimize
               "proguard-rules.pro"
           )
       }
   }
   ```

---

## 📊 Beneficios Esperados

Con estas reglas, deberías ver:

- 📉 **Reducción de tamaño**: ~30-50% menos de tamaño de APK
- 🔒 **Mejor seguridad**: Código ofuscado dificulta la ingeniería inversa
- ⚡ **Mejor rendimiento**: Optimizaciones de código
- 🗑️ **Sin código muerto**: Eliminación de código no utilizado

---

## 🔄 Mantenimiento

### Cuando agregues nuevas librerías:

1. Verifica si la librería proporciona sus propias reglas ProGuard en:
   - `AAR` → `proguard.txt`
   - Documentación oficial

2. Si no hay reglas, agrega una sección en `proguard-rules.pro`:
   ```proguard
   # ========================================================================================
   # NUEVA LIBRERÍA
   # ========================================================================================
   -keep class com.example.libreria.** { *; }
   ```

3. **SIEMPRE** prueba en Release después de agregar dependencias.

---

## 📚 Referencias

- [ProGuard Manual](https://www.guardsquare.com/manual/configuration/usage)
- [R8 Documentation](https://developer.android.com/studio/build/shrink-code)
- [Android ProGuard Rules](https://developer.android.com/studio/build/shrink-code#keep-code)

---

## ✅ Checklist de Publicación

Antes de publicar en Google Play:

- [ ] `isMinifyEnabled = true` en release
- [ ] `isShrinkResources = true` en release
- [ ] Generar y probar APK release
- [ ] Guardar `mapping.txt` para cada versión (crucial para stack traces)
- [ ] Verificar que todas las funcionalidades principales funcionen
- [ ] Probar en diferentes dispositivos/versiones de Android
- [ ] Verificar el tamaño final de la APK/AAB

---

**Última actualización**: 2026-02-12
**Versión ProGuard Rules**: 1.0

