package com.backcube.data.common.repositories.impl

import com.backcube.data.common.utils.notSameContentWith
import com.backcube.data.local.api.CategoriesLocalDataSource
import com.backcube.data.remote.api.CategoriesRemoteDataSource
import com.backcube.data.remote.impl.models.response.categories.toDomain
import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.repositories.CategoryRepository
import com.backcube.domain.utils.ConnectivityObserver
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoriesLocalDataSource: CategoriesLocalDataSource,
    private val categoriesRemoteDataSource: CategoriesRemoteDataSource,
    private val connectivityObserver: ConnectivityObserver
) : CategoryRepository {

    override suspend fun getCategories(): List<CategoryModel> {
        val cachedCategories = categoriesLocalDataSource.getCategories()
        return if (!connectivityObserver.isInternetAvailable()) {
            cachedCategories
        } else {
            val remoteCategories = categoriesRemoteDataSource.getCategories().map { it.toDomain() }
            if (cachedCategories notSameContentWith remoteCategories) {
                categoriesLocalDataSource.insertCategories(remoteCategories)
            }
            remoteCategories
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel> {
        val cachedCategories = categoriesLocalDataSource.getCategoriesByType(isIncome)
        return if (!connectivityObserver.isInternetAvailable()) {
            cachedCategories
        } else {
            val remoteCategories = categoriesRemoteDataSource.getCategoriesByType(isIncome).map { it.toDomain() }
            if (cachedCategories notSameContentWith remoteCategories) {
                categoriesLocalDataSource.insertCategories(remoteCategories)
            }
            remoteCategories
        }
    }
}