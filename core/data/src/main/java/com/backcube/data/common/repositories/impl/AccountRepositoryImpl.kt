package com.backcube.data.common.repositories.impl

import com.backcube.data.common.utils.notSameContentWith
import com.backcube.data.local.api.AccountLocalDataSource
import com.backcube.data.remote.api.AccountRemoteDataSource
import com.backcube.data.remote.impl.models.request.accounts.toApi
import com.backcube.data.remote.impl.models.response.accounts.toDomain
import com.backcube.domain.models.accounts.AccountCreateRequestModel
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.domain.repositories.AccountRepository
import com.backcube.domain.utils.ConnectivityObserver
import com.backcube.domain.utils.NoInternetConnectionException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val connectivityObserver: ConnectivityObserver
) : AccountRepository {

    override suspend fun getAccounts(): Flow<List<AccountModel>> = flow {
        val cachedData = accountLocalDataSource.getAccounts().also {
            emit(it)
        }

        if (!connectivityObserver.isInternetAvailable()) return@flow
        val remoteData = accountRemoteDataSource.getAccounts().map { it.toDomain() }
        if (cachedData notSameContentWith remoteData) {
            accountLocalDataSource.insertAccounts(remoteData)
            emit(remoteData)
        }
    }

    // Заготовка на будущее. По ТЗ не нужно было
    override suspend fun createAccount(request: AccountCreateRequestModel): Flow<AccountModel> = flow {
        emit(accountRemoteDataSource.createAccount(request.toApi()).toDomain())
    }

    override suspend fun getAccountById(id: Int): Flow<AccountResponseModel?> = flow {
        val cachedData = accountLocalDataSource.getAccountById(id).also {
            emit(it)
        }

        if (!connectivityObserver.isInternetAvailable()) return@flow
        val remoteData = accountRemoteDataSource.getAccountById(id)?.toDomain()
        if (remoteData != null && cachedData != remoteData) {
            accountLocalDataSource.insertAccountDetails(remoteData)
            emit(remoteData)
        }
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): Flow<AccountModel> = flow {
        if (!connectivityObserver.isInternetAvailable()) throw NoInternetConnectionException()
        val updatedAccount = (accountRemoteDataSource.updateAccount(id, request.toApi())).toDomain()
        emit(updatedAccount)
    }

    override suspend fun getAccountHistory(id: Int): Flow<AccountHistoryResponseModel> = flow {
        emit(accountRemoteDataSource.getAccountHistory(id).toDomain())
    }
}