package com.backcube.economyapp.data.remote.models.response.transactions

import com.backcube.economyapp.domain.models.transactions.TransactionModel
import java.time.Instant

data class TransactionApiModel(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)

fun TransactionApiModel.toDomain() = TransactionModel(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toBigDecimal(),
    transactionDate = Instant.parse(transactionDate),
    comment = comment,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)