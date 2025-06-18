package com.backcube.economyapp.domain.models.accounts

import com.backcube.economyapp.domain.utils.CurrencyIsoCode
import java.math.BigDecimal
import java.time.Instant

data class AccountResponseModel(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val currency: CurrencyIsoCode,
    val incomeStats: List<StatModel>,
    val expenseStats: List<StatModel>,
    val createdAt: Instant,
    val updatedAt: Instant
)
