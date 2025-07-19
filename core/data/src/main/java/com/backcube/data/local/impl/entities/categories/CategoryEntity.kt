package com.backcube.data.local.impl.entities.categories

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backcube.domain.models.categories.CategoryModel

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
) {
    fun toDomain() = CategoryModel(
        id,
        name,
        emoji,
        isIncome
    )

    companion object {
        fun toEntity(categoryModel: CategoryModel) = CategoryEntity(
            id = categoryModel.id,
            name = categoryModel.name,
            emoji = categoryModel.emoji,
            isIncome = categoryModel.isIncome
        )
    }
}