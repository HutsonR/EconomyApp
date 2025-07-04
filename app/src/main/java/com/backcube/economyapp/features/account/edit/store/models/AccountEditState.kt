package com.backcube.economyapp.features.account.edit.store.models

import com.backcube.economyapp.domain.models.accounts.AccountResponseModel
import com.backcube.economyapp.domain.utils.CurrencyIsoCode

data class AccountEditState(
    val isLoading: Boolean = false,
    val item: AccountResponseModel? = null,
    val balance: String = "",
    val currencies: List<CurrencyIsoCode> = CurrencyIsoCode.entries
)
