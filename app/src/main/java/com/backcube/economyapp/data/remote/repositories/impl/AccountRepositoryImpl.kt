package com.backcube.economyapp.data.remote.repositories.impl

import com.backcube.economyapp.data.remote.api.AccountApi
import com.backcube.economyapp.data.remote.models.request.accounts.toApi
import com.backcube.economyapp.data.remote.models.response.accounts.toDomain
import com.backcube.economyapp.data.remote.utils.getOrThrow
import com.backcube.economyapp.domain.models.accounts.AccountCreateRequestModel
import com.backcube.economyapp.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.models.accounts.AccountResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.economyapp.domain.qualifiers.IoDispatchers
import com.backcube.economyapp.domain.repositories.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi,
    @IoDispatchers private val dispatcher: CoroutineDispatcher,
) : AccountRepository {
    override suspend fun getAccounts(): List<AccountModel> = withContext(dispatcher) {
        accountApi.getAccounts().getOrThrow().map { it.toDomain() }
    }

    override suspend fun createAccount(request: AccountCreateRequestModel): AccountModel = withContext(dispatcher) {
        accountApi.createAccount(request.toApi()).getOrThrow().toDomain()
    }

    override suspend fun getAccountById(id: Int): AccountResponseModel? = withContext(dispatcher) {
        accountApi.getAccountById(id).getOrThrow()?.toDomain()
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): AccountModel = withContext(dispatcher) {
        accountApi.updateAccount(id, request.toApi()).getOrThrow().toDomain()
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel = withContext(dispatcher) {
       accountApi.getAccountHistory(id).getOrThrow().toDomain()
    }
}