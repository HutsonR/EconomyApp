package com.backcube.economyapp.domain.models.accounts

import java.math.BigDecimal

data class AccountHistoryResponseModel(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: BigDecimal,
    val history: List<AccountHistoryModel>
)