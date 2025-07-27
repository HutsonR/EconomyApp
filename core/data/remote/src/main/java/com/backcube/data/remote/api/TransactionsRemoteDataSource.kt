package com.backcube.data.remote.api

import com.backcube.data.remote.impl.models.request.transactions.TransactionRequestApiModel
import com.backcube.data.remote.impl.models.response.transactions.TransactionApiModel
import com.backcube.data.remote.impl.models.response.transactions.TransactionResponseApiModel

interface TransactionsRemoteDataSource {
    suspend fun createTransaction(request: TransactionRequestApiModel): TransactionApiModel
    suspend fun getTransactionById(id: Int): TransactionResponseApiModel
    suspend fun updateTransaction(id: Int, request: TransactionRequestApiModel): TransactionResponseApiModel
    suspend fun deleteTransaction(id: Int): Boolean
    suspend fun getAccountTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseApiModel>
}
