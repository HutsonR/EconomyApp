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
import com.backcube.economyapp.domain.repositories.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountApi: AccountApi
) : AccountRepository {
    override suspend fun getAccounts(): List<AccountModel> = withContext(Dispatchers.IO) {
        accountApi.getAccounts().getOrThrow().map { it.toDomain() }
    }

    override suspend fun createAccount(request: AccountCreateRequestModel): AccountModel = withContext(Dispatchers.IO) {
        accountApi.createAccount(request.toApi()).getOrThrow().toDomain()
    }

    override suspend fun getAccountById(id: Int): AccountResponseModel? = withContext(Dispatchers.IO) {
        accountApi.getAccountById(id).getOrThrow()?.toDomain()
    }

    override suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): AccountModel = withContext(Dispatchers.IO) {
        accountApi.updateAccount(id, request.toApi()).getOrThrow().toDomain()
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel = withContext(Dispatchers.IO) {
       accountApi.getAccountHistory(id).getOrThrow().toDomain()
    }
}