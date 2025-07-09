package com.backcube.economyapp.features.account.main.store.models

import com.backcube.account.main.store.models.AccountEffect

sealed interface AccountEffect {
    data object ShowClientError : AccountEffect
    data object ShowCurrencySheet : AccountEffect
    data class OpenEditScreen(val accountId: Int) : AccountEffect
}