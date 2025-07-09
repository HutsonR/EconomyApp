package com.backcube.economyapp.features.account.edit.store.models

import com.backcube.account.edit.store.models.AccountEditEffect

sealed interface AccountEditEffect {
    data object ShowClientError : AccountEditEffect
    data object ShowCurrencySheet : AccountEditEffect
    data object GoBack : AccountEditEffect
}