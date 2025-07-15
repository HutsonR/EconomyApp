package com.backcube.transactions.presentation.analyze.domain

import com.backcube.domain.models.transactions.TransactionResponseModel
import com.backcube.transactions.presentation.analyze.domain.models.CategoryStats
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class GetCategoriesWithPercentUseCase @Inject constructor() {
    operator fun invoke(transactions: List<TransactionResponseModel>): List<CategoryStats> {
        if (transactions.isEmpty()) return emptyList()

        val totalCategories = transactions.size.toFloat()

        val grouped = transactions.groupBy { it.category }
        return grouped.map { (category, groupTransactions) ->
            val count = groupTransactions.size
            val percentage = (count.toFloat() / totalCategories) * 100

            val totalAmount = groupTransactions.fold(BigDecimal.ZERO) { acc, tx ->
                acc + tx.amount
            }

            CategoryStats(
                name = category.name,
                emoji = category.emoji,
                percent = BigDecimal(percentage.toDouble())
                    .setScale(2, RoundingMode.HALF_UP)
                    .toFloat(),
                totalAmount = totalAmount.setScale(2, RoundingMode.HALF_UP)
            )
        }
    }
}