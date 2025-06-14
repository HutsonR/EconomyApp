package com.backcube.economyapp.domain.models.accounts

import java.time.Instant

data class AccountHistoryModel(
    val id: Int,
    val accountId: Long,
    val changeType: String,
    val previousState: AccountStateModel?,
    val newState: AccountStateModel,
    val changeTimestamp: Instant,
    val createdAt: Instant

)
