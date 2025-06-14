package com.backcube.economyapp.data.remote.repositories.impl

import com.backcube.economyapp.data.remote.api.CategoriesApi
import com.backcube.economyapp.data.remote.models.response.categories.toDomain
import com.backcube.economyapp.domain.models.categories.CategoryModel
import com.backcube.economyapp.domain.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoriesApi: CategoriesApi
) : CategoryRepository {
    override suspend fun getCategories(): List<CategoryModel> = withContext(Dispatchers.IO) {
        categoriesApi.getCategories().map { it.toDomain() }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel> = withContext(Dispatchers.IO) {
        categoriesApi.getCategoriesByType(isIncome).map { it.toDomain() }
    }
}