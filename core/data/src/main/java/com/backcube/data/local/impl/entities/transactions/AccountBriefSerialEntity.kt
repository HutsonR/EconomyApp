package com.backcube.data.local.impl.entities.transactions

import com.backcube.domain.models.accounts.AccountBriefModel
import com.backcube.domain.utils.toCurrencyIsoCode
import kotlinx.serialization.Serializable

@Serializable
internal data class AccountBriefSerialEntity(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
) {
    internal fun toDomain() = AccountBriefModel(
        id = id,
        name = name,
        balance = balance.toBigDecimal(),
        currency = currency.toCurrencyIsoCode()
    )
    companion object {
        internal fun toEntity(accountBriefModel: AccountBriefModel) = AccountBriefSerialEntity(
            id = accountBriefModel.id,
            name = accountBriefModel.name,
            balance = accountBriefModel.balance.toPlainString(),
            currency = accountBriefModel.currency.name
        )
    }
}