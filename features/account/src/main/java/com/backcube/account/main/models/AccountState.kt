package com.backcube.account.main.models

import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.utils.CurrencyIsoCode

data class AccountState(
    val isLoading: Boolean = false,
    val item: AccountResponseModel? = null,
    val currencies: List<CurrencyIsoCode> = CurrencyIsoCode.entries
)
