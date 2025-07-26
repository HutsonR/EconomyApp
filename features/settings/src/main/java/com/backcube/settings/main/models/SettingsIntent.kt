package com.backcube.settings.main.models

import com.backcube.settings.main.models.ui.SettingItemId

internal sealed interface SettingsIntent {
    data class ToggleDarkTheme(val enabled: Boolean) : SettingsIntent
    data class ItemClicked(val id: SettingItemId) : SettingsIntent
}