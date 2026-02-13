package org.override.tamplete

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.override.tamplete.main.MainViewModel

/**
 * MAIN ACTIVITY - Actividad principal con arquitectura MVI
 *
 * Implementa:
 * - Arquitectura MVI (Model-View-Intent)
 * - SplashScreen nativo que se mantiene hasta completar la carga
 * - Inyección de dependencias con Koin
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


        }
    }
}