package com.backcube.account.edit.store.models

import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.CurrencyIsoCode

data class AccountEditState(
    val isLoading: Boolean = false,
    val item: AccountResponseModel? = null,
    val balance: String = "",
    val currencies: List<CurrencyIsoCode> = CurrencyIsoCode.entries
)
