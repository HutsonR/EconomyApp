package com.backcube.economyapp.features.transactions.presentation.edit.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.backcube.economyapp.features.transactions.presentation.edit.TransactionEditorViewModel

class TransactionEditorViewModelFactory(
    private val assistedFactory: TransactionEditorViewModel.Factory,
    private val transactionId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(transactionId) as T
    }
}
