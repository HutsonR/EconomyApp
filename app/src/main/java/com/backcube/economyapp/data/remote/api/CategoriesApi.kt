package com.backcube.economyapp.data.remote.api

import com.backcube.economyapp.data.remote.models.response.categories.CategoryApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApi {
    /**
     * Возвращает список всех категорий (доходов и расходов)
     * */
    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryApiModel>>

    /**
     * Возвращает список категорий доходов или расходов
     *
     * @param isIncome Тип категорий: true - доходы, false - расходы
     * */
    @GET("categories/type/{isIncome}")
    suspend fun getCategoriesByType(@Path("isIncome") isIncome: Boolean): Response<List<CategoryApiModel>>
}