package com.backcube.data.common.repositories.impl

import com.backcube.data.common.repositories.mappers.toApiDate
import com.backcube.data.common.utils.notSameContentWith
import com.backcube.data.local.api.AccountLocalDataSource
import com.backcube.data.local.api.CategoriesLocalDataSource
import com.backcube.data.local.api.SyncLocalDataSource
import com.backcube.data.local.api.TransactionsLocalDataSource
import com.backcube.data.local.mapper.sync.mapToSyncTransactionDelete
import com.backcube.data.local.mapper.sync.toFakeTransactionModel
import com.backcube.data.local.mapper.sync.toFakeTransactionResponseModel
import com.backcube.data.remote.api.TransactionsRemoteDataSource
import com.backcube.data.remote.impl.models.request.transactions.mapTransactionToSyncCreate
import com.backcube.data.remote.impl.models.request.transactions.toApiModel
import com.backcube.data.remote.impl.models.response.transactions.toDomain
import com.backcube.domain.models.accounts.mapToAccountBrief
import com.backcube.domain.models.transactions.TransactionModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import com.backcube.domain.repositories.TransactionRepository
import com.backcube.domain.utils.ConnectivityObserver
import com.backcube.domain.utils.NoInternetConnectionException
import java.time.Instant
import javax.inject.Inject

internal class TransactionRepositoryImpl @Inject constructor(
    private val transactionsLocalDataSource: TransactionsLocalDataSource,
    private val transactionsRemoteDataSource: TransactionsRemoteDataSource,
    private val categoriesLocalDataSource: CategoriesLocalDataSource,
    private val accountLocalDataSource: AccountLocalDataSource,
    private val syncLocalDataSource: SyncLocalDataSource,
    private val connectivityObserver: ConnectivityObserver
) : TransactionRepository {

    override suspend fun createTransaction(request: TransactionRequestModel): TransactionModel {
        return if (!connectivityObserver.isInternetAvailable()) {
            val accountBrief = accountLocalDataSource.getAccountById(request.accountId)?.mapToAccountBrief() ?: throw IllegalArgumentException("Account not found")
            val category = categoriesLocalDataSource.getCategoryById(request.categoryId) ?: throw IllegalArgumentException("Category not found")
            val fakeTransaction = request.toFakeTransactionResponseModel(
                account = accountBrief,
                category = category
            )
            transactionsLocalDataSource.insertTransaction(fakeTransaction)
            syncLocalDataSource.enqueueOperation(request.toApiModel().mapTransactionToSyncCreate(fakeTransaction.id))
            request.toFakeTransactionModel()
        } else {
            transactionsRemoteDataSource.createTransaction(request.toApiModel()).toDomain()
        }
    }

    override suspend fun getTransactionById(id: Int): TransactionResponseModel {
        val cachedData = transactionsLocalDataSource.getTransactionByAccount(id)
        return if (!connectivityObserver.isInternetAvailable()) {
            cachedData
        } else {
            val remoteCategories = transactionsRemoteDataSource.getTransactionById(id).toDomain()
            if (cachedData != remoteCategories) {
                transactionsLocalDataSource.insertTransaction(remoteCategories)
            }
            remoteCategories
        }
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): TransactionResponseModel {
        if (!connectivityObserver.isInternetAvailable()) throw NoInternetConnectionException()
        return transactionsRemoteDataSource.updateTransaction(id, request.toApiModel()).toDomain()
    }

    override suspend fun deleteTransaction(id: Int): Boolean {
        val isLocalDeleted = transactionsLocalDataSource.deleteTransaction(id)
        return if (!connectivityObserver.isInternetAvailable()) {
            syncLocalDataSource.enqueueOperation(mapToSyncTransactionDelete(id))
            isLocalDeleted
        } else {
            transactionsRemoteDataSource.deleteTransaction(id)
        }
    }

    override suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): List<TransactionResponseModel> {
        val cachedData = transactionsLocalDataSource.getTransactionsByPeriod(
            accountId,
            startDate?.toEpochMilli(),
            endDate?.toEpochMilli()
        )
        return if (!connectivityObserver.isInternetAvailable()) {
            cachedData
        } else {
            val remoteCategories = transactionsRemoteDataSource.getAccountTransactions(
                accountId,
                startDate?.toApiDate(),
                endDate?.toApiDate()
            ).map { it.toDomain() }
            if (cachedData notSameContentWith remoteCategories) {
                transactionsLocalDataSource.insertTransactions(remoteCategories)
            }
            remoteCategories
        }
    }
}