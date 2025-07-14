package com.backcube.transactions.presentation.edit.models

import com.backcube.ui.components.AlertData

sealed interface TransactionEditorEffect {
    data object ShowFetchError : TransactionEditorEffect
    data class ShowClientError(val alertData: AlertData = AlertData()) : TransactionEditorEffect
    data object ShowAccountSheet : TransactionEditorEffect
    data object ShowCategorySheet : TransactionEditorEffect
    data object ShowDatePickerModal : TransactionEditorEffect
    data object ShowTimePickerModal : TransactionEditorEffect
    data object GoBack : TransactionEditorEffect
}