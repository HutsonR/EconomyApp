package com.backcube.data.remote.impl.models.response.accounts

import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.utils.toCurrencyIsoCode
import kotlinx.serialization.Serializable
import java.time.Instant

/**
 * Основная модель счета
 *
 * @property id ID счета
 * @property userId ID пользователя, которому принадлежит счет
 * @property name Название счета
 * @property balance Текущий баланс
 * @property currency Валюта счета
 * @property createdAt Дата создания
 * @property updatedAt Дата последнего обновления
 */
@Serializable
data class AccountApiModel(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)

fun AccountApiModel.toDomain() = AccountModel(
    id = id,
    userId = userId,
    name = name,
    balance = balance.toBigDecimal(),
    currency = currency.toCurrencyIsoCode(),
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt)
)