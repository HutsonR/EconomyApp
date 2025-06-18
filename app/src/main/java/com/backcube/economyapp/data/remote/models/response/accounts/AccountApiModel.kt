package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.utils.toCurrencyIsoCode
import java.time.Instant

data class AccountApiModel(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)

fun AccountApiModel.toDomain() = AccountModel(
    id = id,
    userId = userId,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency.toCurrencyIsoCode(),
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)