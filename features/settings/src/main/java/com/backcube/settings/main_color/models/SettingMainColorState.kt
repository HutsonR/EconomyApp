package com.backcube.settings.main_color.models

import com.backcube.domain.models.entities.ThemeColor
import com.backcube.settings.main_color.models.ui.ThemeColorUiModel

data class SettingMainColorState(
    val selectedColor: ThemeColor = ThemeColor.GREEN,
    val availableColors: List<ThemeColorUiModel> = emptyList<ThemeColorUiModel>()
)
