package com.backcube.economyapp.data.remote.models.request.accounts

import com.backcube.economyapp.domain.models.accounts.AccountCreateRequestModel

data class AccountCreateRequestApiModel(
    val name: String,
    val balance: String,
    val currency: String
)

fun AccountCreateRequestModel.toApi() = AccountCreateRequestApiModel(
    name = name,
    balance = balance.toPlainString(),
    currency = currency
)