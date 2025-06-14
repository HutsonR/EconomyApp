package com.backcube.economyapp.data.remote.models.request.accounts

import com.backcube.economyapp.domain.models.accounts.AccountUpdateRequestModel

data class AccountUpdateRequestApiModel(
    val name: String,
    val balance: String,
    val currency: String
)

fun AccountUpdateRequestModel.toApi() = AccountUpdateRequestApiModel(
    name = name,
    balance = balance.toPlainString(),
    currency = currency
)