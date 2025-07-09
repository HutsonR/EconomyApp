package com.backcube.domain.models.accounts

import java.math.BigDecimal

data class AccountStateModel(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val currency: CurrencyIsoCode
)