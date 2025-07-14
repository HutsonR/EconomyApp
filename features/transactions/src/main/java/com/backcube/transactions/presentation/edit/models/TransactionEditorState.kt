package com.backcube.transactions.presentation.edit.models

import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.categories.CategoryModel
import java.time.Instant

data class TransactionEditorState (
    val isLoading: Boolean = false,
    val accounts: List<AccountModel> = emptyList(),
    val categories: List<CategoryModel> = emptyList(),
    val selectedAccount: AccountModel? = null,
    val selectedCategory: CategoryModel? = null,
    val selectedTransactionDate: Instant = Instant.now(),
    val comment: String = "",
    val amount: String = "",
    val formattedAmount: String = ""
)