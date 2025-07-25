package com.backcube.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.backcube.domain.models.entities.ThemeColor

private fun generateLightColorScheme(
    primary: Color = PointGreen
) = lightColorScheme(
    primary = primary,
    onPrimary = White,
    surface = SurfaceMain,
    onSurface = TextTitle,
    onSurfaceVariant = TextSubtitle,
    surfaceVariant = LightGreen,
    outlineVariant = DividerColor,
    surfaceContainerHigh = DarkGray,
    onBackground = LightGreen,
    errorContainer = RedBackground,
    onErrorContainer = White
)

private fun generateDarkColorScheme(
    primary: Color = DarkPointGreen
) = darkColorScheme(
    primary = primary,
    onPrimary = DarkWhite,
    surface = DarkSurfaceMain,
    onSurface = DarkTextTitle,
    onSurfaceVariant = DarkTextSubtitle,
    surfaceVariant = DarkLightGreen,
    outlineVariant = DarkDividerColor,
    surfaceContainerHigh = DarkGrayNight,
    onBackground = DarkLightGreen,
    errorContainer = DarkRedBackground,
    onErrorContainer = DarkWhite
)

@Composable
fun EconomyAppTheme(
    isDarkTheme: Boolean = false,
    primaryColor: ThemeColor,
    content: @Composable () -> Unit
) {
    val primary = ColorPresets.fromThemeColor(primaryColor)
    val colorScheme = if (isDarkTheme) {
        generateDarkColorScheme(primary)
    } else {
        generateLightColorScheme(primary)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}