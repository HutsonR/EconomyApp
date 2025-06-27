package com.backcube.economyapp.data.remote.models.response.transactions

import com.backcube.economyapp.data.remote.models.response.accounts.AccountBriefApiModel
import com.backcube.economyapp.data.remote.models.response.accounts.toDomain
import com.backcube.economyapp.data.remote.models.response.categories.CategoryApiModel
import com.backcube.economyapp.data.remote.models.response.categories.toDomain
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import java.time.Instant

/**
 * Ответ API на запрос транзакции (с вложенными объектами счета и категории)
 *
 * @property id ID транзакции
 * @property account Информация о счете
 * @property category Информация о категории
 * @property amount Сумма транзакции
 * @property transactionDate Дата транзакции
 * @property comment Комментарий (если есть)
 * @property createdAt Дата создания
 * @property updatedAt Дата последнего обновления
 */
data class TransactionResponseApiModel(
    val id: Int,
    val account: AccountBriefApiModel,
    val category: CategoryApiModel,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
)

fun TransactionResponseApiModel.toDomain() = TransactionResponseModel(
    id = id,
    account = account.toDomain(),
    category = category.toDomain(),
    amount = amount.toBigDecimal(),
    transactionDate = Instant.parse(transactionDate),
    comment = comment,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)