package com.backcube.economyapp.domain.models.accounts

import com.backcube.economyapp.domain.utils.CurrencyIsoCode
import java.math.BigDecimal
import java.time.Instant

data class AccountModel(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: BigDecimal,
    val currency: CurrencyIsoCode,
    val createdAt: Instant,
    val updatedAt: Instant
)