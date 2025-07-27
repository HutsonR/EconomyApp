package com.backcube.data.local.impl.entities.transactions

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backcube.domain.models.transactions.TransactionModel
import java.time.Instant

@Entity(tableName = "transactions_api")
internal data class TransactionEntity(
    @PrimaryKey val id: Int,
    val accountId: Int,
    val categoryId: Int,
    val amount: String,
    val transactionDate: String,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
) {
    internal fun toDomain() = TransactionModel(
        id = id,
        accountId = accountId,
        categoryId = categoryId,
        amount = amount.toBigDecimal(),
        transactionDate = Instant.parse(transactionDate),
        comment = comment,
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt)
    )
    companion object {
        internal fun toEntity(transactionModel: TransactionModel) = TransactionEntity(
            id = transactionModel.id,
            accountId = transactionModel.accountId,
            categoryId = transactionModel.categoryId,
            amount = transactionModel.amount.toPlainString(),
            transactionDate = transactionModel.transactionDate.toString(),
            comment = transactionModel.comment,
            createdAt = transactionModel.createdAt.toString(),
            updatedAt = transactionModel.updatedAt.toString()
        )
    }
}
