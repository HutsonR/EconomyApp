package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.economyapp.domain.utils.toCurrencyIsoCode

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
    currency = currency.toCurrencyIsoCode(),
    currentBalance = currentBalance.toBigDecimal(),
    history = history.map { it.toDomain() }
)