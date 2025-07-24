package com.backcube.settings.main_color.models

sealed interface SettingMainColorEffect {
    data object NavigateBack : SettingMainColorEffect
}