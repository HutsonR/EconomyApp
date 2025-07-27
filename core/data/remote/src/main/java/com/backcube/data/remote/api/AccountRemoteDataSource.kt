package com.backcube.data.remote.api

import com.backcube.data.remote.impl.models.request.accounts.AccountCreateRequestApiModel
import com.backcube.data.remote.impl.models.request.accounts.AccountUpdateRequestApiModel
import com.backcube.data.remote.impl.models.response.accounts.AccountApiModel
import com.backcube.data.remote.impl.models.response.accounts.AccountHistoryResponseApiModel
import com.backcube.data.remote.impl.models.response.accounts.AccountResponseApiModel

interface AccountRemoteDataSource {
    suspend fun getAccounts(): List<AccountApiModel>
    suspend fun createAccount(request: AccountCreateRequestApiModel): AccountApiModel
    suspend fun getAccountById(id: Int): AccountResponseApiModel?
    suspend fun updateAccount(id: Int, request: AccountUpdateRequestApiModel): AccountApiModel
    suspend fun getAccountHistory(id: Int): AccountHistoryResponseApiModel
}