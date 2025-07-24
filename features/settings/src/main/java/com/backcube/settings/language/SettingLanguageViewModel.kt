package com.backcube.settings.language

import androidx.lifecycle.viewModelScope
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
        viewModelScope.launch {
            preferencesRepository.localeFlow.collect {
//                modifyState { copy(isDarkTheme = theme == AppTheme.DARK) }
            }
        }
    }

    internal fun handleIntent(intent: SettingLanguageIntent) {
        when (intent) {
            SettingLanguageIntent.GoBack -> effect(SettingLanguageEffect.NavigateBack)
        }
    }
}