package org.override.tamplete.feature.settings.domain.model

import androidx.compose.ui.graphics.Color
import com.materialkolor.Contrast
import com.materialkolor.PaletteStyle
import kotlinx.serialization.Serializable

/**
 * THEME PREFERENCES - Preferencias del tema de la aplicación
 *
 * Modelo que contiene todas las preferencias de personalización del tema
 * Se guarda en DataStore y se sincroniza automáticamente
 *
 * @property isDarkMode Si el tema oscuro está activado
 * @property useDynamicColors Si se usan colores dinámicos del sistema (Material You)
 * @property seedColor Color base para generar el esquema de colores (formato ARGB hex)
 * @property paletteStyle Estilo de la paleta de colores
 * @property contrastLevel Nivel de contraste (0.0 a 1.0)
 */
@Serializable
data class ThemePreferences(
    val isDarkMode: Boolean = false,
    val useDynamicColors: Boolean = true,
    val seedColor: Long = 0xFFFFFFFF, // Blanco por defecto (formato ARGB)
    val paletteStyle: String = PaletteStyle.Expressive.name,
    val contrastLevel: Double = Contrast.Default.value
) {
    companion object {
        /**
         * Preferencias por defecto
         */
        fun default() = ThemePreferences()

        /**
         * Convierte el Long a Color de Compose
         */
        fun ThemePreferences.toColor(): Color {
            return Color(seedColor.toULong())
        }

        /**
         * Convierte el String a PaletteStyle
         */
        fun ThemePreferences.toPaletteStyle(): PaletteStyle {
            return when (paletteStyle) {
                PaletteStyle.TonalSpot.name -> PaletteStyle.TonalSpot
                PaletteStyle.Neutral.name -> PaletteStyle.Neutral
                PaletteStyle.Vibrant.name -> PaletteStyle.Vibrant
                PaletteStyle.Expressive.name -> PaletteStyle.Expressive
                PaletteStyle.Rainbow.name -> PaletteStyle.Rainbow
                PaletteStyle.FruitSalad.name -> PaletteStyle.FruitSalad
                PaletteStyle.Monochrome.name -> PaletteStyle.Monochrome
                PaletteStyle.Fidelity.name -> PaletteStyle.Fidelity
                PaletteStyle.Content.name -> PaletteStyle.Content
                else -> PaletteStyle.Expressive
            }
        }
    }
}

