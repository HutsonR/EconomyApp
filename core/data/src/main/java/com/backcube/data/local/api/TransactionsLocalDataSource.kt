package com.backcube.data.local.api

import com.backcube.domain.models.transactions.TransactionResponseModel

interface TransactionsLocalDataSource {
    suspend fun getAllTransactions(): List<TransactionResponseModel>
    suspend fun getTransactionByAccount(accountId: Int): TransactionResponseModel
    suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: Long?,
        endDate: Long?
    ): List<TransactionResponseModel>
    suspend fun insertTransactions(transactions: List<TransactionResponseModel>)
    suspend fun insertTransaction(transaction: TransactionResponseModel)
    suspend fun deleteTransaction(id: Int): Boolean
}
