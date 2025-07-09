package com.backcube.economyapp.features.account.main.store.models

import com.backcube.account.main.store.models.AccountIntent
import com.backcube.domain.models.accounts.CurrencyIsoCode

sealed interface AccountIntent {
    data class OnCurrencySelected(val isoCode: CurrencyIsoCode) : AccountIntent
    data object OnOpenIsoCodeSheet : AccountIntent
    data object OnOpenEditScreen : AccountIntent
}