package com.backcube.economyapp.data.remote.repositories.impl

import com.backcube.economyapp.data.remote.api.CategoriesApi
import com.backcube.economyapp.data.remote.models.response.categories.toDomain
import com.backcube.economyapp.data.remote.utils.getOrThrow
import com.backcube.economyapp.data.remote.utils.retry.RetryHandler
import com.backcube.economyapp.domain.models.categories.CategoryModel
import com.backcube.economyapp.domain.repositories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoriesApi: CategoriesApi,
    private val retryHandler: RetryHandler,
) : CategoryRepository {
    override suspend fun getCategories(): List<CategoryModel> =
        retryHandler.executeWithRetry {
            categoriesApi.getCategories().getOrThrow().map { it.toDomain() }
        }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel> =
        retryHandler.executeWithRetry {
            categoriesApi.getCategoriesByType(isIncome).getOrThrow().map { it.toDomain() }
        }
}