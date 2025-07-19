package com.backcube.data.remote.impl.datasources

import com.backcube.data.remote.api.CategoriesRemoteDataSource
import com.backcube.data.remote.impl.api.CategoriesApi
import com.backcube.data.remote.impl.models.response.categories.CategoryApiModel
import com.backcube.data.remote.impl.utils.getOrThrow
import com.backcube.data.remote.impl.utils.retry.RetryHandler
import javax.inject.Inject

class CategoriesRemoteDataSourceImpl @Inject constructor(
    private val categoriesApi: CategoriesApi,
    private val retryHandler: RetryHandler
) : CategoriesRemoteDataSource {

    override suspend fun getCategories(): List<CategoryApiModel> =
        retryHandler.executeWithRetry {
            categoriesApi.getCategories().getOrThrow()
        }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryApiModel> =
        retryHandler.executeWithRetry {
            categoriesApi.getCategoriesByType(isIncome).getOrThrow()
        }
}
