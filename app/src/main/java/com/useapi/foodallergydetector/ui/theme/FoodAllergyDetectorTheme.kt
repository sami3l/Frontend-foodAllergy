package com.useapi.foodallergydetector.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary               = md_theme_light_primary,
    onPrimary             = md_theme_light_onPrimary,
    primaryContainer      = md_theme_light_primaryContainer,
    onPrimaryContainer    = md_theme_light_onPrimaryContainer,
    secondary             = md_theme_light_secondary,
    onSecondary           = md_theme_light_onSecondary,
    secondaryContainer    = md_theme_light_secondaryContainer,
    onSecondaryContainer  = md_theme_light_onSecondaryContainer,
    background            = md_theme_light_background,
    onBackground          = md_theme_light_onBackground,
    surface               = md_theme_light_surface,
    onSurface             = md_theme_light_onSurface,
    error                 = md_theme_light_error,
    onError               = md_theme_light_onError
)

private val DarkColors = darkColorScheme(
    primary               = md_theme_dark_primary,
    onPrimary             = md_theme_dark_onPrimary,
    primaryContainer      = md_theme_dark_primaryContainer,
    onPrimaryContainer    = md_theme_dark_onPrimaryContainer,
    secondary             = md_theme_dark_secondary,
    onSecondary           = md_theme_dark_onSecondary,
    secondaryContainer    = md_theme_dark_secondaryContainer,
    onSecondaryContainer  = md_theme_dark_onSecondaryContainer,
    background            = md_theme_dark_background,
    onBackground          = md_theme_dark_onBackground,
    surface               = md_theme_dark_surface,
    onSurface             = md_theme_dark_onSurface,
    error                 = md_theme_dark_error,
    onError               = md_theme_dark_onError
)

/**
 * Your app’s root Material 3 theme. Wrap your entire Compose UI in this.
 *
 * @param darkTheme if `true` forces dark; default follows system setting
 */
@Composable
fun FoodAllergyDetectorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // you could also expose dynamicColor if you want Material You on Android 12+
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme   = colors,
        typography    = Typography,
        content       = content
    )
}
