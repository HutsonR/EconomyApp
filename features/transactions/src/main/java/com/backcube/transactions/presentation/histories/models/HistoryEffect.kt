package com.backcube.transactions.presentation.histories.models

import com.backcube.ui.components.date.DateMode

sealed interface HistoryEffect {
    data object GoBack : HistoryEffect

    data class ShowCalendar(
        val dateMode: DateMode
    ) : HistoryEffect

    data object ShowClientError : HistoryEffect

    data class NavigateToEditorTransaction(
        val transactionId: String
    ) : HistoryEffect

    data class NavigateToAnalyze(
        val isIncome: Boolean
    ) : HistoryEffect
}