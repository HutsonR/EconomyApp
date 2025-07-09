package com.backcube.domain.models.accounts

import java.math.BigDecimal

data class StatModel(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: BigDecimal
)
