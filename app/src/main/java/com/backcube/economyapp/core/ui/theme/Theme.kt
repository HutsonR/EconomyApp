package com.backcube.economyapp.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PointGreen,
    surface = SurfaceMain,
    onSurface = TextTitle,
    onSurfaceVariant = TextSubtitle,
    outlineVariant = DividerColor,
    surfaceContainerHigh = DarkGray,
    onBackground = LightGreen,
    errorContainer = RedBackground,
    onErrorContainer = White
)

@Composable
fun EconomyAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}