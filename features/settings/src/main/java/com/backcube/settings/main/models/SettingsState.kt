package com.backcube.settings.main.models

import com.backcube.settings.main.models.ui.SettingsUiModel

data class SettingsState(
    val items: List<SettingsUiModel> = emptyList(),
    val isDarkTheme: Boolean = false
)
