package com.backcube.settings.main.models.ui

import androidx.annotation.StringRes

data class SettingsUiModel(
    @StringRes val nameRes: Int,
    val id: SettingItemId,
    val type: SettingType
)