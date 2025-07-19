package com.backcube.domain.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

/**
 * Форматирует число [BigDecimal] с разделением на тысячи.
 *
 * @param showDecimals Если `true`, отображает значения после десятичной точки, иначе — без дробной части.
 *
 * @receiver [BigDecimal] — число для форматирования.
 * @return Отформатированная строка с разделителями тысяч и опционально с одной десятичной цифрой.
 */
fun BigDecimal.formatAsWholeThousands(showDecimals: Boolean = false): String {
    val numberFormat = NumberFormat.getInstance(Locale("ru", "RU"))
    numberFormat.isGroupingUsed = true
    numberFormat.maximumFractionDigits = if (showDecimals) 2 else 0
    return numberFormat.format(this)
}