package com.backcube.economyapp.data.remote.models.request.transactions

import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel

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