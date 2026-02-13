# Configuración del Proyecto Tamplete

## 📦 Dependencias Configuradas

Este proyecto incluye todas las dependencias del archivo `libs.versions.toml` organizadas por categorías:

### 🔧 Core Android
- **androidx.core.ktx**: Extensiones de Kotlin para Android
- **lifecycle-runtime-ktx**: Manejo del ciclo de vida
- **activity-compose**: Integración de Activity con Compose
- **core-splashscreen**: Pantalla de inicio nativa
- **biometric**: Autenticación biométrica (huella/Face ID)

### 🎨 Compose UI
- **compose-bom**: Gestión centralizada de versiones de Compose
- **compose-ui**: UI principal de Compose
- **compose-material3**: Material Design 3
- **adaptive**: Diseños adaptativos para diferentes tamaños de pantalla
- **material-icons-extended**: Iconos extendidos de Material

### 🧭 Navigation 3
- **navigation3-runtime**: Runtime de navegación
- **navigation3-ui**: UI de navegación
- **lifecycle-viewmodel-navigation3**: ViewModels con Navigation
- **material3-adaptive-navigation3**: Navegación adaptativa

### ⚡ KotlinX
- **kotlinx-datetime**: Manejo de fechas y tiempos
- **kotlinx-serialization**: Serialización JSON
- **kotlinx-coroutines-core**: Programación asíncrona
- **kotlinx-coroutines-play**: Coroutines con Play Services

### 💉 Koin (Inyección de Dependencias)
- **koin-bom**: Gestión de versiones
- **koin-core**: Core de Koin
- **koin-android**: Koin para Android
- **koin-compose**: Integración con Compose
- **koin-compose-viewmodel**: ViewModels con Koin
- **koin-androidx-workmanager**: Integración con WorkManager
- **koin-compose-navigation3**: Integración con Navigation 3

### 🗄️ Room (Base de Datos Local)
- **room-runtime**: Runtime de Room
- **room-compiler**: Compilador KSP para Room

### 🖼️ Coil (Carga de Imágenes)
- **coil-compose**: Carga y caché de imágenes para Compose
- **coil-network**: Cliente OkHttp para Coil

### 🌐 Ktor (Cliente HTTP)
- **ktor-bom**: Gestión de versiones
- **ktor-client-core**: Core del cliente HTTP
- **ktor-client-okhttp**: Engine OkHttp

### 🔥 Firebase
- **firebase-bom**: Gestión de versiones
- **firebase-auth**: Autenticación
- **firebase-firestore**: Base de datos en la nube
- **firebase-ai**: Gemini/Vertex AI

### 🔐 Google Services
- **gms-auth**: Google Sign In
- **credentials**: API de credenciales
- **cred-play-services**: Integración con Play Services

### 💾 DataStore (Preferencias)
- **datastore-pref**: Almacenamiento de preferencias
- **datastore-pref-core**: Core de DataStore

### ⚙️ WorkManager
- **work-runtime-ktx**: Tareas en segundo plano

### 🎭 UI Adicional
- **material-kolor**: Esquemas de color dinámicos
- **qrose**: Generación y escaneo de códigos QR
- **richtext-ui**: Texto enriquecido
- **haze**: Efectos de desenfoque
- **accompanist-permissions**: Manejo de permisos

### 📁 FileKit
- **filekit-core**: Selección de archivos
- **filekit-dialogs**: Diálogos de archivos
- **filekit-dialogs-compose**: Diálogos para Compose
- **filekit-coil**: Integración con Coil

### 🧪 Testing
- **junit**: Pruebas unitarias
- **koin-test**: Pruebas con Koin
- **ktor-client-mock**: Mock para pruebas de red
- **turbine**: Pruebas de Flows
- **androidx-junit**: Pruebas instrumentadas
- **espresso-core**: Pruebas de UI

## 🔌 Plugins Configurados

1. **android-application**: Plugin principal de Android
2. **kotlin-compose**: Compilación optimizada de Compose
3. **jetbrains-kotlin-serialization**: Serialización JSON
4. **koin-compiler**: Compilador de Koin
5. **google-services**: Google Services y Firebase
6. **ksp**: Kotlin Symbol Processing (para Room)

## 🗄️ Configuración de Room

### Estructura de Archivos

```
core/
├── data/
│   └── local/
│       ├── AppDatabase.kt      # Clase principal de la base de datos
│       ├── UserEntity.kt       # Ejemplo de entidad (tabla)
│       └── UserDao.kt          # Ejemplo de DAO (acceso a datos)
└── di/
    └── DatabaseModule.kt       # Módulo de Koin para Room
```

### Componentes de Room

#### 1. Entity (Entidad)
Representa una tabla en la base de datos:
```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String
)
```

#### 2. DAO (Data Access Object)
Define los métodos de acceso a datos:
```kotlin
@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: UserEntity)
    
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>
}
```

#### 3. Database
Clase abstracta que define la base de datos:
```kotlin
@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

#### 4. Inyección de Dependencias con Koin
```kotlin
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single { get<AppDatabase>().userDao() }
}
```

### Inicialización en MainActivity

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar Koin
        startKoin {
            androidContext(this@MainActivity)
            modules(appModules)
        }
    }
}
```

## 📝 Notas Importantes

1. **KSP**: Room requiere KSP (Kotlin Symbol Processing) para generar código
2. **Sincronización**: Después de modificar el `build.gradle.kts`, sincroniza el proyecto
3. **Migraciones**: En producción, usa migraciones en lugar de `fallbackToDestructiveMigration()`
4. **Coroutines**: Usa `suspend` en métodos del DAO para operaciones asíncronas
5. **Flow**: Usa `Flow` para observar cambios en tiempo real

## 🚀 Próximos Pasos

1. Sincronizar el proyecto con Gradle
2. Crear tus entidades personalizadas
3. Definir DAOs para acceder a los datos
4. Agregar las entidades al `AppDatabase`
5. Configurar migraciones si es necesario
6. Inicializar Koin en la aplicación

## 📚 Recursos Adicionales

- [Documentación de Room](https://developer.android.com/training/data-storage/room)
- [Documentación de Koin](https://insert-koin.io/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Navigation 3](https://developer.android.com/guide/navigation)

