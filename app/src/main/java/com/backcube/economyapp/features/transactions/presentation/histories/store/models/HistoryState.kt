package com.backcube.economyapp.features.transactions.presentation.histories.store.models

import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import java.math.BigDecimal
import java.time.Instant

data class HistoryState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val items: List<TransactionResponseModel> = emptyList(),
    val isIncome: Boolean = false,
    val startHistoryDate: Instant = Instant.now(),
    val endHistoryDate: Instant = Instant.now(),
    val totalSum: BigDecimal = BigDecimal(0)
)
