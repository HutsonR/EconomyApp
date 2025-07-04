package com.backcube.economyapp.features.account.main.store.models

import com.backcube.economyapp.domain.models.accounts.AccountResponseModel
import com.backcube.economyapp.domain.utils.CurrencyIsoCode

data class AccountState(
    val isLoading: Boolean = false,
    val item: AccountResponseModel? = null,
    val currencies: List<CurrencyIsoCode> = CurrencyIsoCode.entries
)
