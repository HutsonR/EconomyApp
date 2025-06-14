package com.backcube.economyapp.domain.repositories

import com.backcube.economyapp.domain.models.categories.CategoryModel

interface CategoryRepository {
    /**
     * Возвращает список всех категорий (доходов и расходов)
     * */
    suspend fun getCategories(): List<CategoryModel>

    /**
     * Возвращает список категорий доходов или расходов
     *
     * @param isIncome Тип категорий: true - доходы, false - расходы
     * */
    suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel>
}