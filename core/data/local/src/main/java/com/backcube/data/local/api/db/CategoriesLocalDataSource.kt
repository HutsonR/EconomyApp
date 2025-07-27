package com.backcube.data.local.api.db

import com.backcube.domain.models.categories.CategoryModel

interface CategoriesLocalDataSource {
    suspend fun getCategories(): List<CategoryModel>
    suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel>
    suspend fun getCategoryById(id: Int): CategoryModel?
    suspend fun insertCategories(list: List<CategoryModel>)
}