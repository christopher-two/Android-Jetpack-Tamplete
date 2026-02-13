# Arquitectura MVI - MainActivity

## 📐 Estructura del Proyecto

Este proyecto implementa la arquitectura **MVI (Model-View-Intent)** para la pantalla principal de la aplicación.

### Carpeta `main/`

```
main/
├── MainApp.kt          # Clase Application para inicializar Koin
├── MainActivity.kt     # Activity principal con SplashScreen
├── MainState.kt        # Estado de la UI
├── MainAction.kt       # Acciones/Intenciones del usuario
└── MainViewModel.kt    # Lógica de negocio y gestión de estado
```

## 🏗️ Arquitectura MVI

### ¿Qué es MVI?

**MVI (Model-View-Intent)** es un patrón arquitectónico basado en flujos unidireccionales de datos:

1. **Intent (Action)**: El usuario realiza una acción
2. **Model (State)**: La acción modifica el estado
3. **View**: La vista renderiza el nuevo estado

### Componentes

#### 1. **MainState.kt** - Estado de la UI

```kotlin
data class MainState(
    val isLoading: Boolean = true,
    val isInitialized: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false,
    val userName: String? = null
)
```

**Características:**
- Inmutable (data class con val)
- Representa TODO el estado de la pantalla
- Cada cambio genera un nuevo estado

#### 2. **MainAction.kt** - Acciones del usuario

```kotlin
sealed interface MainAction {
    data object InitializeApp : MainAction
    data object RetryInitialization : MainAction
    data object OnSplashComplete : MainAction
    data object Logout : MainAction
    data object ClearError : MainAction
}
```

**Características:**
- Sealed interface para tipo seguro
- Representa las intenciones del usuario
- Un solo punto de entrada para modificar el estado

#### 3. **MainViewModel.kt** - Lógica de negocio

```kotlin
class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()
    
    fun onAction(action: MainAction) {
        // Procesa acciones y actualiza el estado
    }
}
```

**Características:**
- Gestiona el estado con StateFlow
- Un solo método `onAction()` para todas las acciones
- Operaciones asíncronas con viewModelScope

#### 4. **MainActivity.kt** - Vista

```kotlin
val viewModel: MainViewModel = koinViewModel()
val state by viewModel.state.collectAsStateWithLifecycle()

MainScreen(state = state)
```

**Características:**
- Observa el estado con `collectAsStateWithLifecycle()`
- Renderiza la UI según el estado actual
- Envía acciones al ViewModel

## 🎨 SplashScreen

### Configuración

El SplashScreen se mantiene visible hasta que la carga inicial se complete:

```kotlin
val splashScreen = installSplashScreen()

splashScreen.setKeepOnScreenCondition {
    state.isLoading  // Se oculta cuando isLoading = false
}
```

### Flujo de Carga

1. **MainActivity se crea** → SplashScreen visible
2. **MainViewModel se inicializa** → Ejecuta `InitializeApp`
3. **Carga datos iniciales**:
   - Verificar autenticación
   - Cargar configuración
   - Sincronizar datos offline
4. **isLoading = false** → SplashScreen se oculta
5. **Muestra contenido apropiado** según el estado

### Datos de Ejemplo Cargados

```kotlin
private fun initializeApp() {
    viewModelScope.launch {
        // 1. Verificar autenticación
        val isAuthenticated = checkUserAuthentication()
        
        // 2. Cargar configuración inicial
        loadInitialConfiguration()
        
        // 3. Sincronizar datos offline
        syncOfflineData()
        
        // 4. Actualizar estado
        _state.update {
            it.copy(
                isLoading = false,
                isInitialized = true,
                isAuthenticated = isAuthenticated
            )
        }
    }
}
```

## 🔄 Flujo de Datos

```
┌─────────────┐
│    USER     │
│  (Action)   │
└──────┬──────┘
       │
       ▼
┌─────────────────┐
│   ViewModel     │
│  onAction()     │
└──────┬──────────┘
       │
       ▼
┌─────────────────┐
│  Update State   │
│  (_state.update)│
└──────┬──────────┘
       │
       ▼
┌─────────────────┐
│   UI Recompone  │
│  (Observe State)│
└─────────────────┘
```

## 💉 Inyección de Dependencias

### ViewModelModule.kt

```kotlin
val viewModelModule = module {
    viewModel { MainViewModel() }
}
```

### Uso en Compose

```kotlin
val viewModel: MainViewModel = koinViewModel()
```

## 🎯 Ventajas de MVI

✅ **Flujo de datos unidireccional** - Más fácil de entender y depurar
✅ **Estado predecible** - Un solo estado inmutable
✅ **Testeable** - Fácil de escribir tests unitarios
✅ **Reproducible** - Los estados se pueden guardar y reproducir
✅ **Thread-safe** - StateFlow maneja la concurrencia

## 📝 Cómo Agregar Nuevas Funcionalidades

### 1. Agregar un nuevo campo al estado

```kotlin
data class MainState(
    // ...existing code...
    val newField: String = ""
)
```

### 2. Agregar una nueva acción

```kotlin
sealed interface MainAction {
    // ...existing code...
    data class UpdateNewField(val value: String) : MainAction
}
```

### 3. Procesar la acción en el ViewModel

```kotlin
fun onAction(action: MainAction) {
    when (action) {
        // ...existing code...
        is MainAction.UpdateNewField -> updateNewField(action.value)
    }
}

private fun updateNewField(value: String) {
    _state.update { it.copy(newField = value) }
}
```

### 4. Usar en la UI

```kotlin
@Composable
fun MainScreen(state: MainState, onAction: (MainAction) -> Unit) {
    Text(text = state.newField)
    Button(onClick = { onAction(MainAction.UpdateNewField("nuevo valor")) }) {
        Text("Actualizar")
    }
}
```

## 🧪 Testing

### Test de Estado

```kotlin
@Test
fun `test initial state`() {
    val viewModel = MainViewModel()
    val state = viewModel.state.value
    
    assertTrue(state.isLoading)
    assertFalse(state.isInitialized)
}
```

### Test de Acciones

```kotlin
@Test
fun `test clear error action`() = runTest {
    val viewModel = MainViewModel()
    
    viewModel.onAction(MainAction.ClearError)
    
    assertNull(viewModel.state.value.errorMessage)
}
```

## 🚀 Próximos Pasos

1. Implementar verificación real de autenticación
2. Conectar con DataStore para persistencia
3. Agregar navegación con Navigation Compose
4. Implementar manejo de errores más robusto
5. Agregar analytics y logging

## 📚 Referencias

- [MVI Architecture Pattern](https://hannesdorfmann.com/android/mosby3-mvi-1/)
- [StateFlow Documentation](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- [Koin Documentation](https://insert-koin.io/)
- [SplashScreen API](https://developer.android.com/develop/ui/views/launch/splash-screen)

