package org.override.tamplete.di

/**
 * LISTA DE TODOS LOS MÓDULOS DE LA APLICACIÓN
 * Agregar aquí todos los módulos de Koin para que sean cargados en la aplicación
 */
val appModules = listOf(
    databaseModule,    // Base de datos local con Room
    networkModule,     // Cliente HTTP con Ktor
    navigationModule,  // Navegadores y controladores de navegación
    viewModelModule,   // ViewModels de la aplicación
    dataStoreModule,   // DataStore para preferencias y sesión
    screenModule       // Registro de pantallas con navegación
)