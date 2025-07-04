package com.backcube.economyapp.features.account.edit.store.models

sealed interface AccountEditEffect {
    data object ShowClientError : AccountEditEffect
    data object ShowCurrencySheet : AccountEditEffect
    data object GoBack : AccountEditEffect
}