package com.backcube.data.common.repositories.impl

import com.backcube.data.common.utils.notSameContentWith
import com.backcube.data.local.api.CategoriesLocalDataSource
import com.backcube.data.remote.api.CategoriesRemoteDataSource
import com.backcube.data.remote.impl.models.response.categories.toDomain
import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.repositories.CategoryRepository
import com.backcube.domain.utils.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoriesLocalDataSource: CategoriesLocalDataSource,
    private val categoriesRemoteDataSource: CategoriesRemoteDataSource,
    private val connectivityObserver: ConnectivityObserver
) : CategoryRepository {

    override suspend fun getCategories(): Flow<List<CategoryModel>> = flow {
        val cachedCategories = categoriesLocalDataSource.getCategories().also {
            emit(it)
        }

        if (!connectivityObserver.isInternetAvailable()) return@flow
        val remoteCategories = categoriesRemoteDataSource.getCategories().map { it.toDomain() }
        if (cachedCategories notSameContentWith remoteCategories) {
            categoriesLocalDataSource.insertCategories(remoteCategories)
            emit(remoteCategories)
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): Flow<List<CategoryModel>> = flow {
        val cachedCategories = categoriesLocalDataSource.getCategoriesByType(isIncome).also {
            emit(it)
        }

        if (!connectivityObserver.isInternetAvailable()) return@flow
        val remoteCategories = categoriesRemoteDataSource.getCategoriesByType(isIncome).map { it.toDomain() }
        if (cachedCategories notSameContentWith remoteCategories) {
            categoriesLocalDataSource.insertCategories(remoteCategories)
            emit(remoteCategories)
        }
    }
}