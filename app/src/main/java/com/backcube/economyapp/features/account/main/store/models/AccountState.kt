package com.backcube.economyapp.features.account.main.store.models

import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.CurrencyIsoCode

data class AccountState(
    val isLoading: Boolean = false,
    val item: AccountResponseModel? = null,
    val currencies: List<CurrencyIsoCode> = CurrencyIsoCode.entries
)
