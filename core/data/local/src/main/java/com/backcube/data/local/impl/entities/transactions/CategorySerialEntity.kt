package com.backcube.data.local.impl.entities.transactions

import com.backcube.domain.models.categories.CategoryModel
import kotlinx.serialization.Serializable

@Serializable
internal data class CategorySerialEntity(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
){
    internal fun toDomain() = CategoryModel(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )
    companion object {
        internal fun toEntity(categoryModel: CategoryModel) = CategorySerialEntity(
            id = categoryModel.id,
            name = categoryModel.name,
            emoji = categoryModel.emoji,
            isIncome = categoryModel.isIncome
        )
    }
}