package com.backcube.transactions.presentation.histories.models

import com.backcube.ui.components.date.DateMode

internal sealed interface HistoryIntent {
    data object GoBack : HistoryIntent

    data class ShowCalendar(
        val dateMode: DateMode
    ) : HistoryIntent

    data class UpdateDate(
        val dateMode: DateMode,
        val date: Long?
    ) : HistoryIntent

    data class EditTransaction(
        val id: Int
    ) : HistoryIntent

    data class GoAnalyze(
        val isIncome: Boolean
    ) : HistoryIntent
}