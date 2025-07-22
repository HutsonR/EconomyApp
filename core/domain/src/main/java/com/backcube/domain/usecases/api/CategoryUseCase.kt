package com.backcube.domain.usecases.api

import com.backcube.domain.models.categories.CategoryModel

interface CategoryUseCase {
    /**
     * Возвращает список всех категорий (доходов и расходов)
     * */
    suspend fun getCategories(): Result<List<CategoryModel>>

    /**
     * Возвращает список категорий доходов или расходов
     *
     * @param isIncome Тип категорий: true - доходы, false - расходы
     * */
    suspend fun getCategoriesByType(isIncome: Boolean): Result<List<CategoryModel>>
}