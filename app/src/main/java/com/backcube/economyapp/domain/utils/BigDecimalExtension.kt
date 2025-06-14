package com.backcube.economyapp.domain.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.formatAsWholeThousands(): String {
    val numberFormat = NumberFormat.getInstance(Locale("ru", "RU"))
    numberFormat.maximumFractionDigits = 0
    numberFormat.isGroupingUsed = true
    return numberFormat.format(this)
}