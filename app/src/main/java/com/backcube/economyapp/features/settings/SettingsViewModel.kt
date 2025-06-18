package com.backcube.economyapp.features.settings

import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.features.settings.models.SettingType
import com.backcube.economyapp.features.settings.models.SettingsUiModel
import com.backcube.economyapp.features.settings.store.models.SettingsEffect
import com.backcube.economyapp.features.settings.store.models.SettingsIntent
import com.backcube.economyapp.features.settings.store.models.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    BaseViewModel<SettingsState, SettingsEffect>(SettingsState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        modifyState {
            copy(
                items = getSettingsList()
            )
        }
    }

    fun handleIntent(intent: SettingsIntent) {
        // todo Дальше больше
        when(intent) {
            else -> Unit
        }
    }

    private fun getSettingsList() = listOf(
        SettingsUiModel(
            name = "Темная тема",
            type = SettingType.SWITCH
        ),
        SettingsUiModel(
            name = "Основной цвет",
            type = SettingType.LINK
        ),
        SettingsUiModel(
            name = "Звуки",
            type = SettingType.LINK
        ),
        SettingsUiModel(
            name = "Хаптики",
            type = SettingType.LINK
        ),
        SettingsUiModel(
            name = "Код пароль",
            type = SettingType.LINK
        ),
        SettingsUiModel(
            name = "Синхронизация",
            type = SettingType.LINK
        ),
        SettingsUiModel(
            name = "Язык",
            type = SettingType.LINK
        ),
        SettingsUiModel(
            name = "О программе",
            type = SettingType.LINK
        )
    )
}