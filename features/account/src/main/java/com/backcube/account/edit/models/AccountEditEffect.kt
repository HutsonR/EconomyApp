package com.backcube.account.edit.models

sealed interface AccountEditEffect {
    data object ShowClientError : AccountEditEffect
    data object ShowCurrencySheet : AccountEditEffect
    data object GoBack : AccountEditEffect
}