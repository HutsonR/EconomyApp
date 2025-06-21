package com.backcube.economyapp.domain.usecases.impl

import com.backcube.economyapp.domain.models.categories.CategoryModel
import com.backcube.economyapp.domain.repositories.CategoryRepository
import com.backcube.economyapp.domain.retry.RetryHandler
import com.backcube.economyapp.domain.usecases.api.CategoryUseCase
import javax.inject.Inject

class CategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val retryHandler: RetryHandler
): CategoryUseCase {

    override suspend fun getCategories(): List<CategoryModel> {
        return retryHandler.executeWithRetry {
            categoryRepository.getCategories()
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel> {
        return retryHandler.executeWithRetry {
            categoryRepository.getCategoriesByType(isIncome)
        }
    }

}