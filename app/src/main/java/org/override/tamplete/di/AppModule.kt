package org.override.tamplete.di

/**
 * LISTA DE TODOS LOS MÓDULOS DE LA APLICACIÓN
 * Agregar aquí todos los módulos de Koin para que sean cargados en la aplicación
 */
val appModules = listOf(
    databaseModule,   // Base de datos local con Room
    networkModule,    // Cliente HTTP con Ktor
    viewModelModule,  // ViewModels de la aplicación
    dataStoreModule,  // DataStore para preferencias y sesión
    // Agregar más módulos aquí:
    // repositoryModule, // Repositorios de datos
    // useCaseModule, // Casos de uso (Clean Architecture)
)