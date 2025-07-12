package com.backcube.economyapp.data.remote.repositories.impl

import com.backcube.economyapp.data.remote.api.TransactionsApi
import com.backcube.economyapp.data.remote.models.request.transactions.toApiModel
import com.backcube.economyapp.data.remote.models.response.transactions.toDomain
import com.backcube.economyapp.data.remote.repositories.mappers.toApiDate
import com.backcube.economyapp.data.remote.utils.getOrThrow
import com.backcube.economyapp.data.remote.utils.retry.RetryHandler
import com.backcube.economyapp.domain.models.transactions.TransactionModel
import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import com.backcube.economyapp.domain.repositories.TransactionRepository
import java.time.Instant
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionsApi: TransactionsApi,
    private val retryHandler: RetryHandler,
): TransactionRepository {
    override suspend fun createTransaction(request: TransactionRequestModel): TransactionModel =
        retryHandler.executeWithRetry {
            transactionsApi.createTransaction(request.toApiModel()).getOrThrow().toDomain()
        }

    override suspend fun getTransactionById(id: Int): TransactionResponseModel =
        retryHandler.executeWithRetry {
            transactionsApi.getTransactionById(id).getOrThrow().toDomain()
        }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): TransactionResponseModel =
        retryHandler.executeWithRetry {
            transactionsApi.updateTransaction(id, request.toApiModel()).getOrThrow().toDomain()
        }

    override suspend fun deleteTransaction(id: Int): Boolean =
        retryHandler.executeWithRetry {
            transactionsApi.deleteTransaction(id).isSuccessful
        }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): List<TransactionResponseModel> =
        retryHandler.executeWithRetry {
           transactionsApi.getAccountTransactions(
               accountId,
               startDate?.toApiDate(),
               endDate?.toApiDate()
           ).getOrThrow().map { it.toDomain() }
        }

}