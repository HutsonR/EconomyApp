package com.backcube.transactions.presentation.analyze.domain.models

import java.math.BigDecimal

data class CategoryStats(
    val id: Int,
    val name: String,
    val emoji: String,
    val percent: Float,
    val totalAmount: BigDecimal
)