package com.backcube.data.remote.models.response.accounts

import com.backcube.domain.models.accounts.StatModel

/**
 * Модель статистики по категории
 *
 * @property categoryId ID категории
 * @property categoryName Название категории
 * @property emoji Эмодзи, ассоциированное с категорией
 * @property amount Сумма по категории
 */
data class StatApiModel(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String
)

fun StatApiModel.toDomain() = StatModel(
    categoryId = categoryId,
    categoryName = categoryName,
    emoji = emoji,
    amount = amount.toBigDecimal()
)