package com.backcube.account.edit.models

import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.utils.CurrencyIsoCode

data class AccountEditState(
    val isLoading: Boolean = false,
    val item: AccountResponseModel? = null,
    val balance: String = "",
    val currencies: List<CurrencyIsoCode> = CurrencyIsoCode.entries
)
