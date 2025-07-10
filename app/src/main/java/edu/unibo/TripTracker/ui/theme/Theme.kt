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
    onPrimary= Color(0xFFffffff),
    secondary = Color(0xFFFF9800), // Orange
    onSecondary = Color(0xFFffffff),
    background = Color(0xFFF1F8E9), // Light Green Background
    surface = Color(0xFFE8F5E8) // Slightly Green Surface
)
private val FitnessDarkPalette = darkColors(
    primary = Color(0xFF81C784), // Light Green for dark mode
    onPrimary= Color(0xFF1B5E20),
    secondary = Color(0xFFFFB74D), // Light Orange for dark mode
    onSecondary = Color(0xFF000000),
    background = Color(0xFF1B5E20), // Dark Green Background
    surface = Color(0xFF2E7D32) // Darker Green Surface
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