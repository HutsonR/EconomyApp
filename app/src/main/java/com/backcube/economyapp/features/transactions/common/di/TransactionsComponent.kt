package com.backcube.economyapp.features.transactions.common.di

import com.backcube.economyapp.features.transactions.presentation.edit.TransactionEditorViewModel
import com.backcube.economyapp.features.transactions.presentation.histories.HistoryViewModel
import com.backcube.economyapp.features.transactions.presentation.list.viewmodels.TransactionsViewModel
import dagger.Subcomponent

@Subcomponent
interface TransactionsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): TransactionsComponent
    }

    val transactionsViewModel: TransactionsViewModel.Factory
    val transactionEditorViewModel: TransactionEditorViewModel.Factory
    val historyViewModel: HistoryViewModel
}
