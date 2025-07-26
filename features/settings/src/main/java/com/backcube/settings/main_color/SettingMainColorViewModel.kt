package com.backcube.settings.main_color

import androidx.lifecycle.viewModelScope
import com.backcube.domain.models.entities.ThemeColor
import com.backcube.domain.repositories.PreferencesRepository
import com.backcube.settings.main_color.mappers.labelRes
import com.backcube.settings.main_color.mappers.previewColor
import com.backcube.settings.main_color.models.SettingMainColorEffect
import com.backcube.settings.main_color.models.SettingMainColorIntent
import com.backcube.settings.main_color.models.SettingMainColorState
import com.backcube.settings.main_color.models.ui.ThemeColorUiModel
import com.backcube.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingMainColorViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel<SettingMainColorState, SettingMainColorEffect>(SettingMainColorState()) {

    init {
        setupCollectColor()
        setAvailableColors()
    }

    private fun setupCollectColor() {
        viewModelScope.launch {
            preferencesRepository.colorFlow.collect {
                modifyState { copy(selectedColor = it) }
            }
        }
    }

    private fun setAvailableColors() {
        val items = ThemeColor.entries.map { themeColor ->
            ThemeColorUiModel(
                color = themeColor,
                labelRes = themeColor.labelRes(),
                previewColor = themeColor.previewColor()
            )
        }

        modifyState { copy(availableColors = items) }
    }

    internal fun handleIntent(intent: SettingMainColorIntent) {
        when (intent) {
            SettingMainColorIntent.GoBack -> effect(SettingMainColorEffect.NavigateBack)
            is SettingMainColorIntent.ChangeColor -> changeColor(intent.color)
        }
    }

    private fun changeColor(color: ThemeColor) {
        viewModelScope.launch {
            preferencesRepository.setColor(color)
        }
    }
}