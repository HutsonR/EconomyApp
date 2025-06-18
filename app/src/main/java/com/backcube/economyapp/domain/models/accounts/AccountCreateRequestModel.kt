package com.backcube.economyapp.domain.models.accounts

import com.backcube.economyapp.domain.utils.CurrencyIsoCode
import java.math.BigDecimal

data class AccountCreateRequestModel(
    val name: String,
    val balance: BigDecimal,
    val currency: CurrencyIsoCode
)