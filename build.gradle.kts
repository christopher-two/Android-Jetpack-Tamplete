// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Plugin principal de aplicación Android
    alias(libs.plugins.android.application) apply false

    // Plugin de Kotlin Compose
    alias(libs.plugins.kotlin.compose) apply false

    // Plugin de serialización de Kotlin
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false

    // Plugin de Koin
    alias(libs.plugins.koin.compiler) apply false

    // Plugin de Google Services
    alias(libs.plugins.google.services) apply false

    // Plugin KSP (Kotlin Symbol Processing) para Room
    alias(libs.plugins.ksp) apply false
}