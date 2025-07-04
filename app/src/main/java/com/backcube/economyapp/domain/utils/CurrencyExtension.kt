package com.backcube.economyapp.domain.utils

import com.backcube.economyapp.R
import com.backcube.economyapp.domain.utils.CurrencyIsoCode.EUR
import com.backcube.economyapp.domain.utils.CurrencyIsoCode.RUB
import com.backcube.economyapp.domain.utils.CurrencyIsoCode.USD

enum class CurrencyIsoCode {
    RUB, USD, EUR;

    fun toCurrency(): String = when(this) {
        RUB -> "\u20BD"
        USD -> "\u0024"
        EUR -> "\u20AC"
    }

    fun toDisplayNameId(): Int = when (this) {
        RUB -> R.string.currency_rub
        USD -> R.string.currency_usd
        EUR -> R.string.currency_eur
    }

    fun toImageSource(): Int = when (this) {
        RUB -> R.drawable.ic_rub
        USD -> R.drawable.ic_usd
        EUR -> R.drawable.ic_eur
    }
}

fun String.toCurrencyIsoCode(): CurrencyIsoCode {
    return when(this) {
        "RUR" -> RUB
        "RUB" -> RUB
        "USD" -> USD
        "EUR" -> EUR
        else -> RUB
    }
}