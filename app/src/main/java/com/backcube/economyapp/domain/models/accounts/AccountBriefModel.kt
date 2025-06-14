package com.backcube.economyapp.domain.models.accounts

import java.math.BigDecimal

data class AccountBriefModel(
    val id: Int,
    val name: String,
    val balance: BigDecimal,
    val currency: String
)