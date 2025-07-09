package com.backcube.domain.models.transactions

import java.math.BigDecimal
import java.time.Instant

data class TransactionModel(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: BigDecimal,
    val transactionDate: Instant,
    val comment: String?,
    val createdAt: Instant,
    val updatedAt: Instant
)