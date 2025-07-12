package com.backcube.economyapp.features.transactions.presentation.edit.store.models

sealed interface TransactionEditorEffect {
    data object ShowFetchError : TransactionEditorEffect
    data object ShowClientError : TransactionEditorEffect
    data object ShowAccountSheet : TransactionEditorEffect
    data object ShowCategorySheet : TransactionEditorEffect
    data object ShowDatePickerModal : TransactionEditorEffect
    data object GoBack : TransactionEditorEffect
}