package com.backcube.economyapp.data.remote.models.request.transactions

import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel

/**
 * Модель запроса на создание или обновление транзакции
 *
 * @property accountId ID счета, к которому относится транзакция
 * @property categoryId ID категории (доход/расход)
 * @property amount Сумма транзакции
 * @property transactionDate Дата транзакции (формат YYYY-MM-DD)
 * @property comment Комментарий к транзакции (необязательный)
 */
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