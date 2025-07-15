package com.backcube.transactions.presentation.analyze.models

import com.backcube.domain.utils.CurrencyIsoCode
import com.backcube.transactions.presentation.analyze.domain.models.CategoryStats
import java.time.Instant

data class AnalyzeState(
    val isLoading: Boolean = false,
    val items: List<CategoryStats> = emptyList<CategoryStats>(),
    val isIncome: Boolean = false,
    val startAnalyzeDate: Instant = Instant.now(),
    val endAnalyzeDate: Instant = Instant.now(),
    val totalSum: String = "",
    val currencyIsoCode: CurrencyIsoCode = CurrencyIsoCode.RUB
)
