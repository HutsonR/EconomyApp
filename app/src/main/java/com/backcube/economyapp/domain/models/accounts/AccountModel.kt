package com.backcube.economyapp.domain.models.accounts

import java.math.BigDecimal
import java.time.Instant

data class AccountModel(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: BigDecimal,
    val currency: String,
    val createdAt: Instant,
    val updatedAt: Instant
)