package com.backcube.settings.vibrate.models

sealed interface SettingVibrateEffect {
    data object NavigateBack : SettingVibrateEffect
}