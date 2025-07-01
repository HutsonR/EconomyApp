package com.backcube.economyapp.data.remote.repositories.impl

import com.backcube.economyapp.data.remote.api.CategoriesApi
import com.backcube.economyapp.data.remote.models.response.categories.toDomain
import com.backcube.economyapp.data.remote.utils.getOrThrow
import com.backcube.economyapp.domain.models.categories.CategoryModel
import com.backcube.economyapp.domain.utils.qualifiers.IoDispatchers
import com.backcube.economyapp.domain.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoriesApi: CategoriesApi,
    @IoDispatchers private val dispatcher: CoroutineDispatcher,
) : CategoryRepository {
    override suspend fun getCategories(): List<CategoryModel> = withContext(dispatcher) {
        categoriesApi.getCategories().getOrThrow().map { it.toDomain() }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel> = withContext(dispatcher) {
        categoriesApi.getCategoriesByType(isIncome).getOrThrow().map { it.toDomain() }
    }
}