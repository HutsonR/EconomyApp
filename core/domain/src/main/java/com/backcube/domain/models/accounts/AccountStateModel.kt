package com.backcube.domain.models.accounts

import com.backcube.domain.utils.CurrencyIsoCode
import java.math.BigDecimal

data class AccountStateModel(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val currency: CurrencyIsoCode
)