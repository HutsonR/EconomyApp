package com.backcube.domain.usecases.impl

import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.repositories.CategoryRepository
import com.backcube.domain.usecases.api.CategoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
): CategoryUseCase {

    override suspend fun getCategories(): Flow<Result<List<CategoryModel>>> =
        categoryRepository.getCategories()
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

    override suspend fun getCategoriesByType(isIncome: Boolean): Flow<Result<List<CategoryModel>>> =
        categoryRepository.getCategoriesByType(isIncome)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

}