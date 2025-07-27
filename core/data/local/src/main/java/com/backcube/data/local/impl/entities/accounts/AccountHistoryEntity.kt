package com.backcube.data.local.impl.entities.accounts

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.utils.toCurrencyIsoCode

@Entity(tableName = "accounts_history")
internal data class AccountHistoryEntity(
    @PrimaryKey val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<HistoryModelEntity>
) {
    internal fun toDomain() = AccountHistoryResponseModel(
        accountId = accountId,
        accountName = accountName,
        currency = currency.toCurrencyIsoCode(),
        currentBalance = currentBalance.toBigDecimal(),
        history = history.map { it.toDomain() }
    )
    companion object {
        internal fun toEntity(accountHistoryResponseModel: AccountHistoryResponseModel) = AccountHistoryEntity(
            accountId = accountHistoryResponseModel.accountId,
            accountName = accountHistoryResponseModel.accountName,
            currency = accountHistoryResponseModel.currency.name,
            currentBalance = accountHistoryResponseModel.currentBalance.toPlainString(),
            history = accountHistoryResponseModel.history.map { HistoryModelEntity.toEntity(it) }
        )
    }
}