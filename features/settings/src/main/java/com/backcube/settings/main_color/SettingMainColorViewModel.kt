package com.backcube.settings.main_color

import androidx.lifecycle.viewModelScope
import com.backcube.domain.repositories.PreferencesRepository
import com.backcube.settings.main_color.models.SettingMainColorEffect
import com.backcube.settings.main_color.models.SettingMainColorIntent
import com.backcube.settings.main_color.models.SettingMainColorState
import com.backcube.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingMainColorViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<SettingMainColorState, SettingMainColorEffect>(SettingMainColorState()) {

    init {
        viewModelScope.launch {
            preferencesRepository.colorFlow.collect {
//                modifyState { copy(isDarkTheme = theme == AppTheme.DARK) }
            }
        }
    }

    internal fun handleIntent(intent: SettingMainColorIntent) {
        when (intent) {
            SettingMainColorIntent.GoBack -> effect(SettingMainColorEffect.NavigateBack)
        }
    }
}