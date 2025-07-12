package com.backcube.economyapp.domain.usecases.impl

import com.backcube.economyapp.domain.models.transactions.TransactionModel
import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import com.backcube.economyapp.domain.repositories.TransactionRepository
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import java.time.Instant
import javax.inject.Inject

class TransactionUseCaseImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
): TransactionUseCase {

    override suspend fun createTransaction(request: TransactionRequestModel): Result<TransactionModel> =
        runCatching {
            transactionRepository.createTransaction(request)
        }

    override suspend fun getTransactionById(id: Int): Result<TransactionResponseModel> =
        runCatching {
            transactionRepository.getTransactionById(id)
        }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): Result<TransactionResponseModel> =
        runCatching {
            transactionRepository.updateTransaction(id, request)
        }

    override suspend fun deleteTransaction(id: Int): Result<Boolean> =
        runCatching {
            transactionRepository.deleteTransaction(id)
        }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): Result<List<TransactionResponseModel>> =
        runCatching {
            transactionRepository.getAccountTransactions(accountId, startDate, endDate)
        }

}