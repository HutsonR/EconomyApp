package com.backcube.economyapp.domain.usecases.api

import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import java.time.Instant

interface TransactionUseCase {
    /**
     * Создает новую транзакцию (доход или расход)
     *
     * @param request see [TransactionRequestModel]
     * */
    suspend fun createTransaction(request: TransactionRequestModel): Result<TransactionResponseModel>

    /**
     * Возвращает детальную информацию о транзакции
     *
     * @param id ID транзакции
     * */
    suspend fun getTransactionById(id: Int): Result<TransactionResponseModel>

    /**
     * Обновляет существующую транзакцию
     *
     * @param id ID транзакции
     * @param request see [TransactionRequestModel]
     * */
    suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): Result<TransactionResponseModel>

    /**
     * Удаляет транзакцию с возможностью возврата средств на счет
     *
     * @param id ID транзакции
     * */
    suspend fun deleteTransaction(id: Int): Result<Boolean>

    /**
     * Возвращает список транзакций для указанного счета за указанный период
     *
     * @param accountId ID счета
     * @param startDate Начальная дата периода (YYYY-MM-DD). Если не указана, используется начало текущего месяца.
     * @param endDate Конечная дата периода (YYYY-MM-DD). Если не указана, используется конец текущего месяца.
     * */
    suspend fun getAccountTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): Result<List<TransactionResponseModel>>
}