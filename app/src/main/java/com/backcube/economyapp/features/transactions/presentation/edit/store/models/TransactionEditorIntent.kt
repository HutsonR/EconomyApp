package com.backcube.economyapp.features.transactions.presentation.edit.store.models

import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.models.categories.CategoryModel

sealed interface TransactionEditorIntent {
    data class OnAmountChange(val amount: String) : TransactionEditorIntent
    data class OnDescriptionChange(val description: String) : TransactionEditorIntent
    data class OnDateSelected(val date: Long?) : TransactionEditorIntent
    data class OnAccountSelected(val account: AccountModel) : TransactionEditorIntent
    data class OnCategorySelected(val category: CategoryModel) : TransactionEditorIntent
    data object OnSaveClick : TransactionEditorIntent
    data object OnCancelClick : TransactionEditorIntent
    data object OnOpenAccountSheet : TransactionEditorIntent
    data object OnOpenCategorySheet : TransactionEditorIntent
    data object OnOpenDatePickerModal : TransactionEditorIntent
    data object Refresh : TransactionEditorIntent
}