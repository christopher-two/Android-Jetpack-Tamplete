# 🛡️ Mejores Prácticas de ProGuard/R8

## 📖 Introducción

Este documento describe las mejores prácticas implementadas en el proyecto para ProGuard/R8.

---

## 🎯 Configuración Implementada

### 1. **Minificación Habilitada** ✅
```kotlin
isMinifyEnabled = true
```
- Reduce el tamaño del código
- Ofusca nombres de clases, métodos y campos
- Dificulta la ingeniería inversa

### 2. **Shrink Resources Habilitado** ✅
```kotlin
isShrinkResources = true
```
- Elimina recursos no utilizados (drawables, layouts, strings, etc.)
- Reduce significativamente el tamaño de la APK

### 3. **Optimizaciones** ✅
```kotlin
proguardFiles(
    getDefaultProguardFile("proguard-android-optimize.txt"),
    "proguard-rules.pro"
)
```
- Usa `proguard-android-optimize.txt` para optimizaciones agresivas
- Aplica reglas personalizadas del proyecto

---

## 📚 Estructura de Reglas

### Organización por Librería

El archivo `proguard-rules.pro` está organizado en secciones:

```
1. Configuración General
2. Kotlin & Kotlinx
3. Jetpack Compose
4. Navigation 3
5. Room Database
6. Koin (DI)
7. Ktor Client
8. OkHttp
9. DataStore
10. Firebase
11. Google Play Services
12. Coil
13. WorkManager
14. Splash Screen
15. Biometric
16. Material Kolor
17. Accompanist
18. Haze
19. FileKit
20. QRose
21. RichText
22. Modelos del Proyecto
23. ViewModels y Estados (MVI)
24. Repositorios
25. Enumeraciones y Sealed Classes
26. Parcelable
27. Testing
28. Warnings Suprimidos
```

---

## 🔍 Reglas Críticas del Proyecto

### 1. Serialización (Kotlinx Serialization)

```proguard
-keep,includedescriptorclasses class org.override.tamplete.**$$serializer { *; }
-keepclassmembers class org.override.tamplete.** {
    *** Companion;
}
-keepclasseswithmembers class org.override.tamplete.** {
    kotlinx.serialization.KSerializer serializer(...);
}
```

**¿Por qué?** Kotlinx Serialization usa reflexión y generación de código. Sin estas reglas, la deserialización fallará en runtime.

### 2. Arquitectura MVI

```proguard
# States
-keep class org.override.tamplete.**.presentation.**State { *; }

# Actions
-keep interface org.override.tamplete.**.presentation.**Action { *; }
-keep class org.override.tamplete.**.presentation.**Action* { *; }

# ViewModels
-keep class * extends androidx.lifecycle.ViewModel { <init>(...); }
```

**¿Por qué?** Los estados y acciones son parte del contrato de la UI. Ofuscarlos puede romper la navegación y el manejo de estados.

### 3. Room Database

```proguard
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *
```

**¿Por qué?** Room usa anotaciones y reflexión. Las entidades y DAOs deben mantenerse intactos.

### 4. DataStore Serializers

```proguard
-keep class org.override.tamplete.feature.auth.data.local.UserSerializer { *; }
-keep class * implements androidx.datastore.core.Serializer { <init>(...); }
```

**¿Por qué?** DataStore con serializadores personalizados necesita preservar la estructura de las clases para serializar/deserializar correctamente.

### 5. Firebase Models

```proguard
-keepclassmembers class org.override.tamplete.**.model.** {
    <fields>;
    <init>();
}
```

**¿Por qué?** Firestore usa reflexión para mapear objetos. Los campos y constructores deben preservarse.

### 6. Koin Modules

```proguard
-keep class org.override.tamplete.di.** { *; }
-keepclassmembers class org.override.tamplete.di.** { *; }
```

**¿Por qué?** Los módulos de Koin usan reflexión para la inyección de dependencias.

---

## ⚠️ Problemas Comunes y Soluciones

### Problema 1: ClassNotFoundException en Release

**Síntomas:**
```
java.lang.ClassNotFoundException: com.example.MyClass
```

**Solución:**
```proguard
-keep class com.example.MyClass { *; }
```

### Problema 2: Serialization Falla

**Síntomas:**
```
kotlinx.serialization.SerializationException: Serializer for class 'MyClass' is not found
```

**Solución:**
```proguard
-keep class com.example.MyClass { *; }
-keepclassmembers class com.example.MyClass {
    *** Companion;
}
```

### Problema 3: Navigation Falla

**Síntomas:**
- Pantallas blancas
- Crashes al navegar

**Solución:**
```proguard
-keep class org.override.tamplete.feature.**.presentation.** { *; }
```

### Problema 4: DataStore No Lee Datos

**Síntomas:**
```
Unable to parse data
```

**Solución:**
```proguard
-keep class org.override.tamplete.feature.auth.domain.model.User { *; }
-keep class * implements androidx.datastore.core.Serializer { *; }
```

### Problema 5: Koin No Encuentra Dependencias

**Síntomas:**
```
NoBeanDefFoundException: No definition found for class 'X'
```

**Solución:**
```proguard
-keep class org.override.tamplete.di.** { *; }
-keep class * { @org.koin.core.annotation.* *; }
```

---

## 🧪 Testing

### Checklist de Pruebas en Release:

1. **Compilar Release**
   ```bash
   ./gradlew assembleRelease
   ```

2. **Ejecutar Script de Verificación**
   ```bash
   ./verify-proguard.sh
   ```

3. **Instalar APK**
   ```bash
   adb install app/build/outputs/apk/release/app-release.apk
   ```

4. **Probar Funcionalidades Críticas:**
   - [ ] Splash screen se muestra correctamente
   - [ ] Login/Logout funciona
   - [ ] DataStore guarda y lee datos
   - [ ] Navegación entre pantallas funciona
   - [ ] Room guarda y lee datos
   - [ ] Llamadas de red con Ktor funcionan
   - [ ] Firebase Auth funciona
   - [ ] Firebase Firestore lee/escribe datos
   - [ ] Carga de imágenes con Coil funciona
   - [ ] WorkManager ejecuta tareas

5. **Revisar Logs**
   ```bash
   adb logcat | grep -E "ClassNotFoundException|NoSuchMethodException|SerializationException"
   ```

---

## 📊 Métricas de Éxito

Con la configuración actual, debes esperar:

| Métrica | Valor Esperado |
|---------|----------------|
| Reducción de tamaño | 30-50% |
| Clases ofuscadas | 70-90% |
| Recursos eliminados | 20-40% |
| Tiempo de compilación extra | 30-60 segundos |

---

## 🔄 Mantenimiento

### Al Agregar Nuevas Librerías:

1. **Verificar si la librería proporciona reglas ProGuard:**
   - Buscar en `AAR` → `META-INF/proguard/`
   - Revisar documentación oficial

2. **Si no hay reglas, agregar manualmente:**
   ```proguard
   # ========================================================================================
   # NUEVA LIBRERÍA
   # ========================================================================================
   -keep class com.example.newlibrary.** { *; }
   ```

3. **Probar en Release:**
   ```bash
   ./verify-proguard.sh
   ```

### Al Agregar Nuevos Modelos:

```proguard
# Si usa Kotlinx Serialization
-keep class org.override.tamplete.feature.newfeature.domain.model.** { *; }

# Si usa Firebase
-keepclassmembers class org.override.tamplete.feature.newfeature.domain.model.** {
    <fields>;
    <init>();
}
```

### Al Agregar Nuevos Repositorios:

```proguard
-keep interface org.override.tamplete.feature.newfeature.domain.repository.** { *; }
-keep class org.override.tamplete.feature.newfeature.data.**Repository { *; }
```

---

## 📝 Archivos Importantes

### mapping.txt
**Ubicación:** `app/build/outputs/mapping/release/mapping.txt`

**Uso:** Mapea nombres ofuscados a nombres originales.

**Importante:** Guarda este archivo para cada release publicada. Lo necesitarás para decodificar stack traces de producción.

### seeds.txt (Opcional)
**Ubicación:** `app/build/outputs/mapping/release/seeds.txt`

**Uso:** Lista todas las clases/métodos que ProGuard mantiene.

### usage.txt (Opcional)
**Ubicación:** `app/build/outputs/mapping/release/usage.txt`

**Uso:** Lista todas las clases/métodos que ProGuard elimina.

### configuration.txt (Opcional)
**Ubicación:** `app/build/outputs/mapping/release/configuration.txt`

**Uso:** Muestra la configuración completa usada por ProGuard.

---

## 🔗 Recursos Adicionales

- [ProGuard Manual](https://www.guardsquare.com/manual/home)
- [R8 Documentation](https://developer.android.com/studio/build/shrink-code)
- [Android Proguard Examples](https://github.com/krschultz/android-proguard-snippets)
- [Kotlin Serialization ProGuard](https://github.com/Kotlin/kotlinx.serialization#android)

---

## ⚡ Tips Rápidos

1. **Siempre prueba en Release antes de publicar**
2. **Guarda mapping.txt de cada versión publicada**
3. **Revisa logs después de la primera instalación**
4. **Mantén las reglas organizadas por librería**
5. **Documenta reglas customizadas**
6. **Usa `-dontwarn` solo cuando estés seguro**
7. **Prefiere reglas específicas sobre `-keep class ** { *; }`**

---

**Última actualización:** 2026-02-12
**Mantenido por:** Team Tamplete

