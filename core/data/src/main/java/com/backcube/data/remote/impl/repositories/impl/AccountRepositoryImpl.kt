package com.backcube.data.remote.impl.repositories.impl

import com.backcube.data.remote.api.AccountRemoteDataSource
import com.backcube.data.remote.impl.models.request.accounts.toApi
import com.backcube.data.remote.impl.models.response.accounts.toDomain
import com.backcube.domain.models.accounts.AccountCreateRequestModel
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun getAccounts(): Flow<List<AccountModel>> = flow {
        emit(accountRemoteDataSource.getAccounts().map { it.toDomain() })
    }

    override suspend fun createAccount(request: AccountCreateRequestModel): Flow<AccountModel> = flow {
        emit(accountRemoteDataSource.createAccount(request.toApi()).toDomain())
    }

    override suspend fun getAccountById(id: Int): Flow<AccountResponseModel?> = flow {
        emit(accountRemoteDataSource.getAccountById(id)?.toDomain())
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): Flow<AccountModel> = flow {
        emit(accountRemoteDataSource.updateAccount(id, request.toApi()).toDomain())
    }

    override suspend fun getAccountHistory(id: Int): Flow<AccountHistoryResponseModel> = flow {
        emit(accountRemoteDataSource.getAccountHistory(id).toDomain())
    }
}