package com.backcube.economyapp.features.account.main.store.models

sealed interface AccountEffect {
    data object ShowClientError : AccountEffect
    data object ShowCurrencySheet : AccountEffect
    data class OpenEditScreen(val accountId: Int) : AccountEffect
}