package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountStateModel

data class AccountStateApiModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)

fun AccountStateApiModel.toDomain() = AccountStateModel(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency
)