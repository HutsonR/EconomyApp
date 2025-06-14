package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountHistoryResponseModel

data class AccountHistoryResponseApiModel(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryApiModel>
)

fun AccountHistoryResponseApiModel.toDomain() = AccountHistoryResponseModel(
    accountId = accountId,
    accountName = accountName,
    currency = currency,
    currentBalance = currentBalance.toBigDecimal(),
    history = history.map { it.toDomain() }
)