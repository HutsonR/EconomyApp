package com.backcube.data.remote.impl.models.response.accounts

import com.backcube.domain.models.accounts.AccountHistoryModel
import java.time.Instant

/**
 * Модель записи об изменении состояния счета
 *
 * @property id ID записи истории
 * @property accountId ID счета
 * @property changeType Тип изменения (например, "create", "update")
 * @property previousState Состояние счета до изменения (может отсутствовать)
 * @property newState Состояние счета после изменения
 * @property changeTimestamp Дата и время изменения
 * @property createdAt Дата создания записи в системе
 */
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