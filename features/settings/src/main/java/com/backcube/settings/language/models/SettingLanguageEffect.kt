package com.backcube.settings.language.models

sealed interface SettingLanguageEffect {
    data object NavigateBack : SettingLanguageEffect
}