package com.backcube.data.local.impl.datasources

import com.backcube.data.local.api.TransactionsLocalDataSource
import com.backcube.data.local.impl.dao.TransactionDao
import com.backcube.data.local.impl.entities.transactions.TransactionResponseEntity
import com.backcube.domain.models.transactions.TransactionResponseModel
import java.time.Instant
import javax.inject.Inject

class TransactionsLocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionDao
): TransactionsLocalDataSource {
    override suspend fun getAllTransactions(): List<TransactionResponseModel> {
        return transactionDao.getAllResponses().map { it.toDomain() }
    }

    override suspend fun getTransactionByAccount(accountId: Int): TransactionResponseModel {
        return transactionDao.getResponseById(accountId).toDomain()
    }

    override suspend fun getTransactionsByPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseModel> {
        return transactionDao.getResponsesByAccountAndPeriod(
            accountId,
            startDate ?: Instant.now().toString(),
            endDate ?: Instant.now().toString()
        ).map { it.toDomain() }
    }

    override suspend fun insertTransactions(transactions: List<TransactionResponseModel>) {
        transactionDao.insertResponses(transactions.map { TransactionResponseEntity.toEntity(it) })
    }

    override suspend fun insertTransaction(transaction: TransactionResponseModel) {
        transactionDao.insertResponse(TransactionResponseEntity.toEntity(transaction))
    }

    override suspend fun deleteTransaction(id: Int) {
        transactionDao.deleteResponseById(id)
    }

    override suspend fun updateTransaction(transaction: TransactionResponseModel) {
        transactionDao.insertResponse(TransactionResponseEntity.toEntity(transaction))
    }

}