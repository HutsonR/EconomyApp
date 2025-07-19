package com.backcube.data.common.repositories.impl

import com.backcube.data.common.repositories.mappers.toApiDate
import com.backcube.data.common.utils.notSameContentWith
import com.backcube.data.local.api.TransactionsLocalDataSource
import com.backcube.data.remote.api.TransactionsRemoteDataSource
import com.backcube.data.remote.impl.models.request.transactions.toApiModel
import com.backcube.data.remote.impl.models.response.transactions.toDomain
import com.backcube.domain.models.transactions.TransactionModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import com.backcube.domain.repositories.TransactionRepository
import com.backcube.domain.utils.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionsLocalDataSource: TransactionsLocalDataSource,
    private val transactionsRemoteDataSource: TransactionsRemoteDataSource,
    private val connectivityObserver: ConnectivityObserver
) : TransactionRepository {

    override suspend fun createTransaction(request: TransactionRequestModel): Flow<TransactionModel> = flow {
        emit(transactionsRemoteDataSource.createTransaction(request.toApiModel()).toDomain())
    }

    override suspend fun getTransactionById(id: Int): Flow<TransactionResponseModel> = flow {
        val cachedCategories = transactionsLocalDataSource.getTransactionByAccount(id).also {
            emit(it)
        }

        if (!connectivityObserver.isInternetAvailable()) return@flow
        val remoteCategories = transactionsRemoteDataSource.getTransactionById(id).toDomain()
        if (cachedCategories != remoteCategories) {
            transactionsLocalDataSource.insertTransaction(remoteCategories)
            emit(remoteCategories)
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): Flow<TransactionResponseModel> = flow {
        transactionsRemoteDataSource.updateTransaction(id, request.toApiModel()).toDomain().let {
            transactionsLocalDataSource.updateTransaction(it)
            emit(it)
        }
    }

    override suspend fun deleteTransaction(id: Int): Flow<Boolean> = flow {
        transactionsLocalDataSource.deleteTransaction(id)
        emit(transactionsRemoteDataSource.deleteTransaction(id))
    }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): Flow<List<TransactionResponseModel>> = flow {
        val cachedCategories = transactionsLocalDataSource.getTransactionsByPeriod(
            accountId,
            startDate?.toApiDate(),
            endDate?.toApiDate()
        ).also {
            emit(it)
        }

        if (!connectivityObserver.isInternetAvailable()) return@flow
        val remoteCategories = transactionsRemoteDataSource.getAccountTransactions(
            accountId,
            startDate?.toApiDate(),
            endDate?.toApiDate()
        ).map { it.toDomain() }
        if (cachedCategories notSameContentWith remoteCategories) {
            transactionsLocalDataSource.insertTransactions(remoteCategories)
            emit(remoteCategories)
        }
    }
}