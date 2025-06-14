package com.backcube.economyapp.domain.models.transactions

import java.math.BigDecimal
import java.time.Instant

data class TransactionRequestModel(
    val accountId: Int,
    val categoryId: Int,
    val amount: BigDecimal,
    val transactionDate: Instant,
    val comment: String?
)