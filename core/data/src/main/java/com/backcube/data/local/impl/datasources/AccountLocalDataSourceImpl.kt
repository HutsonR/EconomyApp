package com.backcube.data.local.impl.datasources

import com.backcube.data.local.api.AccountLocalDataSource
import com.backcube.data.local.impl.dao.AccountDao
import com.backcube.data.local.impl.entities.accounts.AccountEntity
import com.backcube.data.local.impl.entities.accounts.AccountHistoryEntity
import com.backcube.data.local.impl.entities.accounts.AccountResponseEntity
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel
import javax.inject.Inject

internal class AccountLocalDataSourceImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountLocalDataSource {

    override suspend fun getAccounts(): List<AccountModel> {
        return accountDao.getAllAccounts().map { it.toDomain() }
    }

    override suspend fun getAccountById(id: Int): AccountResponseModel? {
        return accountDao.getAccountDetails(id)?.toDomain()
    }

    override suspend fun insertAccounts(accounts: List<AccountModel>) {
        accountDao.insertAccounts(accounts.map { AccountEntity.toEntity(it) })
    }

    override suspend fun insertAccountDetails(account: AccountResponseModel) {
        accountDao.insertAccountDetails(AccountResponseEntity.toEntity(account))
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel? {
        return accountDao.getAccountHistory(id)?.toDomain()
    }

    override suspend fun insertAccountHistory(account: AccountHistoryResponseModel) {
        accountDao.insertAccountHistory(AccountHistoryEntity.toEntity(account))
    }

    override suspend fun clearAccounts() {
        accountDao.clearAccounts()
    }

    override suspend fun clearAccountDetails() {
        accountDao.clearAccountDetails()
    }
}