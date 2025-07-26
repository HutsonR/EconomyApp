package com.backcube.settings.main_color.models.ui

import androidx.compose.ui.graphics.Color
import com.backcube.domain.models.entities.ThemeColor

data class ThemeColorUiModel(
    val color: ThemeColor,
    val labelRes: Int,
    val previewColor: Color
)
