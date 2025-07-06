package com.backcube.economyapp.domain.usecases.impl

import com.backcube.economyapp.domain.models.accounts.AccountCreateRequestModel
import com.backcube.economyapp.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.models.accounts.AccountResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.economyapp.domain.repositories.AccountRepository
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.utils.retry.RetryHandler
import javax.inject.Inject

class AccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    private val retryHandler: RetryHandler
): AccountUseCase {

    override suspend fun getAccounts(): Result<List<AccountModel>> {
        return retryHandler.executeWithRetryResult {
            accountRepository.getAccounts()
        }
    }

    override suspend fun createAccount(request: AccountCreateRequestModel): Result<AccountModel> {
        return retryHandler.executeWithRetryResult {
            accountRepository.createAccount(request)
        }
    }

    override suspend fun getAccountById(id: Int): Result<AccountResponseModel?> {
        return retryHandler.executeWithRetryResult {
            accountRepository.getAccountById(id)
        }
    }

    override suspend fun updateAccount(id: Int, request: AccountUpdateRequestModel): Result<AccountModel> {
        return retryHandler.executeWithRetryResult {
            accountRepository.updateAccount(id, request)
        }
    }

    override suspend fun getAccountHistory(id: Int): Result<AccountHistoryResponseModel> {
        return retryHandler.executeWithRetryResult {
            accountRepository.getAccountHistory(id)
        }
    }

}