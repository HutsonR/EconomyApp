package com.backcube.economyapp.features.account.store.models

import com.backcube.economyapp.domain.models.accounts.AccountResponseModel

data class AccountState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val item: AccountResponseModel? = null
)
