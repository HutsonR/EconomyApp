package com.backcube.data.remote.repositories.fake

import com.backcube.data.remote.repositories.fake.utils.LOADING_DELAY
import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeCategoryRepositoryImpl @Inject constructor(): CategoryRepository {
    override suspend fun getCategories(): List<CategoryModel> = withContext(Dispatchers.IO) {
        delay(LOADING_DELAY)
        return@withContext categories
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryModel> = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

    val categories = listOf(
        CategoryModel(1, "Зарплата", "💰", true),
        CategoryModel(2, "Фриланс", "🧑‍💻", true),
        CategoryModel(3, "Подарки", "🎁", true),
        CategoryModel(4, "Продукты", "🛒", false),
        CategoryModel(5, "Транспорт", "🚌", false),
        CategoryModel(6, "Кафе и рестораны", "☕", false),
        CategoryModel(7, "Путешествия", "✈️", false),
        CategoryModel(8, "Развлечения", "🎮", false),
        CategoryModel(9, "Здоровье", "💊", false),
        CategoryModel(10, "Одежда", "👕", false)
    )

}