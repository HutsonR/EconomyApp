package com.backcube.transactions.presentation.list.models

import com.backcube.domain.models.transactions.TransactionResponseModel
import java.math.BigDecimal
import java.time.Instant

data class TransactionState(
    val isLoading: Boolean = false,
    val items: List<TransactionResponseModel> = emptyList(),
    val totalSum: BigDecimal = BigDecimal(0),
    val transactionStartDate: Instant = Instant.now(),
    val transactionEndDate: Instant = Instant.now()
)