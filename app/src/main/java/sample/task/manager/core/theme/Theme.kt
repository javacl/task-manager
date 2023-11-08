package sample.task.manager.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val darkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    scrim = md_theme_dark_scrim
)

private val lightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    scrim = md_theme_light_scrim
)

val ColorScheme.warning: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_warning else md_theme_light_warning

val ColorScheme.onWarning: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_onWarning else md_theme_light_onWarning

val ColorScheme.success: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_success else md_theme_light_success

val ColorScheme.onSuccess: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_onSuccess else md_theme_light_onSuccess

val ColorScheme.disable: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_disable else md_theme_light_disable

val ColorScheme.onDisable: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_onDisable else md_theme_light_onDisable

val ColorScheme.divider: Color
    @Composable
    get() = if (isSystemInDarkTheme()) md_theme_dark_divider else md_theme_light_divider

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme
    } else {
        lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
