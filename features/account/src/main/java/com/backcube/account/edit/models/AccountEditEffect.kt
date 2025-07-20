package com.backcube.account.edit.models

import com.backcube.ui.components.AlertData

sealed interface AccountEditEffect {
    data class ShowError(val alertData: AlertData = AlertData()) : AccountEditEffect
    data object ShowCurrencySheet : AccountEditEffect
    data object GoBack : AccountEditEffect
}