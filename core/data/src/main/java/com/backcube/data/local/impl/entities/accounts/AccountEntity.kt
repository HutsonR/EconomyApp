package com.backcube.data.local.impl.entities.accounts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.utils.toCurrencyIsoCode
import java.time.Instant

@Entity(tableName = "accounts_api")
data class AccountEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
) {
    fun toDomain() = AccountModel(
        id = id,
        userId = userId,
        name = name,
        balance = balance.toBigDecimal(),
        currency = currency.toCurrencyIsoCode(),
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt)
    )
    companion object {
        fun toEntity(accountModel: AccountModel) = AccountEntity(
            id = accountModel.id,
            userId = accountModel.userId,
            name = accountModel.name,
            balance = accountModel.balance.toPlainString(),
            currency = accountModel.currency.name,
            createdAt = accountModel.createdAt.toString(),
            updatedAt = accountModel.updatedAt.toString()
        )
    }
}