package com.backcube.data.local.impl.entities.accounts

import com.backcube.domain.models.accounts.AccountHistoryModel
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class HistoryModelEntity(
    val id: Int,
    val accountId: Long,
    val changeType: String,
    val previousState: AccountStateEntity?,
    val newState: AccountStateEntity,
    val changeTimestamp: String,
    val createdAt: String
) {
    internal fun toDomain() = AccountHistoryModel(
        id = id,
        accountId = accountId,
        changeType = changeType,
        previousState = previousState?.toDomain(),
        newState = newState.toDomain(),
        changeTimestamp = Instant.parse(changeTimestamp),
        createdAt = Instant.parse(createdAt)
    )
    companion object {
        internal fun toEntity(accountHistoryModel: AccountHistoryModel) = HistoryModelEntity(
            id = accountHistoryModel.id,
            accountId = accountHistoryModel.accountId,
            changeType = accountHistoryModel.changeType,
            previousState = AccountStateEntity.toEntity(accountHistoryModel.previousState),
            newState = AccountStateEntity.toEntity(accountHistoryModel.newState)!!,
            changeTimestamp = accountHistoryModel.changeTimestamp.toString(),
            createdAt = accountHistoryModel.createdAt.toString()
        )
    }
}