package com.backcube.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PointGreen,
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

private val DarkColorScheme = darkColorScheme(
    primary = DarkPointGreen,
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
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}