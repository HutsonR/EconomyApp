package com.backcube.data.remote.impl.datasources

import com.backcube.data.remote.api.TransactionsRemoteDataSource
import com.backcube.data.remote.impl.api.TransactionsApi
import com.backcube.data.remote.impl.models.request.transactions.TransactionRequestApiModel
import com.backcube.data.remote.impl.models.response.transactions.TransactionApiModel
import com.backcube.data.remote.impl.models.response.transactions.TransactionResponseApiModel
import com.backcube.data.remote.impl.utils.getOrThrow
import com.backcube.data.remote.impl.utils.retry.RetryHandler
import javax.inject.Inject

class TransactionsRemoteDataSourceImpl @Inject constructor(
    private val transactionsApi: TransactionsApi,
    private val retryHandler: RetryHandler
) : TransactionsRemoteDataSource {

    override suspend fun createTransaction(request: TransactionRequestApiModel): TransactionApiModel =
        retryHandler.executeWithRetry {
            transactionsApi.createTransaction(request).getOrThrow()
        }

    override suspend fun getTransactionById(id: Int): TransactionResponseApiModel =
        retryHandler.executeWithRetry {
            this@TransactionsRemoteDataSourceImpl.transactionsApi.getTransactionById(id).getOrThrow()
        }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestApiModel
    ): TransactionResponseApiModel =
        retryHandler.executeWithRetry {
            transactionsApi.updateTransaction(id, request).getOrThrow()
        }

    override suspend fun deleteTransaction(id: Int): Boolean =
        retryHandler.executeWithRetry {
            transactionsApi.deleteTransaction(id).isSuccessful
        }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseApiModel> =
        retryHandler.executeWithRetry {
            transactionsApi.getAccountTransactions(accountId, startDate, endDate).getOrThrow()
        }
}
