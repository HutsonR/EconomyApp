package com.backcube.economyapp.features.settings

import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.features.settings.domain.GetSettingsListUseCase
import com.backcube.economyapp.features.settings.store.models.SettingsEffect
import com.backcube.economyapp.features.settings.store.models.SettingsIntent
import com.backcube.economyapp.features.settings.store.models.SettingsState
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getSettingsListUseCase: GetSettingsListUseCase
) :
    BaseViewModel<SettingsState, SettingsEffect>(SettingsState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        modifyState {
            copy(
                items = getSettingsListUseCase()
            )
        }
    }

    fun handleIntent(intent: SettingsIntent) {
        // todo Дальше больше
        when(intent) {
            else -> Unit
        }
    }
}