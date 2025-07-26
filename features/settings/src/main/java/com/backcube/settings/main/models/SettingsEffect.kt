package com.backcube.settings.main.models

sealed interface SettingsEffect {
    data object NavigateToColor : SettingsEffect
    data object NavigateToHaptics : SettingsEffect
    data object NavigateToPasscode : SettingsEffect
    data object NavigateToSync : SettingsEffect
    data object NavigateToLanguage : SettingsEffect
    data object NavigateToAbout : SettingsEffect
}