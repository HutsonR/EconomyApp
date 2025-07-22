package com.backcube.domain.models.accounts

import com.backcube.domain.utils.CurrencyIsoCode
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

fun AccountResponseModel.mapToAccountBrief(): AccountBriefModel =
    AccountBriefModel(
        id = this.id,
        name = this.name,
        balance = this.balance,
        currency = this.currency
    )