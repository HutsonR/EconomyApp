package com.backcube.data.remote.models.request.accounts

import com.backcube.domain.models.accounts.AccountCreateRequestModel

/**
 * Модель запроса на создание нового счета
 *
 * @property name Название счета
 * @property balance Начальный баланс счета
 * @property currency Валюта счета (например, "RUB", "USD")
 */
data class AccountCreateRequestApiModel(
    val name: String,
    val balance: String,
    val currency: String
)

fun AccountCreateRequestModel.toApi() = AccountCreateRequestApiModel(
    name = name,
    balance = balance.toPlainString(),
    currency = currency.name
)