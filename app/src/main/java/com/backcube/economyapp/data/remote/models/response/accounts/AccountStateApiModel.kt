package com.backcube.economyapp.data.remote.models.response.accounts

import com.backcube.economyapp.domain.models.accounts.AccountStateModel
import com.backcube.economyapp.domain.utils.toCurrencyIsoCode

/**
 * Модель состояния счета (используется в истории изменений)
 *
 * @property id ID состояния
 * @property name Название счета
 * @property balance Баланс счета
 * @property currency Валюта счета
 */
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