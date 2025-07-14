package com.backcube.account.main.models

sealed interface AccountEffect {
    data object ShowClientError : AccountEffect
    data object ShowCurrencySheet : AccountEffect
    data class OpenEditScreen(val accountId: Int) : AccountEffect
}