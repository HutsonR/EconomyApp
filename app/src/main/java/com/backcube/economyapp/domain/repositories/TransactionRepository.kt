package com.backcube.economyapp.domain.repositories

import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel

interface TransactionRepository {
    /**
     * Создает новую транзакцию (доход или расход)
     *
     * @param request see [TransactionRequestModel]
     * */
    suspend fun createTransaction(request: TransactionRequestModel): TransactionResponseModel

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
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseModel>
}