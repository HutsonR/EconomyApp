package com.backcube.economyapp.domain.utils

fun String.toCurrency(): String {
    return when(this) {
        "RUR" -> "\u20BD"
        "RUB" -> "\u20BD"
        "USD" -> "\u0024"
        "EUR" -> "\u20AC"
        else -> ""
    }
}