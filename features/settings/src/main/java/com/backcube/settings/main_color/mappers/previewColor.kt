package com.backcube.settings.main_color.mappers

import androidx.compose.ui.graphics.Color
import com.backcube.domain.models.entities.ThemeColor
import com.backcube.ui.theme.ColorPresets

fun ThemeColor.previewColor(): Color = ColorPresets.fromThemeColor(this)