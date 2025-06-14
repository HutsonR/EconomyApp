package com.backcube.economyapp.features.expenses.store.models

import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import java.math.BigDecimal

data class ExpenseState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val items: List<TransactionResponseModel> = emptyList(),
    val totalSum: BigDecimal = BigDecimal(0)
)
