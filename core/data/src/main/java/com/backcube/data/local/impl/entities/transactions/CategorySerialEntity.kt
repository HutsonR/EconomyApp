package com.backcube.data.local.impl.entities.transactions

import com.backcube.domain.models.categories.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
data class CategorySerialEntity(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
){
    fun toDomain() = CategoryModel(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
    companion object {
        fun toEntity(categoryModel: CategoryModel) = CategorySerialEntity(
            id = categoryModel.id,
            name = categoryModel.name,
            emoji = categoryModel.emoji,
            isIncome = categoryModel.isIncome
        )
    }
}