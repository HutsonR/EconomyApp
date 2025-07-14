package com.backcube.account.edit.models

import com.backcube.domain.utils.CurrencyIsoCode

internal sealed interface AccountEditIntent {
    data class OnAccountNameChange(val name: String) : AccountEditIntent
    data class OnAccountBalanceChange(val balance: String) : AccountEditIntent
    data class OnCurrencySelected(val isoCode: CurrencyIsoCode) : AccountEditIntent
    data object OnSaveClick : AccountEditIntent
    data object OnCancelClick : AccountEditIntent
    data object OnOpenIsoCodeSheet : AccountEditIntent
}