package com.backcube.data.remote.api

import com.backcube.data.remote.impl.models.response.categories.CategoryApiModel

interface CategoriesRemoteDataSource {
    suspend fun getCategories(): List<CategoryApiModel>
    suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryApiModel>
}