package com.backcube.domain.models.transactions

import com.backcube.domain.models.accounts.AccountBriefModel
import com.backcube.domain.models.categories.CategoryModel
import java.math.BigDecimal
import java.time.Instant

data class TransactionResponseModel(
    val id: Int,
    val account: AccountBriefModel,
    val category: CategoryModel,
    val amount: BigDecimal,
    val transactionDate: Instant,
    val comment: String?,
    val createdAt: Instant,
    val updatedAt: Instant
)