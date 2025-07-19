package com.backcube.transactions.common.di

import com.backcube.transactions.presentation.analyze.AnalyzeViewModel
import com.backcube.transactions.presentation.edit.TransactionEditorViewModel
import com.backcube.transactions.presentation.histories.HistoryViewModel
import com.backcube.transactions.presentation.list.viewmodels.TransactionsViewModel
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
    val analyzeViewModel: AnalyzeViewModel.Factory
}
