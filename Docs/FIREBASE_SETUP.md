# 🔥 Configuración de Firebase (Opcional)

## 📋 Resumen

Este proyecto soporta Firebase de manera **opcional**. El proyecto compilará y funcionará sin Firebase configurado, pero las funcionalidades de Firebase Auth, Firestore y Vertex AI no estarán disponibles.

---

## ✅ Estado Actual

- ✅ Proyecto compila SIN Firebase configurado
- ✅ Firebase es completamente opcional
- ✅ Las dependencias de Firebase solo se incluyen si `google-services.json` existe

---

## 🚀 Cómo Habilitar Firebase

### Paso 1: Crear Proyecto en Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Haz clic en "Agregar proyecto" o selecciona un proyecto existente
3. Sigue el asistente de configuración

### Paso 2: Registrar tu App Android

1. En la consola de Firebase, ve a **Configuración del proyecto** (⚙️)
2. Selecciona la pestaña **Tus apps**
3. Haz clic en el icono de Android
4. Completa los datos:
   - **Nombre del paquete**: `org.override.tamplete`
   - **Alias de la app** (opcional): `Tamplete`
   - **Certificado de firma SHA-1** (opcional, pero recomendado para Auth)

### Paso 3: Descargar google-services.json

1. Firebase generará el archivo `google-services.json`
2. Descárgalo
3. Copia el archivo a la carpeta `app/` del proyecto:
   ```bash
   cp ~/Downloads/google-services.json app/google-services.json
   ```

### Paso 4: Verificar la Configuración

Ejecuta el proyecto y verifica en los logs:

```
✅ Google Services habilitado - google-services.json encontrado
```

Si ves:
```
⚠️  Google Services deshabilitado - google-services.json no encontrado
```

Significa que el archivo no está en el lugar correcto.

---

## 🔑 Obtener SHA-1 (Para Google Sign-In)

Si planeas usar Google Sign-In, necesitas agregar tu certificado SHA-1:

### Debug SHA-1:
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

### Release SHA-1:
```bash
keytool -list -v -keystore /path/to/your/keystore.jks -alias your_alias
```

Copia el SHA-1 y agrégalo en:
**Firebase Console > Configuración del proyecto > Tus apps > [Tu app] > Certificados SHA**

---

## 📦 Servicios de Firebase Habilitados

Una vez configurado, tendrás acceso a:

### 1. Firebase Authentication
```kotlin
// Ejemplo de uso
FirebaseAuth.getInstance()
```

**Servicios soportados:**
- Email/Password
- Google Sign-In
- Anónimo
- Otros proveedores disponibles

### 2. Cloud Firestore
```kotlin
// Ejemplo de uso
FirebaseFirestore.getInstance()
```

**Características:**
- Base de datos NoSQL en tiempo real
- Offline persistence
- Queries avanzadas

### 3. Vertex AI (Firebase AI)
```kotlin
// Ejemplo de uso
Firebase.vertexAI
```

**Características:**
- Generative AI
- Modelos Gemini
- Chat y generación de contenido

---

## 🧪 Testing Sin Firebase

El proyecto está configurado para funcionar sin Firebase:

```bash
# Compilar sin Firebase
./gradlew assembleDebug

# Ejecutar tests
./gradlew test
```

**Nota:** Si usas funcionalidades de Firebase en tu código, asegúrate de manejar el caso cuando Firebase no está configurado.

---

## 🛡️ Seguridad

### ⚠️ IMPORTANTE:

1. **NUNCA** subas `google-services.json` a Git
   - Ya está incluido en `.gitignore`
   
2. **Mantén seguro** tu archivo `google-services.json`
   - Contiene información sensible de tu proyecto
   
3. **Usa diferentes proyectos** para desarrollo y producción
   - `google-services-dev.json` para desarrollo
   - `google-services-prod.json` para producción

### Usar Diferentes Configuraciones:

```kotlin
// En build.gradle.kts puedes configurar:
buildTypes {
    debug {
        // Usa archivo de dev si existe
    }
    release {
        // Usa archivo de prod si existe
    }
}
```

---

## 🔧 Configuración Avanzada

### Habilitar Servicios Específicos

En Firebase Console, habilita los servicios que necesites:

#### 1. Authentication
- Ve a **Build > Authentication**
- Haz clic en "Comenzar"
- Habilita los métodos de inicio de sesión que necesites

#### 2. Firestore
- Ve a **Build > Firestore Database**
- Haz clic en "Crear base de datos"
- Selecciona el modo (producción/prueba)
- Elige la ubicación

#### 3. Vertex AI
- Ve a **Build > Vertex AI in Firebase**
- Haz clic en "Comenzar"
- Acepta los términos

---

## 📊 Costos

Firebase tiene un plan gratuito generoso:

| Servicio | Límite Gratuito |
|----------|-----------------|
| Authentication | Ilimitado |
| Firestore | 50,000 lecturas/día |
| Storage | 1 GB |
| Hosting | 10 GB/mes |

[Ver precios completos](https://firebase.google.com/pricing)

---

## 🐛 Troubleshooting

### Error: "google-services.json is missing"

**Solución:**
```bash
# Verifica que el archivo existe
ls -la app/google-services.json

# Si no existe, cópialo desde Firebase Console
```

### Error: "Default FirebaseApp is not initialized"

**Causa:** Firebase no está inicializado correctamente.

**Solución:**
1. Verifica que `google-services.json` esté en `app/`
2. Sincroniza Gradle: `File > Sync Project with Gradle Files`
3. Limpia y reconstruye: `./gradlew clean build`

### Google Sign-In no funciona

**Causa:** Falta el certificado SHA-1.

**Solución:**
1. Obtén tu SHA-1 (ver sección anterior)
2. Agrégalo en Firebase Console
3. Descarga nuevamente `google-services.json`
4. Reemplaza el archivo en `app/`

---

## 📚 Recursos

- [Documentación de Firebase](https://firebase.google.com/docs)
- [Firebase Android Setup](https://firebase.google.com/docs/android/setup)
- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Cloud Firestore](https://firebase.google.com/docs/firestore)
- [Vertex AI](https://firebase.google.com/docs/vertex-ai)

---

## ✅ Checklist de Configuración

- [ ] Crear proyecto en Firebase Console
- [ ] Registrar app Android con package name `org.override.tamplete`
- [ ] Descargar `google-services.json`
- [ ] Copiar archivo a `app/google-services.json`
- [ ] Verificar que el proyecto compila
- [ ] Habilitar Authentication en Firebase Console
- [ ] Habilitar Firestore en Firebase Console
- [ ] Agregar SHA-1 para Google Sign-In (si se usa)
- [ ] Configurar reglas de seguridad de Firestore
- [ ] Probar en dispositivo real

---

**Última actualización:** 2026-02-12
**Versión:** 1.0

