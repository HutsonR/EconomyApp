package com.backcube.settings.language

import androidx.lifecycle.viewModelScope
import com.backcube.domain.models.entities.AppLocale
import com.backcube.domain.repositories.PreferencesRepository
import com.backcube.settings.language.models.SettingLanguageEffect
import com.backcube.settings.language.models.SettingLanguageIntent
import com.backcube.settings.language.models.SettingLanguageState
import com.backcube.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingLanguageViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<SettingLanguageState, SettingLanguageEffect>(SettingLanguageState()) {

    init {
        setupCollectLocale()
        setAvailableLocales()
    }

    private fun setupCollectLocale() {
        viewModelScope.launch {
            preferencesRepository.localeFlow.collect {
                modifyState { copy(selectedLocale = it) }
            }
        }
    }

    private fun setAvailableLocales() {
        AppLocale.entries.forEach { locale ->
            modifyState { copy(availableLocales = availableLocales + (locale to locale.name)) }
        }
    }

    internal fun handleIntent(intent: SettingLanguageIntent) {
        when (intent) {
            SettingLanguageIntent.GoBack -> effect(SettingLanguageEffect.NavigateBack)
            is SettingLanguageIntent.ChangeLanguage -> changeLocale(intent.locale)
        }
    }

    private fun changeLocale(locale: AppLocale) {
        viewModelScope.launch {
            preferencesRepository.setLocale(locale)
        }
    }
}