package com.backcube.economyapp.data.remote.repositories.impl

import com.backcube.economyapp.data.remote.api.TransactionsApi
import com.backcube.economyapp.data.remote.models.request.transactions.toApiModel
import com.backcube.economyapp.data.remote.models.response.transactions.toDomain
import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import com.backcube.economyapp.domain.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionsApi: TransactionsApi
): TransactionRepository {
    override suspend fun createTransaction(request: TransactionRequestModel): TransactionResponseModel = withContext(Dispatchers.IO) {
        transactionsApi.createTransaction(request.toApiModel()).toDomain()
    }

    override suspend fun getTransactionById(id: Int): TransactionResponseModel = withContext(Dispatchers.IO) {
        transactionsApi.getTransactionById(id).toDomain()
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): TransactionResponseModel = withContext(Dispatchers.IO) {
        transactionsApi.updateTransaction(id, request.toApiModel()).toDomain()
    }

    override suspend fun deleteTransaction(id: Int): Boolean = withContext(Dispatchers.IO) {
        transactionsApi.deleteTransaction(id).isSuccessful
    }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseModel> = withContext(Dispatchers.IO) {
       transactionsApi.getAccountTransactions(accountId, startDate, endDate).map { it.toDomain() }
    }

}