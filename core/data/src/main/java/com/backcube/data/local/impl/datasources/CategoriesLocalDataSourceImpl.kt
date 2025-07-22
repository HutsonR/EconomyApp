package com.backcube.data.local.impl.datasources

import com.backcube.data.local.api.CategoriesLocalDataSource
import com.backcube.data.local.impl.dao.CategoryDao
import com.backcube.data.local.impl.entities.categories.CategoryEntity
import com.backcube.domain.models.categories.CategoryModel
import javax.inject.Inject

class CategoriesLocalDataSourceImpl @Inject constructor(
    private val categoryDao: CategoryDao
): CategoriesLocalDataSource {

    override suspend fun getCategories(): List<CategoryModel> {
        return categoryDao.getAllCategories().map { it.toDomain() }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel> {
        return categoryDao.getCategoriesByType(isIncome).map { it.toDomain() }
    }

    override suspend fun getCategoryById(id: Int): CategoryModel? {
        return categoryDao.getCategoryById(id)?.toDomain()
    }

    override suspend fun insertCategories(list: List<CategoryModel>) {
        return categoryDao.insertCategories(list.map { CategoryEntity.toEntity(it) })
    }

}