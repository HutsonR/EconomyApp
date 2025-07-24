package com.backcube.settings.language.models

internal sealed interface SettingLanguageIntent {
    data object GoBack : SettingLanguageIntent
}