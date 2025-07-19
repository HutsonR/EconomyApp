package com.backcube.data.remote.impl.models.response.accounts

import com.backcube.domain.models.accounts.AccountStateModel
import com.backcube.domain.utils.toCurrencyIsoCode
import kotlinx.serialization.Serializable

/**
 * Модель состояния счета (используется в истории изменений)
 *
 * @property id ID состояния
 * @property name Название счета
 * @property balance Баланс счета
 * @property currency Валюта счета
 */
@Serializable
data class AccountStateApiModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)

fun AccountStateApiModel.toDomain() = AccountStateModel(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency.toCurrencyIsoCode()
)