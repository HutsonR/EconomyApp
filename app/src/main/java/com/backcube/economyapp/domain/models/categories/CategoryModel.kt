package com.backcube.economyapp.domain.models.categories

data class CategoryModel(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)
