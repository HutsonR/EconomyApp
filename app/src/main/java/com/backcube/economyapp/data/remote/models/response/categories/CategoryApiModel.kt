package com.backcube.economyapp.data.remote.models.response.categories

import com.backcube.economyapp.domain.models.categories.CategoryModel

/**
 * Модель категории доходов или расходов
 *
 * @property id ID категории
 * @property name Название категории
 * @property emoji Эмодзи категории
 * @property isIncome Признак дохода (true — доход, false — расход)
 */
data class CategoryApiModel(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)

fun CategoryApiModel.toDomain() = CategoryModel(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)