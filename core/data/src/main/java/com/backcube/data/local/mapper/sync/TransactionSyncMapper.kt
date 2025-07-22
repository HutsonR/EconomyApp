package com.backcube.data.local.mapper.sync

import com.backcube.domain.models.accounts.AccountBriefModel
import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.models.transactions.TransactionModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import java.time.Instant
import kotlin.random.Random

fun TransactionRequestModel.toFakeTransactionResponseModel(
    account: AccountBriefModel,
    category: CategoryModel
): TransactionResponseModel =
    TransactionResponseModel(
        id = -Random.nextInt(100000), // временный локальный ID
        account = account,
        category = category,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )

fun TransactionRequestModel.toFakeTransactionModel(): TransactionModel =
    TransactionModel(
        id = -Random.nextInt(100000), // временный локальный ID
        accountId = this.accountId,
        categoryId = this.categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )