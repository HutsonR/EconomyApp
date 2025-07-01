package com.backcube.economyapp.domain.usecases.impl

import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import com.backcube.economyapp.domain.repositories.TransactionRepository
import com.backcube.economyapp.domain.utils.retry.RetryHandler
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import java.time.Instant
import javax.inject.Inject

class TransactionUseCaseImpl @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val retryHandler: RetryHandler
): TransactionUseCase {

    override suspend fun createTransaction(request: TransactionRequestModel): TransactionResponseModel {
        return retryHandler.executeWithRetry {
            transactionRepository.createTransaction(request)
        }
    }

    override suspend fun getTransactionById(id: Int): TransactionResponseModel {
        return retryHandler.executeWithRetry {
            transactionRepository.getTransactionById(id)
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): TransactionResponseModel {
        return retryHandler.executeWithRetry {
            transactionRepository.updateTransaction(id, request)
        }
    }

    override suspend fun deleteTransaction(id: Int): Boolean {
        return retryHandler.executeWithRetry {
            transactionRepository.deleteTransaction(id)
        }
    }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): List<TransactionResponseModel> {
        return retryHandler.executeWithRetry {
            transactionRepository.getAccountTransactions(accountId, startDate, endDate)
        }
    }

}