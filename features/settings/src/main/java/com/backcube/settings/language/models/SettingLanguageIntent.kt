package com.backcube.settings.language.models

import com.backcube.domain.models.entities.AppLocale

internal sealed interface SettingLanguageIntent {
    data object GoBack : SettingLanguageIntent
    data class ChangeLanguage(val locale: AppLocale) : SettingLanguageIntent
}