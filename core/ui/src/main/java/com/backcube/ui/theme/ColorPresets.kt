// core/ui/theme/ColorPresets.kt
package com.backcube.ui.theme

import androidx.compose.ui.graphics.Color
import com.backcube.domain.models.entities.ThemeColor

object ColorPresets {
    val GREEN = Color(0xFF2AE881)
    val BLUE = Color(0xFF42A5F5)
    val ORANGE = Color(0xFFFFA726)
    val PURPLE = Color(0xFFAB47BC)
    val PINK = Color(0xFFEE4880)

    fun fromThemeColor(themeColor: ThemeColor): Color = when (themeColor) {
        ThemeColor.GREEN -> GREEN
        ThemeColor.BLUE -> BLUE
        ThemeColor.ORANGE -> ORANGE
        ThemeColor.PURPLE -> PURPLE
        ThemeColor.PINK -> PINK
    }
}
