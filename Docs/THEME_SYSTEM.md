# 🎨 Sistema de Temas - Documentación

## 📁 Estructura de Carpetas

```
feature/settings/
├── domain/
│   └── model/
│       └── ThemePreferences.kt       # Modelo de datos de preferencias
├── data/
│   └── local/
│       ├── ThemePreferencesSerializer.kt   # Serializer para DataStore
│       └── ThemePreferencesRepository.kt   # Repositorio de preferencias
└── presentation/
    └── (screens de configuración - futuro)

core/ui/
└── theme/
    └── AppTheme.kt                   # Tema principal de Compose
```

---

## 🎯 Componentes

### 1. ThemePreferences (Model)
**Ubicación:** `feature/settings/domain/model/ThemePreferences.kt`

Modelo de datos que contiene todas las preferencias del tema:

```kotlin
@Serializable
data class ThemePreferences(
    val isDarkMode: Boolean = false,
    val useDynamicColors: Boolean = true,
    val seedColor: Long = 0xFFFFFFFF,
    val paletteStyle: String = "Expressive",
    val contrastLevel: Double = 0.0
)
```

**Propiedades:**
- `isDarkMode`: Activa/desactiva el modo oscuro
- `useDynamicColors`: Usa colores dinámicos de Material You (Android 12+)
- `seedColor`: Color base para generar la paleta (formato ARGB Long)
- `paletteStyle`: Estilo de paleta (Expressive, TonalSpot, Vibrant, etc.)
- `contrastLevel`: Nivel de contraste (0.0 = Normal, 1.0 = Máximo)

### 2. ThemePreferencesRepository (Data Layer)
**Ubicación:** `core/ui/data/ThemePreferencesRepository.kt`

Repositorio que gestiona la persistencia de preferencias usando DataStore.

**Funciones principales:**
```kotlin
// Observar cambios en tiempo real
val themePreferencesFlow: Flow<ThemePreferences>

// Guardar preferencias completas
suspend fun saveThemePreferences(preferences: ThemePreferences)

// Actualizar propiedades individuales
suspend fun updateDarkMode(isDark: Boolean)
suspend fun updateDynamicColors(useDynamic: Boolean)
suspend fun updateSeedColor(color: Color)
suspend fun updatePaletteStyle(style: PaletteStyle)
suspend fun updateContrastLevel(level: Double)

// Resetear a valores por defecto
suspend fun resetToDefault()
```

### 3. ThemePreferencesSerializer (Data Layer)
**Ubicación:** `core/ui/data/ThemePreferencesSerializer.kt`

Serializer personalizado para DataStore que convierte `ThemePreferences` a JSON.

### 4. AppTheme (Presentation Layer)
**Ubicación:** `core/ui/theme/AppTheme.kt`

Composable principal del tema con dos versiones:

#### Versión Manual:
```kotlin
@Composable
fun AppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    seedColor: Color = Color.White,
    style: PaletteStyle = PaletteStyle.Expressive,
    contrastLevel: Double = Contrast.Default.value,
    useDynamicColors: Boolean = true,
    content: @Composable () -> Unit
)
```

#### Versión con Preferencias:
```kotlin
@Composable
fun AppTheme(
    preferences: ThemePreferences,
    content: @Composable () -> Unit
)
```

---

## 🔄 Flujo de Datos

```
┌─────────────────────────────────────────────────────┐
│                   MainActivity                       │
│  - Observa state.themePreferences                   │
│  - Pasa preferencias a AppTheme                     │
└──────────────────┬──────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────┐
│                  MainViewModel                       │
│  - Carga preferencias al iniciar                    │
│  - Actualiza MainState con themePreferences         │
└──────────────────┬──────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────┐
│           ThemePreferencesRepository                 │
│  - Lee de DataStore                                 │
│  - Emite Flow<ThemePreferences>                     │
└──────────────────┬──────────────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────────────┐
│                    DataStore                         │
│  - Archivo: theme_prefs.json                        │
│  - Usa ThemePreferencesSerializer                   │
└─────────────────────────────────────────────────────┘
```

---

## 💻 Uso en el Código

### En MainActivity:
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val viewModel: MainViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            
            // El tema se aplica automáticamente desde el estado
            AppTheme(preferences = state.themePreferences) {
                // Tu contenido aquí
            }
        }
    }
}
```

### En MainViewModel:
```kotlin
class MainViewModel(
    private val themePreferencesRepository: ThemePreferencesRepository
) : ViewModel() {
    
    private fun initializeApp() {
        viewModelScope.launch {
            // Cargar preferencias del tema
            val themePreferences = themePreferencesRepository
                .themePreferencesFlow
                .first()
            
            _state.update { 
                it.copy(themePreferences = themePreferences) 
            }
        }
    }
}
```

### Cambiar Preferencias:
```kotlin
// Desde un ViewModel o Repository
viewModelScope.launch {
    // Cambiar a modo oscuro
    themePreferencesRepository.updateDarkMode(true)
    
    // Cambiar color base
    themePreferencesRepository.updateSeedColor(Color.Blue)
    
    // Cambiar estilo de paleta
    themePreferencesRepository.updatePaletteStyle(PaletteStyle.Vibrant)
    
    // Cambiar nivel de contraste
    themePreferencesRepository.updateContrastLevel(0.5)
}
```

---

## 🎨 Estilos de Paleta Disponibles

| Estilo | Descripción |
|--------|-------------|
| `Expressive` | Colores expresivos y vibrantes (predeterminado) |
| `TonalSpot` | Tonos sutiles con acento |
| `Neutral` | Paleta neutral y minimalista |
| `Vibrant` | Colores muy vibrantes y saturados |
| `Rainbow` | Arcoíris de colores |
| `FruitSalad` | Paleta variada de colores |
| `Monochrome` | Escala de grises |
| `Fidelity` | Fidelidad al color semilla |
| `Content` | Basado en contenido |

---

## 🌈 Colores Dinámicos (Material You)

El sistema soporta colores dinámicos de Material You en Android 12+:

```kotlin
AppTheme(
    preferences = ThemePreferences(
        useDynamicColors = true  // Usa colores del fondo de pantalla
    )
) {
    // Contenido
}
```

**Ventajas:**
- Se adapta al fondo de pantalla del usuario
- Consistencia con el resto del sistema
- Actualización automática al cambiar el fondo

**Desventajas:**
- Solo funciona en Android 12+
- No permite colores personalizados

---

## ⚙️ Configuración en Settings

Para crear una pantalla de ajustes de tema, puedes crear composables como:

```kotlin
@Composable
fun ThemeSettingsScreen(
    preferences: ThemePreferences,
    onUpdatePreferences: (ThemePreferences) -> Unit
) {
    Column {
        // Switch de modo oscuro
        SwitchPreference(
            title = "Modo Oscuro",
            checked = preferences.isDarkMode,
            onCheckedChange = { 
                onUpdatePreferences(preferences.copy(isDarkMode = it))
            }
        )
        
        // Switch de colores dinámicos
        SwitchPreference(
            title = "Colores Dinámicos",
            checked = preferences.useDynamicColors,
            onCheckedChange = { 
                onUpdatePreferences(preferences.copy(useDynamicColors = it))
            }
        )
        
        // Selector de color
        ColorPicker(
            selectedColor = Color(preferences.seedColor.toULong()),
            onColorSelected = { color ->
                onUpdatePreferences(
                    preferences.copy(seedColor = color.toArgb().toLong())
                )
            }
        )
        
        // Selector de estilo
        PaletteStyleSelector(
            selectedStyle = preferences.paletteStyle,
            onStyleSelected = { style ->
                onUpdatePreferences(preferences.copy(paletteStyle = style))
            }
        )
    }
}
```

---

## 🔧 Valores por Defecto

Las preferencias por defecto son:
- **Modo oscuro:** Desactivado (sigue el sistema)
- **Colores dinámicos:** Activado (si Android 12+)
- **Color semilla:** Blanco (#FFFFFF)
- **Estilo de paleta:** Expressive
- **Nivel de contraste:** 0.0 (Normal)

---

## 📦 Inyección de Dependencias (Koin)

El repositorio está registrado en `DataStoreModule.kt`:

```kotlin
val dataStoreModule = module {
    single<ThemePreferencesRepository> {
        ThemePreferencesRepository(androidContext())
    }
}
```

Y se inyecta automáticamente en `MainViewModel` gracias al plugin de Koin.

---

## 🧪 Testing

Para testear el tema:

```kotlin
@Test
fun testThemePreferences() = runTest {
    val repository = ThemePreferencesRepository(context)
    
    // Cambiar a modo oscuro
    repository.updateDarkMode(true)
    
    // Verificar cambio
    val preferences = repository.themePreferencesFlow.first()
    assertEquals(true, preferences.isDarkMode)
}
```

---

## 📝 Notas Importantes

1. **Persistencia Automática:** Los cambios se guardan automáticamente en DataStore
2. **Reactivo:** Los cambios se reflejan inmediatamente en la UI gracias a Flow
3. **Type-Safe:** Usa tipos seguros de Kotlin (Color, PaletteStyle)
4. **Retrocompatibilidad:** Funciona en Android 5.0+ (los colores dinámicos requieren Android 12+)
5. **Sin Flickering:** Las preferencias se cargan ANTES de mostrar la UI

---

## 🔮 Futuras Mejoras

- [ ] Soporte para múltiples temas guardados
- [ ] Temas programados por horario
- [ ] Sincronización de temas entre dispositivos
- [ ] Generación de paleta desde imagen
- [ ] Modo de alto contraste para accesibilidad

---

**Última actualización:** 2026-02-12
**Versión:** 1.0

