package edu.unibo.tracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkBluePalette = darkColors(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,

    error = md_theme_dark_error,

    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
)
private val DarkColorPaletteProva = darkColors(
    primary = Color.LightGray,
    secondary = Color.Red, //FAB
    surface = Color.Black,
    onSecondary = Color.White
)
private val FitnessLightPalette = lightColors(
    primary = Color(0xFF4CAF50), // Bright Green
    onPrimary = Color(0xFFFFFFFF), // White text on green
    secondary = Color(0xFFFF9800), // Orange accent
    onSecondary = Color(0xFFFFFFFF), // White text on orange
    background = Color(0xFFFFFFFF), // Pure white background
    onBackground = Color(0xFF212121), // Dark text on light background
    surface = Color(0xFFF5F5F5), // Light gray surface
    onSurface = Color(0xFF212121), // Dark text on light surface
    error = Color(0xFFD32F2F) // Red for errors
)
private val FitnessDarkPalette = darkColors(
    primary = Color(0xFF66BB6A), // Bright Green for dark mode
    onPrimary = Color(0xFF000000), // Black text on green
    secondary = Color(0xFFFF9800), // Orange accent
    onSecondary = Color(0xFF000000), // Black text on orange
    background = Color(0xFF121212), // True dark background
    onBackground = Color(0xFFE0E0E0), // Light text on dark background
    surface = Color(0xFF1E1E1E), // Dark surface
    onSurface = Color(0xFFE0E0E0), // Light text on dark surface
    error = Color(0xFFFF5252) // Red for errors
)
private val LightBluePalette = lightColors(

    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
)

@Composable
fun FitTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        FitnessDarkPalette
    } else {
        FitnessLightPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

// Alias for backward compatibility
@Composable
fun ProvaComponentiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) = FitTrackerTheme(darkTheme, content)