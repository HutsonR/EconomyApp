package com.backcube.economyapp.domain.models.accounts

import java.math.BigDecimal

data class AccountUpdateRequestModel(
    val name: String,
    val balance: BigDecimal,
    val currency: String
)