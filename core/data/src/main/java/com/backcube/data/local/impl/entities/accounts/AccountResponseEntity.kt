package com.backcube.data.local.impl.entities.accounts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.utils.toCurrencyIsoCode
import java.time.Instant

@Entity(tableName = "accounts_response")
internal data class AccountResponseEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatSerialEntity>,
    val expenseStats: List<StatSerialEntity>,
    val createdAt: String,
    val updatedAt: String
) {
    internal fun toDomain() = AccountResponseModel(
        id = id,
        name = name,
        balance = balance.toBigDecimal(),
        currency = currency.toCurrencyIsoCode(),
        incomeStats = incomeStats.map { it.toDomain() },
        expenseStats = expenseStats.map { it.toDomain() },
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt)
    )
    companion object {
        internal fun toEntity(accountResponseModel: AccountResponseModel) = AccountResponseEntity(
            id = accountResponseModel.id,
            name = accountResponseModel.name,
            balance = accountResponseModel.balance.toPlainString(),
            currency = accountResponseModel.currency.name,
            incomeStats = accountResponseModel.incomeStats.map { StatSerialEntity.toEntity(it) },
            expenseStats = accountResponseModel.expenseStats.map { StatSerialEntity.toEntity(it) },
            createdAt = accountResponseModel.createdAt.toString(),
            updatedAt = accountResponseModel.updatedAt.toString()
        )
    }
}