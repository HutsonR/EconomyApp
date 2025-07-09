package com.backcube.domain.models.accounts

import java.math.BigDecimal

data class AccountHistoryResponseModel(
    val accountId: Int,
    val accountName: String,
    val currency: CurrencyIsoCode,
    val currentBalance: BigDecimal,
    val history: List<AccountHistoryModel>
)