package com.backcube.settings.vibrate

import androidx.lifecycle.viewModelScope
import com.backcube.domain.repositories.PreferencesRepository
import com.backcube.settings.vibrate.models.SettingVibrateEffect
import com.backcube.settings.vibrate.models.SettingVibrateIntent
import com.backcube.settings.vibrate.models.SettingVibrateState
import com.backcube.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingVibrateViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<SettingVibrateState, SettingVibrateEffect>(SettingVibrateState()) {

    init {
        viewModelScope.launch {
            preferencesRepository.vibrationEnabledFlow.collect {
//                modifyState { copy(isDarkTheme = theme == AppTheme.DARK) }
            }
            preferencesRepository.hapticEffectFlow.collect {

            }
        }
    }

    internal fun handleIntent(intent: SettingVibrateIntent) {
        when (intent) {
            SettingVibrateIntent.GoBack -> effect(SettingVibrateEffect.NavigateBack)
        }
    }
}