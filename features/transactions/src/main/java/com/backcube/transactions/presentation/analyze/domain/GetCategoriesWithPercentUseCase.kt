package com.backcube.transactions.presentation.analyze.domain

import com.backcube.domain.models.transactions.TransactionResponseModel
import com.backcube.transactions.presentation.analyze.domain.models.CategoryStats
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

/**
 * UseCase для расчета процента транзакций для каждой категории и общей суммы, потраченной в каждой категории.
 *
 * Этот класс берет список транзакций, группирует их по категориям, а затем вычисляет
 * процент транзакций, относящихся к каждой категории, по отношению к общему количеству транзакций.
 * Он также суммирует общую сумму транзакций по каждой категории.
 */
class GetCategoriesWithPercentUseCase @Inject constructor() {
    operator fun invoke(transactions: List<TransactionResponseModel>): List<CategoryStats> {
        if (transactions.isEmpty()) return emptyList()

        val grouped = transactions.groupBy { it.category }
        val totalAmount = transactions.fold(BigDecimal.ZERO) { acc, tx -> acc + tx.amount }

        return grouped.map { (category, groupTransactions) ->
            val categoryAmount = groupTransactions.fold(BigDecimal.ZERO) { acc, tx -> acc + tx.amount }

            val percentage = if (totalAmount != BigDecimal.ZERO) {
                (categoryAmount.toDouble() / totalAmount.toDouble()) * 100
            } else {
                0.0
            }

            CategoryStats(
                name = category.name,
                emoji = category.emoji,
                percent = percentage.toFloat(),
                totalAmount = categoryAmount.setScale(2, RoundingMode.HALF_UP)
            )
        }
    }
}