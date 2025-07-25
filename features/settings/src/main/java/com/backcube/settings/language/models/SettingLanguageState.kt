package com.backcube.settings.language.models

import com.backcube.domain.models.entities.AppLocale

data class SettingLanguageState(
    val selectedLocale: AppLocale = AppLocale.RU,
    val availableLocales: Map<AppLocale, String> = emptyMap<AppLocale, String>()
)
