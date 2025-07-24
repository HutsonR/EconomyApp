package com.backcube.settings.main

import androidx.lifecycle.viewModelScope
import com.backcube.domain.models.entities.AppTheme
import com.backcube.domain.repositories.PreferencesRepository
import com.backcube.settings.main.domain.GetSettingsListUseCase
import com.backcube.settings.main.models.SettingsEffect
import com.backcube.settings.main.models.SettingsIntent
import com.backcube.settings.main.models.SettingsState
import com.backcube.settings.main.models.ui.SettingItemId
import com.backcube.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getSettingsListUseCase: GetSettingsListUseCase,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<SettingsState, SettingsEffect>(SettingsState()) {

    init {
        fetchData()

        viewModelScope.launch {
            preferencesRepository.themeFlow.collect { theme ->
                modifyState { copy(isDarkTheme = theme == AppTheme.DARK) }
            }
        }
    }

    private fun fetchData() {
        modifyState {
            copy(
                items = getSettingsListUseCase()
            )
        }
    }

    internal fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ToggleDarkTheme -> {
                viewModelScope.launch {
                    val theme = if (intent.enabled) AppTheme.DARK else AppTheme.LIGHT
                    preferencesRepository.setTheme(theme)
                }
            }

            is SettingsIntent.ItemClicked -> {
                when (intent.id) {
                    SettingItemId.COLOR -> effect(SettingsEffect.NavigateToColor)
                    SettingItemId.HAPTICS -> effect(SettingsEffect.NavigateToHaptics)
                    SettingItemId.PASSCODE -> effect(SettingsEffect.NavigateToPasscode)
                    SettingItemId.SYNC -> effect(SettingsEffect.NavigateToSync)
                    SettingItemId.LANGUAGE -> effect(SettingsEffect.NavigateToLanguage)
                    SettingItemId.ABOUT -> effect(SettingsEffect.NavigateToAbout)
                    else -> Unit
                }
            }
        }
    }
}