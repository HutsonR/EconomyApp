package com.backcube.data.remote.repositories.impl

import com.backcube.data.remote.api.AccountApi
import com.backcube.data.remote.models.request.accounts.toApi
import com.backcube.data.remote.models.response.accounts.toDomain
import com.backcube.data.remote.utils.getOrThrow
import com.backcube.data.remote.utils.retry.RetryHandler
import com.backcube.domain.models.accounts.AccountCreateRequestModel
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.domain.repositories.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi,
    private val retryHandler: RetryHandler,
) : AccountRepository {
    override suspend fun getAccounts(): List<AccountModel> =
        retryHandler.executeWithRetry {
            accountApi.getAccounts().getOrThrow().map { it.toDomain() }
        }

    override suspend fun createAccount(request: AccountCreateRequestModel): AccountModel =
        retryHandler.executeWithRetry {
            accountApi.createAccount(request.toApi()).getOrThrow().toDomain()
        }

    override suspend fun getAccountById(id: Int): AccountResponseModel? =
        retryHandler.executeWithRetry {
            accountApi.getAccountById(id).getOrThrow()?.toDomain()
        }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): AccountModel =
        retryHandler.executeWithRetry {
            accountApi.updateAccount(id, request.toApi()).getOrThrow().toDomain()
        }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel =
        retryHandler.executeWithRetry {
           accountApi.getAccountHistory(id).getOrThrow().toDomain()
        }
}