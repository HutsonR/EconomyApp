package com.backcube.transactions.presentation.list.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.backcube.transactions.presentation.list.viewmodels.TransactionsViewModel

class TransactionsViewModelFactory(
    private val assistedFactory: TransactionsViewModel.Factory,
    private val isIncome: Boolean
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(isIncome) as T
    }
}
