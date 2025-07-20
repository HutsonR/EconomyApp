package com.backcube.account.main.models

import com.backcube.ui.components.AlertData

sealed interface AccountEffect {
    data class ShowError(val alertData: AlertData = AlertData()) : AccountEffect
    data object ShowCurrencySheet : AccountEffect
    data class OpenEditScreen(val accountId: Int) : AccountEffect
}