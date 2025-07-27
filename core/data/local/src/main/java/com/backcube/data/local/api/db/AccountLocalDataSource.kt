package com.backcube.data.local.api.db

import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel

interface AccountLocalDataSource {
    suspend fun getAccounts(): List<AccountModel>
    suspend fun getAccountById(id: Int): AccountResponseModel?
    suspend fun insertAccounts(accounts: List<AccountModel>)
    suspend fun insertAccountDetails(account: AccountResponseModel)
    suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel?
    suspend fun insertAccountHistory(account: AccountHistoryResponseModel)
    suspend fun clearAccounts()
    suspend fun clearAccountDetails()
}
