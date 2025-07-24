package com.backcube.settings.about.models

sealed interface SettingAboutEffect {
    data object NavigateBack : SettingAboutEffect
}