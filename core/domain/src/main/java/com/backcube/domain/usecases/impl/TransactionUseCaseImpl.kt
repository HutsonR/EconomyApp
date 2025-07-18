package com.backcube.domain.usecases.impl

import com.backcube.domain.models.transactions.TransactionModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import com.backcube.domain.repositories.TransactionRepository
import com.backcube.domain.usecases.api.TransactionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class TransactionUseCaseImpl @Inject constructor(
    private val transactionRepository: TransactionRepository
): TransactionUseCase {

    override suspend fun createTransaction(request: TransactionRequestModel): Flow<Result<TransactionModel>> =
        transactionRepository.createTransaction(request)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun getTransactionById(id: Int): Flow<Result<TransactionResponseModel>> =
        transactionRepository.getTransactionById(id)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): Flow<Result<TransactionResponseModel>> =
        transactionRepository.updateTransaction(id, request)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun deleteTransaction(id: Int): Flow<Result<Boolean>> =
        transactionRepository.deleteTransaction(id)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): Flow<Result<List<TransactionResponseModel>>> =
        transactionRepository.getAccountTransactions(accountId, startDate, endDate)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

}