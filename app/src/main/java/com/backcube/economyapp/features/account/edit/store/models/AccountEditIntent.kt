package com.backcube.economyapp.features.account.edit.store.models

import com.backcube.economyapp.domain.utils.CurrencyIsoCode

sealed interface AccountEditIntent {
    data class OnAccountNameChange(val name: String) : AccountEditIntent
    data class OnAccountBalanceChange(val balance: String) : AccountEditIntent
    data class OnCurrencySelected(val isoCode: CurrencyIsoCode) : AccountEditIntent
    data object OnSaveClick : AccountEditIntent
    data object OnCancelClick : AccountEditIntent
    data object OnOpenIsoCodeSheet : AccountEditIntent
}