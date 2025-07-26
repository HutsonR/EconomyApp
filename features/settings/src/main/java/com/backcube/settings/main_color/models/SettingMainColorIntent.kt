package com.backcube.settings.main_color.models

import com.backcube.domain.models.entities.ThemeColor

internal sealed interface SettingMainColorIntent {
    data object GoBack : SettingMainColorIntent
    data class ChangeColor(val color: ThemeColor) : SettingMainColorIntent
}