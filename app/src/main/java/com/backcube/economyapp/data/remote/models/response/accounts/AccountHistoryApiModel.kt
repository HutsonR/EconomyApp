package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountHistoryModel
import java.time.Instant

data class AccountHistoryApiModel(
    val id: Int,
    val accountId: Long,
    val changeType: String,
    val previousState: AccountStateApiModel?,
    val newState: AccountStateApiModel,
    val changeTimestamp: String,
    val createdAt: String
)

fun AccountHistoryApiModel.toDomain() = AccountHistoryModel(
    id = id,
    accountId = accountId,
    changeType = changeType,
    previousState = previousState?.toDomain(),
    newState = newState.toDomain(),
    changeTimestamp = Instant.parse(changeTimestamp),
    createdAt = Instant.parse(createdAt)
)