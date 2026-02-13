plugins {
    // Plugin principal de aplicación Android
    alias(libs.plugins.android.application)

    // Plugin de Kotlin Compose para compilación optimizada
    alias(libs.plugins.kotlin.compose)

    // Plugin de serialización de Kotlin para JSON
    alias(libs.plugins.jetbrains.kotlin.serialization)

    // Plugin de Koin para inyección de dependencias (compilador)
    alias(libs.plugins.koin.compiler)

    // Plugin de Google Services para Firebase
    alias(libs.plugins.google.services)

    // Plugin KSP para procesamiento de anotaciones (necesario para Room)
    alias(libs.plugins.ksp)
}
val nameProject = "tamplete"

android {
    namespace = "org.override.$nameProject"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "org.override.$nameProject"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ==================== CORE ANDROID ====================
    // Biblioteca principal de Android KTX con extensiones de Kotlin
    implementation(libs.androidx.core.ktx)

    // Lifecycle runtime para manejo del ciclo de vida de componentes
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Integración de Activity con Compose
    implementation(libs.androidx.activity.compose)

    // Core Splashscreen para pantalla de inicio nativa
    implementation(libs.core.splashscreen)

    // Biometría para autenticación con huella/face ID
    implementation(libs.biometric)

    // ==================== COMPOSE UI ====================
    // BOM de Compose para gestión centralizada de versiones
    implementation(platform(libs.androidx.compose.bom))

    // UI principal de Compose
    implementation(libs.androidx.compose.ui)

    // Gráficos de Compose
    implementation(libs.androidx.compose.ui.graphics)

    // Preview de herramientas para desarrollo
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Material Design 3 para Compose
    implementation(libs.androidx.compose.material3)

    // Material Design Adaptive para diseños adaptativos
    implementation(libs.adaptive)
    implementation(libs.adaptive.layout)
    implementation(libs.adaptive.navigation)

    // Iconos extendidos de Material Design
    implementation(libs.material.icons.ext)

    // ==================== NAVIGATION 3 ====================
    // Runtime de Navigation 3 para navegación entre pantallas
    implementation(libs.androidx.navigation3.runtime)

    // UI de Navigation 3
    implementation(libs.androidx.navigation3.ui)

    // Integración de ViewModel con Navigation 3
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // Navegación adaptativa con Material 3 y Navigation 3
    implementation(libs.androidx.material3.adaptive.navigation3)

    // ==================== KOTLINX ====================
    // Manejo de fechas y tiempos multiplataforma
    implementation(libs.kotlinx.datetime)

    // Serialización y deserialización JSON
    implementation(libs.kotlinx.serialization)

    // Coroutines para programación asíncrona
    implementation(libs.kotlinx.coroutines.core)

    // Integración de Coroutines con Play Services
    implementation(libs.kotlinx.coroutines.play)

    // ==================== KOIN (INYECCIÓN DE DEPENDENCIAS) ====================
    // BOM de Koin para gestión de versiones
    implementation(platform(libs.koin.bom))

    // Core de Koin
    implementation(libs.koin.core)

    // Koin para Android
    implementation(libs.koin.android)

    // Integración de Koin con Compose
    implementation(libs.koin.compose)

    // ViewModels con Koin en Compose
    implementation(libs.koin.compose.viewmodel)

    // Integración de Koin con WorkManager
    implementation(libs.koin.androidx.workmanager)

    // Integración de Koin con Navigation 3
    implementation(libs.koin.compose.navigation3)

    // ==================== ROOM (BASE DE DATOS LOCAL) ====================
    // Runtime de Room para base de datos SQLite
    implementation(libs.room.runtime)

    // Compilador de Room para generar código (requiere KSP)
    ksp(libs.room.compiler)

    // ==================== COIL (CARGA DE IMÁGENES) ====================
    // Coil para Compose - carga y caché de imágenes
    implementation(libs.coil.compose)

    // Cliente de red OkHttp para Coil
    implementation(libs.coil.network)

    // ==================== KTOR (CLIENTE HTTP) ====================
    // BOM de Ktor para gestión de versiones
    implementation(platform(libs.ktor.bom))

    // Core de Ktor Client
    implementation(libs.ktor.client.core)

    // Engine OkHttp para Ktor
    implementation(libs.ktor.client.okhttp)

    // Content Negotiation para serialización/deserialización automática
    implementation(libs.ktor.client.content.negotiation)

    // Serialización JSON para Ktor
    implementation(libs.ktor.serialization.json)

    // Logging para depuración de peticiones HTTP
    implementation(libs.ktor.client.logging)

    // ==================== FIREBASE ====================
    // BOM de Firebase para gestión de versiones
    implementation(platform(libs.firebase.bom))

    // Firebase Authentication para autenticación de usuarios
    implementation(libs.firebase.auth)

    // Firebase Firestore para base de datos en la nube
    implementation(libs.firebase.firestore)

    // Firebase AI (Gemini/Vertex AI)
    implementation(libs.firebase.ai)

    // ==================== GOOGLE SERVICES ====================
    // Google Sign In para autenticación con Google
    implementation(libs.gms.auth)

    // Credentials API para gestión de credenciales
    implementation(libs.credentials)

    // Integración de Credentials con Play Services
    implementation(libs.cred.play.services)

    // ==================== DATASTORE (PREFERENCIAS) ====================
    // Datastore para almacenamiento de preferencias (reemplazo de SharedPreferences)
    implementation(libs.datastore.pref)

    // Core de Datastore
    implementation(libs.datastore.pref.core)

    // ==================== WORKMANAGER ====================
    // WorkManager para tareas en segundo plano
    implementation(libs.work.runtime.ktx)

    // ==================== UI ADICIONAL ====================
    // Material Kolor para generación dinámica de esquemas de color
    implementation(libs.material.kolor)

    // QRose para generación y escaneo de códigos QR
    implementation(libs.qrose)

    // RichText UI para texto enriquecido
    implementation(libs.richtext.ui)

    // RichText UI con Material 3
    implementation(libs.richtext.ui.m3)

    // Haze para efectos de desenfoque
    implementation(libs.haze)

    // Accompanist Permissions para manejo de permisos en Compose
    implementation(libs.accompanist.permissions)

    // ==================== FILEKIT ====================
    // FileKit Core para selección de archivos
    implementation(libs.filekit.core)

    // FileKit Dialogs para diálogos de archivos
    implementation(libs.filekit.dialogs)

    // FileKit Dialogs con Compose
    implementation(libs.filekit.dialogs.compose)

    // Integración de FileKit con Coil
    implementation(libs.filekit.coil)

    // ==================== TESTING ====================
    // JUnit para pruebas unitarias
    testImplementation(libs.junit)

    // Koin Test para pruebas con inyección de dependencias
    testImplementation(libs.koin.test)

    // Ktor Mock Client para pruebas de red
    testImplementation(libs.ktor.client.mock)

    // Turbine para pruebas de Flows
    testImplementation(libs.turbine)

    // JUnit para pruebas instrumentadas en Android
    androidTestImplementation(libs.androidx.junit)

    // Espresso para pruebas de UI
    androidTestImplementation(libs.androidx.espresso.core)

    // BOM de Compose para pruebas
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // JUnit4 para pruebas de UI con Compose
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // ==================== DEBUG/TOOLING ====================
    // Herramientas de desarrollo para Compose
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Manifesto de pruebas para Compose
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}