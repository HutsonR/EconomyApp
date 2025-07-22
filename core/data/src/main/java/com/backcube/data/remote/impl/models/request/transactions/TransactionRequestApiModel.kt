package com.backcube.data.remote.impl.models.request.transactions

import com.backcube.domain.models.sync.SyncEntityType
import com.backcube.domain.models.sync.SyncOperationType
import com.backcube.domain.models.sync.SyncQueueModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Модель запроса на создание или обновление транзакции
 *
 * @property accountId ID счета, к которому относится транзакция
 * @property categoryId ID категории (доход/расход)
 * @property amount Сумма транзакции
 * @property transactionDate Дата транзакции (формат YYYY-MM-DD)
 * @property comment Комментарий к транзакции (необязательный)
 */
@Serializable
data class TransactionRequestApiModel(
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?
)

fun TransactionRequestModel.toApiModel() = TransactionRequestApiModel(
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toPlainString(),
    transactionDate = transactionDate.toString(),
    comment = comment
)

fun TransactionRequestApiModel.mapTransactionToSyncCreate(transactionId: Int): SyncQueueModel {
    return SyncQueueModel(
        id = transactionId,
        operation = SyncOperationType.CREATE,
        entityType = SyncEntityType.TRANSACTION,
        targetId = null,
        payload = Json.encodeToString(this)
    )
}