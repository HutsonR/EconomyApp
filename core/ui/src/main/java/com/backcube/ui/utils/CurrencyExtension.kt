package com.backcube.ui.utils

import com.backcube.domain.models.accounts.CurrencyIsoCode
import com.backcube.domain.models.accounts.CurrencyIsoCode.EUR
import com.backcube.domain.models.accounts.CurrencyIsoCode.RUB
import com.backcube.domain.models.accounts.CurrencyIsoCode.USD
import com.backcube.ui.R

fun CurrencyIsoCode.toCurrency(): String = when(this) {
    RUB -> "\u20BD"
    USD -> "\u0024"
    EUR -> "\u20AC"
}

fun CurrencyIsoCode.toDisplayNameId(): Int = when (this) {
    RUB -> R.string.currency_rub
    USD -> R.string.currency_usd
    EUR -> R.string.currency_eur
}

fun CurrencyIsoCode.toImageSource(): Int = when (this) {
    RUB -> R.drawable.ic_rub
    USD -> R.drawable.ic_usd
    EUR -> R.drawable.ic_eur
}