package com.backcube.economyapp.data.remote.models.response.categories

import com.backcube.economyapp.domain.models.categories.CategoryModel

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