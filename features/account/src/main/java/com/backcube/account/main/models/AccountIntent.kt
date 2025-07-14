package com.backcube.account.main.models

import com.backcube.domain.utils.CurrencyIsoCode

internal sealed interface AccountIntent {
    data class OnCurrencySelected(val isoCode: CurrencyIsoCode) : AccountIntent
    data object OnOpenIsoCodeSheet : AccountIntent
    data object OnOpenEditScreen : AccountIntent
}