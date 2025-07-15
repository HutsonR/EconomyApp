package com.backcube.transactions.presentation.analyze.models

import com.backcube.ui.components.date.DateMode

internal sealed interface AnalyzeIntent {
    data object GoBack : AnalyzeIntent

    data object Refresh : AnalyzeIntent

    data class ShowCalendar(
        val dateMode: DateMode
    ) : AnalyzeIntent

    data class UpdateDate(
        val dateMode: DateMode,
        val date: Long?
    ) : AnalyzeIntent
}