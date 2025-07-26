package com.backcube.settings.vibrate

import androidx.lifecycle.viewModelScope
import com.backcube.domain.models.entities.HapticEffect
import com.backcube.domain.repositories.PreferencesRepository
import com.backcube.settings.vibrate.mappers.labelRes
import com.backcube.settings.vibrate.models.SettingVibrateEffect
import com.backcube.settings.vibrate.models.SettingVibrateIntent
import com.backcube.settings.vibrate.models.SettingVibrateState
import com.backcube.settings.vibrate.models.ui.VibrateUiModel
import com.backcube.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingVibrateViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<SettingVibrateState, SettingVibrateEffect>(SettingVibrateState()) {

    init {
        setupCollectHaptic()
        setAvailableHaptics()
    }

    private fun setupCollectHaptic() {
        viewModelScope.launch {
            preferencesRepository.vibrationEnabledFlow.collect {
                modifyState { copy(isHapticsEnabled = it) }
            }
        }
        viewModelScope.launch {
            preferencesRepository.hapticEffectFlow.collect {
                modifyState { copy(currentHapticEffect = it) }
            }
        }
    }

    private fun setAvailableHaptics() {
        val items = HapticEffect.entries.map { effect ->
            VibrateUiModel(
                hapticEffect = effect,
                labelRes = effect.labelRes(),
            )
        }

        modifyState { copy(hapticAvailableEffects = items) }
    }

    internal fun handleIntent(intent: SettingVibrateIntent) {
        when (intent) {
            SettingVibrateIntent.GoBack -> effect(SettingVibrateEffect.NavigateBack)
            is SettingVibrateIntent.ChangeHapticsLevel -> changeHapticLevel(intent.effect)
            is SettingVibrateIntent.ToggleHaptics -> changeHapticActive(intent.isActive)
        }
    }

    private fun changeHapticLevel(hapticEffect: HapticEffect) {
        viewModelScope.launch {
            preferencesRepository.setHapticEffect(hapticEffect)
        }
    }

    private fun changeHapticActive(isActive: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setVibrationEnabled(isActive)
        }
    }
}