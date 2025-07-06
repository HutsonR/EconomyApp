package com.backcube.economyapp.domain.usecases.impl

import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import com.backcube.economyapp.domain.repositories.TransactionRepository
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.domain.utils.retry.RetryHandler
import java.time.Instant
import javax.inject.Inject

class TransactionUseCaseImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val retryHandler: RetryHandler
): TransactionUseCase {

    override suspend fun createTransaction(request: TransactionRequestModel): Result<TransactionResponseModel> {
        return retryHandler.executeWithRetryResult {
            transactionRepository.createTransaction(request)
        }
    }

    override suspend fun getTransactionById(id: Int): Result<TransactionResponseModel> {
        return retryHandler.executeWithRetryResult {
            transactionRepository.getTransactionById(id)
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): Result<TransactionResponseModel> {
        return retryHandler.executeWithRetryResult {
            transactionRepository.updateTransaction(id, request)
        }
    }

    override suspend fun deleteTransaction(id: Int): Result<Boolean> {
        return retryHandler.executeWithRetryResult {
            transactionRepository.deleteTransaction(id)
        }
    }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): Result<List<TransactionResponseModel>> {
        return retryHandler.executeWithRetryResult {
            transactionRepository.getAccountTransactions(accountId, startDate, endDate)
        }
    }

}