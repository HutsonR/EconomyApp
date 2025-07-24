package com.backcube.settings.models

import com.backcube.settings.models.ui.SettingsUiModel

data class SettingsState(
    val isLoading: Boolean = false,
    val items: List<SettingsUiModel> = emptyList()
)
