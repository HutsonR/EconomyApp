package com.backcube.economyapp.features.settings.store.models

import com.backcube.economyapp.features.settings.models.SettingsUiModel

data class SettingsState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val items: List<SettingsUiModel> = emptyList()
)
