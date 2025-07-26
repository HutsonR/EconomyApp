package com.backcube.data.local.impl.entities.transactions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backcube.domain.models.transactions.TransactionResponseModel
import java.time.Instant

@Entity(tableName = "transactions_response")
internal data class TransactionResponseEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "account_id")
    val accountId: Int,
    val account: AccountBriefSerialEntity,
    val category: CategorySerialEntity,
    val amount: String,
    @ColumnInfo(name = "transaction_date")
    val transactionDate: Long,
    val comment: String?,
    val createdAt: String,
    val updatedAt: String
) {
    internal fun toDomain() = TransactionResponseModel(
        id = id,
        account = account.toDomain(),
        category = category.toDomain(),
        amount = amount.toBigDecimal(),
        transactionDate = Instant.ofEpochMilli(transactionDate),
        comment = comment,
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt)
    )
    companion object {
        internal fun toEntity(transactionResponseModel: TransactionResponseModel) = TransactionResponseEntity(
            id = transactionResponseModel.id,
            accountId = transactionResponseModel.account.id,
            account = AccountBriefSerialEntity.toEntity(transactionResponseModel.account),
            category = CategorySerialEntity.toEntity(transactionResponseModel.category),
            amount = transactionResponseModel.amount.toPlainString(),
            transactionDate = transactionResponseModel.transactionDate.toEpochMilli(),
            comment = transactionResponseModel.comment,
            createdAt = transactionResponseModel.createdAt.toString(),
            updatedAt = transactionResponseModel.updatedAt.toString()
        )
    }
}
