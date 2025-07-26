package com.backcube.data.local.impl.entities.accounts

import com.backcube.domain.models.accounts.AccountStateModel
import com.backcube.domain.utils.toCurrencyIsoCode
import kotlinx.serialization.Serializable

@Serializable
data class AccountStateEntity(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
) {
    internal fun toDomain() = AccountStateModel(
        id = id,
        name = name,
        balance = balance.toBigDecimal(),
        currency = currency.toCurrencyIsoCode()
    )
    companion object {
        internal fun toEntity(accountStateModel: AccountStateModel?): AccountStateEntity? {
            if (accountStateModel == null) return null
            return AccountStateEntity(
                id = accountStateModel.id,
                name = accountStateModel.name,
                balance = accountStateModel.balance.toPlainString(),
                currency = accountStateModel.currency.name
            )
        }
    }
}