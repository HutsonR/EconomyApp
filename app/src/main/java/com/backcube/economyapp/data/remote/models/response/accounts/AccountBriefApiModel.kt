package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountBriefModel

data class AccountBriefApiModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)

fun AccountBriefApiModel.toDomain() = AccountBriefModel(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency
)