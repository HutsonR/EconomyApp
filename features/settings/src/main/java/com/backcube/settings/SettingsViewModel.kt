package com.backcube.settings

import com.backcube.settings.domain.GetSettingsListUseCase
import com.backcube.settings.models.SettingsEffect
import com.backcube.settings.models.SettingsIntent
import com.backcube.settings.models.SettingsState
import com.backcube.ui.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val getSettingsListUseCase: GetSettingsListUseCase
) : BaseViewModel<SettingsState, SettingsEffect>(SettingsState()) {

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

    internal fun handleIntent(intent: SettingsIntent) {
        // todo Дальше больше
        when(intent) {
            else -> Unit
        }
    }
}