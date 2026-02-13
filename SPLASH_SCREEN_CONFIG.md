# 🚀 Configuración de Splash Screen

## 📖 Resumen

Este proyecto implementa un **Splash Screen** completamente integrado con el sistema de temas, que soporta:

- ✅ **Modo claro y oscuro** automático
- ✅ **Colores dinámicos** (Material You en Android 12+)
- ✅ **Color semilla personalizable**
- ✅ **Configuración programática** desde preferencias
- ✅ **Animaciones fluidas** sin flickering
- ✅ **Compatibilidad** hacia atrás (Android 5.0+)

---

## 📁 Archivos de Configuración

### 1. Recursos XML

#### `res/values/themes.xml` (Modo Claro)
```xml
<style name="Theme.Tamplete" parent="android:Theme.Material.Light.NoActionBar">
    <!-- Configuración del Splash Screen -->
    <item name="android:windowSplashScreenBackground">@color/splash_background_light</item>
    <item name="android:windowSplashScreenAnimatedIcon">@drawable/ic_launcher_foreground</item>
    <item name="android:windowSplashScreenAnimationDuration">1000</item>
    
    <!-- Barras del sistema -->
    <item name="android:statusBarColor">@color/splash_status_bar_light</item>
    <item name="android:windowLightStatusBar">true</item>
</style>
```

#### `res/values-night/themes.xml` (Modo Oscuro)
```xml
<style name="Theme.Tamplete" parent="android:Theme.Material.NoActionBar">
    <!-- Configuración del Splash Screen para modo oscuro -->
    <item name="android:windowSplashScreenBackground">@color/splash_background_dark</item>
    <item name="android:windowSplashScreenAnimatedIcon">@drawable/ic_launcher_foreground</item>
    
    <!-- Barras del sistema en modo oscuro -->
    <item name="android:statusBarColor">@color/splash_status_bar_dark</item>
    <item name="android:windowLightStatusBar">false</item>
</style>
```

#### `res/values/colors.xml`
```xml
<!-- Colores del Splash Screen -->
<color name="splash_background_light">#FFFFFFFF</color>
<color name="splash_icon_light">#FF6200EE</color>
<color name="splash_background_dark">#FF000000</color>
<color name="splash_icon_dark">#FFBB86FC</color>
```

### 2. Configurador Programático

**Ubicación:** `feature/settings/presentation/SplashScreenConfigurator.kt`

```kotlin
object SplashScreenConfigurator {
    fun configure(
        activity: Activity,
        splashScreen: SplashScreen,
        preferences: ThemePreferences
    )
}
```

---

## 🎨 Cómo Funciona

### Flujo de Carga

```
┌─────────────────────────────────────────────┐
│  1. App se inicia                           │
│     - Android muestra Splash Screen         │
│     - Usa tema del sistema (claro/oscuro)   │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│  2. MainActivity.onCreate()                 │
│     - installSplashScreen()                 │
│     - Mantiene splash visible               │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│  3. MainViewModel se inicializa             │
│     - Carga preferencias del tema           │
│     - Actualiza MainState                   │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│  4. SplashScreenConfigurator                │
│     - Aplica colores según preferencias     │
│     - Configura barras del sistema          │
└──────────────────┬──────────────────────────┘
                   ▼
┌─────────────────────────────────────────────┐
│  5. AppTheme se renderiza                   │
│     - Splash desaparece (isLoading=false)   │
│     - Animación de transición suave         │
└─────────────────────────────────────────────┘
```

### Integración con Preferencias

```kotlin
// En MainActivity
setContent {
    val viewModel: MainViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    // Configurar splash con preferencias
    LaunchedEffect(state.themePreferences) {
        SplashScreenConfigurator.configure(
            activity = this@MainActivity,
            splashScreen = splashScreen,
            preferences = state.themePreferences
        )
    }
    
    // Mantener splash hasta que termine de cargar
    splashScreen.setKeepOnScreenCondition {
        state.isLoading
    }
    
    // Aplicar tema
    AppTheme(preferences = state.themePreferences) {
        // Tu contenido
    }
}
```

---

## 🎯 Características

### 1. Tema Automático

El Splash Screen **se adapta automáticamente** al tema del sistema:

- **Modo claro:** Fondo blanco, iconos oscuros
- **Modo oscuro:** Fondo negro, iconos claros

### 2. Configuración Dinámica

Cuando el usuario cambia las preferencias del tema:

```kotlin
// El splash se actualiza automáticamente
LaunchedEffect(state.themePreferences) {
    SplashScreenConfigurator.configure(
        activity = this@MainActivity,
        splashScreen = splashScreen,
        preferences = state.themePreferences
    )
}
```

### 3. Colores Personalizados

Puedes aplicar el color semilla del tema:

```kotlin
SplashScreenConfigurator.applySeedColor(
    activity = this,
    seedColor = Color.Blue,
    isDark = true
)
```

### 4. Barras del Sistema

Las barras de estado y navegación se configuran automáticamente:

- **Modo claro:** Iconos negros
- **Modo oscuro:** Iconos blancos
- **Color:** Sigue el fondo del splash

---

## 🔧 Personalización

### Cambiar Duración de la Animación

En `themes.xml`:
```xml
<item name="android:windowSplashScreenAnimationDuration">2000</item>
<!-- Duración en milisegundos -->
```

### Cambiar Icono

En `themes.xml`:
```xml
<item name="android:windowSplashScreenAnimatedIcon">@drawable/tu_icono</item>
```

### Cambiar Colores

En `colors.xml`:
```xml
<!-- Personaliza estos colores -->
<color name="splash_background_light">#FFFAFAFA</color>
<color name="splash_icon_light">#FF1976D2</color>
```

### Animación de Salida Personalizada

En `SplashScreenConfigurator.kt`:
```kotlin
splashScreen.setOnExitAnimationListener { splashScreenView ->
    // Animación personalizada
    val alpha = ObjectAnimator.ofFloat(
        splashScreenView,
        View.ALPHA,
        1f,
        0f
    )
    alpha.duration = 500
    alpha.doOnEnd { splashScreenView.remove() }
    alpha.start()
}
```

---

## 📱 Compatibilidad

### Android 12+ (API 31+)
- ✅ Splash Screen API nativa
- ✅ Animación del icono
- ✅ Control completo de colores

### Android 5.0 - 11 (API 21-30)
- ✅ Splash Screen mediante `core-splashscreen`
- ✅ Tema aplicado desde XML
- ⚠️ Sin animación del icono (estático)

---

## ⚙️ Configuración en AndroidManifest.xml

El tema se aplica a la actividad principal:

```xml
<activity
    android:name=".MainActivity"
    android:theme="@style/Theme.Tamplete"
    android:exported="true">
    <!-- ... -->
</activity>
```

**Importante:** El tema debe aplicarse a la `Activity`, no a la `Application`.

---

## 🧪 Testing

### Probar Modo Claro/Oscuro

1. Cambia el tema del sistema:
   ```
   Ajustes > Pantalla > Tema oscuro
   ```

2. Reinicia la app

3. El splash debe cambiar automáticamente

### Probar con Preferencias Personalizadas

```kotlin
// En tu código de testing
viewModelScope.launch {
    themePreferencesRepository.updateDarkMode(true)
    themePreferencesRepository.updateSeedColor(Color.Blue)
}
```

### Verificar Duración

El splash se mantiene visible mientras:
```kotlin
splashScreen.setKeepOnScreenCondition {
    state.isLoading  // Mientras MainViewModel esté cargando
}
```

---

## 🐛 Problemas Comunes

### Splash no se muestra

**Causa:** El tema no está aplicado a la actividad.

**Solución:**
```xml
<activity
    android:name=".MainActivity"
    android:theme="@style/Theme.Tamplete">  <!-- Asegúrate de esto -->
</activity>
```

### Colores no cambian

**Causa:** Las preferencias no se están cargando correctamente.

**Solución:** Verifica que `MainViewModel` cargue las preferencias:
```kotlin
private fun initializeApp() {
    viewModelScope.launch {
        // Cargar preferencias PRIMERO
        val themePreferences = themePreferencesRepository
            .themePreferencesFlow
            .first()
        _state.update { it.copy(themePreferences = themePreferences) }
    }
}
```

### Splash desaparece muy rápido

**Causa:** `isLoading` se pone en `false` demasiado pronto.

**Solución:** Agrega un delay mínimo en `MainViewModel`:
```kotlin
// Simular carga mínima para el splash
delay(1500)
```

### Flickering al cargar

**Causa:** Las preferencias se cargan después de renderizar la UI.

**Solución:** Carga las preferencias **antes** de mostrar contenido:
```kotlin
// En MainViewModel.initializeApp()
val themePreferences = themePreferencesRepository
    .themePreferencesFlow
    .first()
_state.update { it.copy(themePreferences = themePreferences) }
```

---

## 📊 Mejores Prácticas

1. **Cargar preferencias primero:** Siempre carga las preferencias del tema antes que otros datos

2. **Mantener tiempo mínimo:** Un splash de 1-2 segundos mejora la percepción de calidad

3. **No bloquear indefinidamente:** Siempre pon un timeout máximo:
   ```kotlin
   private val splashTimeout = 5000L // 5 segundos máximo
   ```

4. **Usar colores del tema:** El splash debe verse como parte de la app, no como algo separado

5. **Probar en dispositivos reales:** Los emuladores pueden comportarse diferente

---

## 🔮 Futuras Mejoras

- [ ] Animación del icono personalizada
- [ ] Transición animada entre splash y contenido
- [ ] Precarga de recursos durante el splash
- [ ] Splash diferente para primera instalación
- [ ] Integración con onboarding

---

## 📚 Referencias

- [Splash Screen API - Android Developers](https://developer.android.com/guide/topics/ui/splash-screen)
- [SplashScreen Compat Library](https://developer.android.com/reference/androidx/core/splashscreen/SplashScreen)
- [Material Design - Launch Screen](https://m3.material.io/styles/motion/transitions/applying-transitions)

---

**Última actualización:** 2026-02-12
**Versión:** 1.0

