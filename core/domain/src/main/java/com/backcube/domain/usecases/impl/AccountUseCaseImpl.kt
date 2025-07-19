package com.backcube.domain.usecases.impl

import com.backcube.domain.models.accounts.AccountCreateRequestModel
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.domain.repositories.AccountRepository
import com.backcube.domain.usecases.api.AccountUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
): AccountUseCase {

    override suspend fun getAccounts(): Flow<Result<List<AccountModel>>> =
        accountRepository.getAccounts()
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun createAccount(request: AccountCreateRequestModel): Flow<Result<AccountModel>> =
        accountRepository.createAccount(request)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun getAccountById(id: Int): Flow<Result<AccountResponseModel?>> =
        accountRepository.getAccountById(id)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun updateAccount(id: Int, request: AccountUpdateRequestModel): Flow<Result<AccountModel>> =
        accountRepository.updateAccount(id, request)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun getAccountHistory(id: Int): Flow<Result<AccountHistoryResponseModel>> =
        accountRepository.getAccountHistory(id)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

}