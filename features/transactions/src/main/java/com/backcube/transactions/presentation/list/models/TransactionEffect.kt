package com.backcube.economyapp.features.transactions.presentation.list.store.models

sealed interface TransactionEffect {
    data object NavigateToHistory : TransactionEffect
    data class NavigateToEditorTransaction(val transactionId: String) : TransactionEffect
    data object ShowClientError : TransactionEffect
}