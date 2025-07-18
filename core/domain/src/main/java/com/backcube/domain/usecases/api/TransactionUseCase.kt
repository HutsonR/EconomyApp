package com.backcube.domain.usecases.api

import com.backcube.domain.models.transactions.TransactionModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import kotlinx.coroutines.flow.Flow
import java.time.Instant

interface TransactionUseCase {
    /**
     * Создает новую транзакцию (доход или расход)
     *
     * @param request see [TransactionRequestModel]
     * */
    suspend fun createTransaction(request: TransactionRequestModel): Flow<Result<TransactionModel>>

    /**
     * Возвращает детальную информацию о транзакции
     *
     * @param id ID транзакции
     * */
    suspend fun getTransactionById(id: Int): Flow<Result<TransactionResponseModel>>

    /**
     * Обновляет существующую транзакцию
     *
     * @param id ID транзакции
     * @param request see [TransactionRequestModel]
     * */
    suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): Flow<Result<TransactionResponseModel>>

    /**
     * Удаляет транзакцию с возможностью возврата средств на счет
     *
     * @param id ID транзакции
     * */
    suspend fun deleteTransaction(id: Int): Flow<Result<Boolean>>

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
    ): Flow<Result<List<TransactionResponseModel>>>
}