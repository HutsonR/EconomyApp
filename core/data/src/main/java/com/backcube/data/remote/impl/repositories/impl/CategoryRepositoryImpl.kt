package com.backcube.data.remote.impl.repositories.impl

import com.backcube.data.remote.api.CategoriesRemoteDataSource
import com.backcube.data.remote.impl.models.response.categories.toDomain
import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoriesRemoteDataSource: CategoriesRemoteDataSource
) : CategoryRepository {

    override suspend fun getCategories(): Flow<List<CategoryModel>> = flow {
        emit(categoriesRemoteDataSource.getCategories().map { it.toDomain() })
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): Flow<List<CategoryModel>> = flow {
        emit(categoriesRemoteDataSource.getCategoriesByType(isIncome).map { it.toDomain() })
    }
}