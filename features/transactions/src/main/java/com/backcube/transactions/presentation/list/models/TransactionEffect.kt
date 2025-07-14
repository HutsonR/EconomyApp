package com.backcube.transactions.presentation.list.models

sealed interface TransactionEffect {
    data object NavigateToHistory : TransactionEffect
    data class NavigateToEditorTransaction(val transactionId: String) : TransactionEffect
    data object ShowClientError : TransactionEffect
}