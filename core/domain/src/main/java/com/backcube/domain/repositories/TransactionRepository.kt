package com.backcube.domain.repositories

import com.backcube.domain.models.transactions.TransactionModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import java.time.Instant

interface TransactionRepository {
    /**
     * Создает новую транзакцию (доход или расход)
     *
     * @param request see [TransactionRequestModel]
     * */
    suspend fun createTransaction(request: TransactionRequestModel): TransactionModel

    /**
     * Возвращает детальную информацию о транзакции
     *
     * @param id ID транзакции
     * */
    suspend fun getTransactionById(id: Int): TransactionResponseModel

    /**
     * Обновляет существующую транзакцию
     *
     * @param id ID транзакции
     * @param request see [TransactionRequestModel]
     * */
    suspend fun updateTransaction(
        id: Int,
        request: TransactionRequestModel
    ): TransactionResponseModel

    /**
     * Удаляет транзакцию с возможностью возврата средств на счет
     *
     * @param id ID транзакции
     * */
    suspend fun deleteTransaction(id: Int): Boolean

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
    ): List<TransactionResponseModel>
}