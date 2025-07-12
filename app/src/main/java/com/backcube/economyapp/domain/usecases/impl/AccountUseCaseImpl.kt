package com.backcube.economyapp.domain.usecases.impl

import com.backcube.economyapp.domain.models.accounts.AccountCreateRequestModel
import com.backcube.economyapp.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.models.accounts.AccountResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.economyapp.domain.repositories.AccountRepository
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import javax.inject.Inject

class AccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
): AccountUseCase {

    override suspend fun getAccounts(): Result<List<AccountModel>> =
        runCatching {
            accountRepository.getAccounts()
        }

    override suspend fun createAccount(request: AccountCreateRequestModel): Result<AccountModel> =
        runCatching {
            accountRepository.createAccount(request)
        }

    override suspend fun getAccountById(id: Int): Result<AccountResponseModel?> =
        runCatching {
            accountRepository.getAccountById(id)
        }

    override suspend fun updateAccount(id: Int, request: AccountUpdateRequestModel): Result<AccountModel> =
        runCatching {
            accountRepository.updateAccount(id, request)
        }

    override suspend fun getAccountHistory(id: Int): Result<AccountHistoryResponseModel> =
        runCatching {
            accountRepository.getAccountHistory(id)
        }

}