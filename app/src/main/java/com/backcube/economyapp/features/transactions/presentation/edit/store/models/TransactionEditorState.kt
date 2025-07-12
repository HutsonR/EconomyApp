package com.backcube.economyapp.features.transactions.presentation.edit.store.models

import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.models.categories.CategoryModel
import com.backcube.economyapp.domain.models.transactions.TransactionResponseModel
import java.math.BigDecimal
import java.time.Instant

data class TransactionEditorState (
    val isLoading: Boolean = false,
    val item: TransactionResponseModel? = null,
    val accounts: List<AccountModel> = emptyList(),
    val categories: List<CategoryModel> = emptyList(),
    val selectedAccount: AccountModel? = null,
    val selectedCategory: CategoryModel? = null,
    val selectedTransactionDate: Instant = Instant.now(),
    val comment: String = "",
    val amount: String = "",
    val formattedAmount: BigDecimal = BigDecimal.ZERO
)