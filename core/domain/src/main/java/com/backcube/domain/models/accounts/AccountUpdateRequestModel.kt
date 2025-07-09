package com.backcube.domain.models.accounts

import java.math.BigDecimal

data class AccountUpdateRequestModel(
    val name: String,
    val balance: BigDecimal,
    val currency: CurrencyIsoCode
)