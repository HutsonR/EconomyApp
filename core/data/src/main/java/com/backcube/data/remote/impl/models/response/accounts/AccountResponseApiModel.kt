package com.backcube.data.remote.impl.models.response.accounts

import com.backcube.domain.models.accounts.AccountResponseModel
import com.backcube.domain.utils.toCurrencyIsoCode
import kotlinx.serialization.Serializable
import java.time.Instant

/**
 * Подробная информация о счете, включая статистику
 *
 * @property id ID счета
 * @property name Название счета
 * @property balance Баланс счета
 * @property currency Валюта
 * @property incomeStats Статистика доходов по категориям
 * @property expenseStats Статистика расходов по категориям
 * @property createdAt Дата создания
 * @property updatedAt Дата последнего обновления
 */
@Serializable
data class AccountResponseApiModel(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: List<StatApiModel>,
    val expenseStats: List<StatApiModel>,
    val createdAt: String,
    val updatedAt: String
)

fun AccountResponseApiModel.toDomain() = AccountResponseModel(
    id = id,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency.toCurrencyIsoCode(),
    incomeStats = incomeStats.map { it.toDomain() },
    expenseStats = expenseStats.map { it.toDomain() },
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)