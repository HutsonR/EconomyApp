package com.backcube.economyapp.domain.usecases.impl

import com.backcube.economyapp.domain.models.accounts.AccountCreateRequestModel
import com.backcube.economyapp.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.models.accounts.AccountResponseModel
import com.backcube.economyapp.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.economyapp.domain.repositories.AccountRepository
import com.backcube.economyapp.domain.retry.RetryHandler
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import javax.inject.Inject

class AccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
    private val retryHandler: RetryHandler
): AccountUseCase {

    override suspend fun getAccounts(): List<AccountModel> {
        return retryHandler.executeWithRetry {
            accountRepository.getAccounts()
        }
    }

    override suspend fun createAccount(request: AccountCreateRequestModel): AccountModel {
        return retryHandler.executeWithRetry {
            accountRepository.createAccount(request)
        }
    }

    override suspend fun getAccountById(id: Int): AccountResponseModel? {
        return retryHandler.executeWithRetry {
            accountRepository.getAccountById(id)
        }
    }

    override suspend fun updateAccount(id: Int, request: AccountUpdateRequestModel): AccountModel {
        return retryHandler.executeWithRetry {
            accountRepository.updateAccount(id, request)
        }
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponseModel {
        return retryHandler.executeWithRetry {
            accountRepository.getAccountHistory(id)
        }
    }

}