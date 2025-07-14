package com.backcube.data.remote.api

import com.backcube.data.remote.models.request.accounts.AccountCreateRequestApiModel
import com.backcube.data.remote.models.request.accounts.AccountUpdateRequestApiModel
import com.backcube.data.remote.models.response.accounts.AccountApiModel
import com.backcube.data.remote.models.response.accounts.AccountHistoryResponseApiModel
import com.backcube.data.remote.models.response.accounts.AccountResponseApiModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AccountApi {

    /**
     * Возвращает список всех счетов текущего пользователя
     * */
    @GET("accounts")
    suspend fun getAccounts(): Response<List<AccountApiModel>>

    /**
     * Создает новый счет для текущего пользователя
     *
     * @param request See [AccountCreateRequestApiModel]
     * */
    @POST("accounts")
    suspend fun createAccount(@Body request: AccountCreateRequestApiModel): Response<AccountApiModel>

    /**
     * Возвращает информацию о конкретном счете, включая статистику
     *
     * @param id ID счета
     * */
    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: Int): Response<AccountResponseApiModel?>

    /**
     * Обновляет данные существующего счета
     *
     * @param id ID счета
     * @param request See [AccountUpdateRequestApiModel]
     * */
    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body request: AccountUpdateRequestApiModel
    ): Response<AccountApiModel>

    /**
     * Возвращает историю изменений баланса и других параметров счета, произведенных вне транзакций (при создании или изменении счета)
     *
     * @param id ID счета
     * */
    @GET("accounts/{id}/history")
    suspend fun getAccountHistory(@Path("id") id: Int): Response<AccountHistoryResponseApiModel>

}