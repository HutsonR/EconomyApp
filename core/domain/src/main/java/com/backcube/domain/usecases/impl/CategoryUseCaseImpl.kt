package com.backcube.domain.usecases.impl

import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.repositories.CategoryRepository
import com.backcube.domain.usecases.api.CategoryUseCase
import javax.inject.Inject

class CategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
): CategoryUseCase {

    override suspend fun getCategories(): Result<List<CategoryModel>> =
        runCatching {
            categoryRepository.getCategories()
        }

    override suspend fun getCategoriesByType(isIncome: Boolean): Result<List<CategoryModel>> =
        runCatching {
            categoryRepository.getCategoriesByType(isIncome)
        }

}