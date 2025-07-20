package com.backcube.data.local.api

import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel

interface AccountLocalDataSource {
    suspend fun getAccounts(): List<AccountModel>
    suspend fun getAccountById(id: Int): AccountResponseModel?
    suspend fun insertAccounts(accounts: List<AccountModel>)
    suspend fun insertAccountDetails(account: AccountResponseModel)
    suspend fun clearAccounts()
    suspend fun clearAccountDetails()
}
