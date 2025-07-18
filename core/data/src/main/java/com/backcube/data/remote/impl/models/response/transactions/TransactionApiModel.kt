package com.backcube.data.remote.impl.models.response.transactions

import com.backcube.domain.models.transactions.TransactionModel
import java.time.Instant

/**
 * Модель транзакции
 *
 * @property id ID транзакции
 * @property accountId ID счета
 * @property categoryId ID категории
 * @property amount Сумма
 * @property transactionDate Дата транзакции (YYYY-MM-DD)
 * @property comment Комментарий (необязательный)
 * @property createdAt Дата создания
 * @property updatedAt Дата обновления
 */
data class TransactionApiModel(
    val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)

fun TransactionApiModel.toDomain() = TransactionModel(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.toBigDecimal(),
    transactionDate = Instant.parse(transactionDate),
    comment = comment,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)