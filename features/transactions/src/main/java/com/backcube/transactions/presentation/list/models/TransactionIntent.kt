package com.backcube.economyapp.features.transactions.presentation.list.store.models

sealed interface TransactionIntent {
    data object GoToHistory : TransactionIntent
    data object AddTransaction : TransactionIntent
    data class  EditTransaction(val id: Int) : TransactionIntent
    data object Refresh : TransactionIntent
}