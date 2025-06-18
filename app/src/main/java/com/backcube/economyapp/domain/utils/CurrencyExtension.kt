package com.backcube.economyapp.domain.utils

import com.backcube.economyapp.domain.utils.CurrencyIsoCode.EUR
import com.backcube.economyapp.domain.utils.CurrencyIsoCode.RUB
import com.backcube.economyapp.domain.utils.CurrencyIsoCode.USD

enum class CurrencyIsoCode {
    RUB, USD, EUR;

    fun toCurrency(): String {
        return when(this) {
            RUB -> "\u20BD"
            USD -> "\u0024"
            EUR -> "\u20AC"
        }
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