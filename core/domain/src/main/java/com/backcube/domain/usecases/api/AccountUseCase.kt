package com.backcube.domain.usecases.api

import com.backcube.domain.models.accounts.AccountCreateRequestModel
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.models.accounts.AccountUpdateRequestModel

interface AccountUseCase {
    /**
     * Возвращает список всех счетов текущего пользователя
     * */
    suspend fun getAccounts(): Result<List<AccountModel>>

    /**
     * Создает новый счет для текущего пользователя
     *
     * @param request See [AccountCreateRequestModel]
     * */
    suspend fun createAccount(request: AccountCreateRequestModel): Result<AccountModel>

    /**
     * Возвращает информацию о конкретном счете, включая статистику
     *
     * @param id ID счета
     * */
    suspend fun getAccountById(id: Int): Result<AccountResponseModel?>

    /**
     * Обновляет данные существующего счета
     *
     * @param id ID счета
     * @param request See [AccountUpdateRequestModel]
     * */
    suspend fun updateAccount(
        id: Int,
        request: AccountUpdateRequestModel
    ): Result<AccountModel>

    /**
     * Возвращает историю изменений баланса и других параметров счета, произведенных вне транзакций (при создании или изменении счета)
     *
     * @param id ID счета
     * */
    suspend fun getAccountHistory(id: Int): Result<AccountHistoryResponseModel?>
}