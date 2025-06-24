package com.backcube.economyapp.data.remote.api

import com.backcube.economyapp.data.remote.models.request.transactions.TransactionRequestApiModel
import com.backcube.economyapp.data.remote.models.response.transactions.TransactionResponseApiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TransactionsApi {
    /**
     * Создает новую транзакцию (доход или расход)
     *
     * @param request see [TransactionRequestApiModel]
     * */
    @POST("transactions")
    suspend fun createTransaction(@Body request: TransactionRequestApiModel): Response<TransactionResponseApiModel>
    /**
     * Возвращает детальную информацию о транзакции
     *
     * @param id ID транзакции
     * */
    @GET("transactions/{id}")
    suspend fun getTransactionById(@Path("id") id: Int): Response<TransactionResponseApiModel>

    /**
     * Обновляет существующую транзакцию
     *
     * @param id ID транзакции
     * @param request see [TransactionRequestApiModel]
     * */
    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Path("id") id: Int,
        @Body request: TransactionRequestApiModel
    ): Response<TransactionResponseApiModel>

    /**
     * Удаляет транзакцию с возможностью возврата средств на счет
     *
     * @param id ID транзакции
     * */
    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(@Path("id") id: Int): Response<Unit>

    /**
     * Возвращает список транзакций для указанного счета за указанный период
     *
     * @param accountId ID счета
     * @param startDate Начальная дата периода (YYYY-MM-DD). Если не указана, используется начало текущего месяца.
     * @param endDate Конечная дата периода (YYYY-MM-DD). Если не указана, используется конец текущего месяца.
     * */
    @GET("transactions/account/{accountId}/period")
    suspend fun getAccountTransactions(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?
    ): Response<List<TransactionResponseApiModel>>
}