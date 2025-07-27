package com.backcube.data.remote.impl.datasources

import com.backcube.data.remote.api.AccountRemoteDataSource
import com.backcube.data.remote.impl.api.AccountApi
import com.backcube.data.remote.impl.models.request.accounts.AccountCreateRequestApiModel
import com.backcube.data.remote.impl.models.request.accounts.AccountUpdateRequestApiModel
import com.backcube.data.remote.impl.models.response.accounts.AccountApiModel
import com.backcube.data.remote.impl.models.response.accounts.AccountHistoryResponseApiModel
import com.backcube.data.remote.impl.models.response.accounts.AccountResponseApiModel
import com.backcube.data.remote.impl.utils.getOrThrow
import com.backcube.data.remote.impl.utils.retry.RetryHandler
import javax.inject.Inject

internal class AccountRemoteDataSourceImpl @Inject constructor(
    private val accountApi: AccountApi,
    private val retryHandler: RetryHandler
) : AccountRemoteDataSource {

    override suspend fun getAccounts(): List<AccountApiModel> =
        retryHandler.executeWithRetry {
            accountApi.getAccounts().getOrThrow()
        }

    override suspend fun createAccount(request: AccountCreateRequestApiModel): AccountApiModel =
        retryHandler.executeWithRetry {
            accountApi.createAccount(request).getOrThrow()
        }

    override suspend fun getAccountById(id: Int): AccountResponseApiModel? =
        retryHandler.executeWithRetry {
            accountApi.getAccountById(id).getOrThrow()
        }

    override suspend fun updateAccount(id: Int, request: AccountUpdateRequestApiModel): AccountApiModel =
        retryHandler.executeWithRetry {
            accountApi.updateAccount(id, request).getOrThrow()
        }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseApiModel =
        retryHandler.executeWithRetry {
            accountApi.getAccountHistory(id).getOrThrow()
        }
}
