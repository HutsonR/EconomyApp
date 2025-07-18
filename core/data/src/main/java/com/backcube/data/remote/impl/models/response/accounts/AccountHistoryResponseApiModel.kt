package com.backcube.data.remote.impl.models.response.accounts

import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.domain.utils.toCurrencyIsoCode

/**
 * Ответ API с историей изменений счета
 *
 * @property accountId ID счета
 * @property accountName Название счета
 * @property currency Валюта счета
 * @property currentBalance Текущий баланс
 * @property history Список изменений счета
 */
data class AccountHistoryResponseApiModel(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: List<AccountHistoryApiModel>
)

fun AccountHistoryResponseApiModel.toDomain() = AccountHistoryResponseModel(
    accountId = accountId,
    accountName = accountName,
    currency = currency.toCurrencyIsoCode(),
    currentBalance = currentBalance.toBigDecimal(),
    history = history.map { it.toDomain() }
)