package com.backcube.data.remote.impl.repositories.impl

import com.backcube.data.remote.api.TransactionsRemoteDataSource
import com.backcube.data.remote.impl.models.request.transactions.toApiModel
import com.backcube.data.remote.impl.models.response.transactions.toDomain
import com.backcube.data.remote.impl.repositories.mappers.toApiDate
import com.backcube.domain.models.transactions.TransactionModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import com.backcube.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionsRemoteDataSource: TransactionsRemoteDataSource
) : TransactionRepository {

    override suspend fun createTransaction(request: TransactionRequestModel): Flow<TransactionModel> = flow {
        emit(transactionsRemoteDataSource.createTransaction(request.toApiModel()).toDomain())
    }

    override suspend fun getTransactionById(id: Int): Flow<TransactionResponseModel> = flow {
        emit(transactionsRemoteDataSource.getTransactionById(id).toDomain())
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): Flow<TransactionResponseModel> = flow {
        emit(transactionsRemoteDataSource.updateTransaction(id, request.toApiModel()).toDomain())
    }

    override suspend fun deleteTransaction(id: Int): Flow<Boolean> = flow {
        emit(transactionsRemoteDataSource.deleteTransaction(id))
    }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): Flow<List<TransactionResponseModel>> = flow {
        emit(
            transactionsRemoteDataSource.getAccountTransactions(
                accountId,
                startDate?.toApiDate(),
                endDate?.toApiDate()
            ).map { it.toDomain() }
        )
    }
}