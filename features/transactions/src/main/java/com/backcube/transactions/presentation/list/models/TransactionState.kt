package com.backcube.economyapp.features.transactions.presentation.list.store.models

import com.backcube.domain.models.transactions.TransactionResponseModel
import java.math.BigDecimal
import java.time.Instant

data class TransactionState(
    val isLoading: Boolean = false,
    val items: List<TransactionResponseModel> = emptyList(),
    val totalSum: BigDecimal = BigDecimal(0),
    val incomeDate: Instant = Instant.now()
)