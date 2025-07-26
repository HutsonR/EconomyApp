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
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
    private val accountLocalDataSource: AccountLocalDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val connectivityObserver: ConnectivityObserver
) : AccountRepository {

    override suspend fun getAccounts(): List<AccountModel> {
        val cachedData = accountLocalDataSource.getAccounts()
        return if (!connectivityObserver.isInternetAvailable()) {
            cachedData
        } else {
            val remoteData = accountRemoteDataSource.getAccounts().map { it.toDomain() }
            if (cachedData notSameContentWith remoteData) {
                accountLocalDataSource.insertAccounts(remoteData)
            }
            remoteData
        }
    }

    // Заготовка на будущее. По ТЗ не нужно было
    override suspend fun createAccount(request: AccountCreateRequestModel): AccountModel {
        return accountRemoteDataSource.createAccount(request.toApi()).toDomain()
    }

    override suspend fun getAccountById(id: Int): AccountResponseModel? {
        val cachedData = accountLocalDataSource.getAccountById(id)
        return if (!connectivityObserver.isInternetAvailable()) {
            cachedData
        } else {
            val remoteData = accountRemoteDataSource.getAccountById(id)?.toDomain()
            if (remoteData != null && cachedData != remoteData) {
                accountLocalDataSource.insertAccountDetails(remoteData)
            }
            remoteData
        }
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): AccountModel {
        if (!connectivityObserver.isInternetAvailable()) throw NoInternetConnectionException()
        return accountRemoteDataSource.updateAccount(id, request.toApi()).toDomain()
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel? {
        val cachedData = accountLocalDataSource.getAccountHistory(id)
        return if (!connectivityObserver.isInternetAvailable()) {
            cachedData
        } else {
            val remoteData = accountRemoteDataSource.getAccountHistory(id).toDomain()
            if (cachedData != remoteData) {
                accountLocalDataSource.insertAccountHistory(remoteData)
            }
            remoteData
        }
    }
}