package com.backcube.transactions.presentation.edit.models

import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.categories.CategoryModel

internal sealed interface TransactionEditorIntent {
    data class OnAmountChange(val amount: String) : TransactionEditorIntent
    data class OnDescriptionChange(val description: String) : TransactionEditorIntent
    data class OnDateSelected(val date: Long?) : TransactionEditorIntent
    data class OnTimeSelected(val hour: Int, val minute: Int) : TransactionEditorIntent
    data class OnAccountSelected(val account: AccountModel) : TransactionEditorIntent
    data class OnCategorySelected(val category: CategoryModel) : TransactionEditorIntent
    data object OnSaveClick : TransactionEditorIntent
    data object OnCancelClick : TransactionEditorIntent
    data object OnOpenAccountSheet : TransactionEditorIntent
    data object OnOpenCategorySheet : TransactionEditorIntent
    data object OnOpenDatePickerModal : TransactionEditorIntent
    data object OnOpenTimePickerModal : TransactionEditorIntent
    data object Refresh : TransactionEditorIntent
    data object OnDeleteClick : TransactionEditorIntent
}