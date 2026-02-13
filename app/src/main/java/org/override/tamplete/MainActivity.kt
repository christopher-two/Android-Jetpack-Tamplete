package org.override.tamplete

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.override.tamplete.core.ui.theme.AppTheme
import org.override.tamplete.main.MainViewModel

/**
 * MAIN ACTIVITY - Actividad principal con arquitectura MVI
 *
 * Implementa:
 * - Arquitectura MVI (Model-View-Intent)
 * - SplashScreen nativo que se mantiene hasta completar la carga
 * - Inyección de dependencias con Koin
 * - Tema dinámico basado en preferencias del usuario
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Instalar SplashScreen antes de super.onCreate()
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Obtener ViewModel mediante Koin
            val viewModel: MainViewModel = koinViewModel()

            // Observar el estado usando StateFlow
            val state by viewModel.state.collectAsStateWithLifecycle()

            // Mantener el SplashScreen visible mientras está cargando
            splashScreen.setKeepOnScreenCondition {
                state.isLoading
            }

            // Aplicar tema con las preferencias del usuario
            AppTheme(preferences = state.themePreferences) {
                // Contenido de la aplicación
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.isAuthenticated) {
                        Text("Bienvenido, ${state.userName ?: "Usuario"}")
                    } else {
                        Text("Pantalla de inicio")
                    }
                }
            }
        }
    }
}