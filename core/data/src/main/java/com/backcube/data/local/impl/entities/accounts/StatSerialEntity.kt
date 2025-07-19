package com.backcube.data.local.impl.entities.accounts

import com.backcube.domain.models.accounts.StatModel
import kotlinx.serialization.Serializable

@Serializable
data class StatSerialEntity(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String
) {
    fun toDomain() = StatModel(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount.toBigDecimal()
    )
    companion object {
        fun toEntity(statModel: StatModel) = StatSerialEntity(
            categoryId = statModel.categoryId,
            categoryName = statModel.categoryName,
            emoji = statModel.emoji,
            amount = statModel.amount.toPlainString()
        )
    }
}