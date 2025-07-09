package com.backcube.domain.models.accounts

import com.backcube.domain.models.accounts.CurrencyIsoCode.EUR
import com.backcube.domain.models.accounts.CurrencyIsoCode.RUB
import com.backcube.domain.models.accounts.CurrencyIsoCode.USD

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