package com.backcube.account.main.mappers

import androidx.compose.ui.graphics.Color
import com.backcube.domain.models.accounts.AccountHistoryResponseModel
import com.backcube.ui.components.graphics.ChartPoint
import java.math.BigDecimal
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun AccountHistoryResponseModel.toChartPoints(): List<ChartPoint> {
    return history
        .sortedBy { it.changeTimestamp }
        .filterNot { historyItem ->
            // Исключаем, если баланс не изменился, но изменился только currency
            val sameBalance = historyItem.previousState?.balance == historyItem.newState.balance
            val currencyChanged = historyItem.previousState?.currency != historyItem.newState.currency
            sameBalance && currencyChanged
        }
        .map { historyItem ->
            val currentBalance = historyItem.newState.balance
            val previousBalance = historyItem.previousState?.balance

            val delta = if (previousBalance != null) {
                currentBalance - previousBalance
            } else {
                BigDecimal.ZERO
            }

            val color = when {
                delta > BigDecimal.ZERO -> Color(0xFF2AE881)
                delta < BigDecimal.ZERO -> Color(0xFFFF5F00)
                else -> Color.Gray
            }

            val formattedDate = historyItem.changeTimestamp
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(DateTimeFormatter.ofPattern("dd.MM"))

            ChartPoint(
                xLabel = formattedDate,
                value = currentBalance.toFloat(),
                color = color
            )
        }
}
