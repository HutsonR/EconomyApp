package com.backcube.data.remote.impl.models.response.accounts

import com.backcube.domain.models.accounts.AccountBriefModel
import com.backcube.domain.utils.toCurrencyIsoCode

/**
 * Краткая модель счета (для отображения в списках и вложенных структурах)
 *
 * @property id ID счета
 * @property name Название счета
 * @property balance Баланс
 * @property currency Валюта счета
 */
data class AccountBriefApiModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)

fun AccountBriefApiModel.toDomain() = AccountBriefModel(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency.toCurrencyIsoCode()
)