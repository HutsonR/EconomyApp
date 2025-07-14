package com.backcube.transactions.presentation.list.models

internal sealed interface TransactionIntent {
    data object GoToHistory : TransactionIntent
    data object AddTransaction : TransactionIntent
    data class  EditTransaction(val id: Int) : TransactionIntent
    data object Refresh : TransactionIntent
}