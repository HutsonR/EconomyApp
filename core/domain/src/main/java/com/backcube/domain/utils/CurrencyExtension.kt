package com.backcube.domain.utils

import com.backcube.domain.utils.CurrencyIsoCode.EUR
import com.backcube.domain.utils.CurrencyIsoCode.RUB
import com.backcube.domain.utils.CurrencyIsoCode.USD

enum class CurrencyIsoCode {
    RUB, USD, EUR;
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