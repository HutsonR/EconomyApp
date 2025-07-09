package com.backcube.domain.models.accounts

import java.math.BigDecimal

data class AccountCreateRequestModel(
    val name: String,
    val balance: BigDecimal,
    val currency: CurrencyIsoCode
)