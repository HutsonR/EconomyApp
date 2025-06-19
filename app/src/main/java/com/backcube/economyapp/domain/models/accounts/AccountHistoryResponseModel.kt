package com.backcube.economyapp.domain.models.accounts

import com.backcube.economyapp.domain.utils.CurrencyIsoCode
import java.math.BigDecimal

data class AccountHistoryResponseModel(
    val accountId: Int,
    val accountName: String,
    val currency: CurrencyIsoCode,
    val currentBalance: BigDecimal,
    val history: List<AccountHistoryModel>
)