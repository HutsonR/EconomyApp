package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountResponseModel
import com.backcube.economyapp.domain.utils.toCurrencyIsoCode
import java.time.Instant

data class AccountResponseApiModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatApiModel>,
    val expenseStats: List<StatApiModel>,
    val createdAt: String,
    val updatedAt: String
)

fun AccountResponseApiModel.toDomain() = AccountResponseModel(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency.toCurrencyIsoCode(),
    incomeStats = incomeStats.map { it.toDomain() },
    expenseStats = expenseStats.map { it.toDomain() },
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)