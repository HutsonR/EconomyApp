package com.backcube.data.remote.repositories.impl

import com.backcube.data.remote.api.CategoriesApi
import com.backcube.data.remote.models.response.categories.toDomain
import com.backcube.data.remote.utils.getOrThrow
import com.backcube.data.remote.utils.retry.RetryHandler
import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.repositories.CategoryRepository
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