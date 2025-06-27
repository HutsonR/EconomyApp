package com.backcube.economyapp.features.histories.store.models

import com.backcube.economyapp.core.ui.components.date.DateMode

sealed interface HistoryIntent {
    data object GoBack : HistoryIntent

    data class ShowCalendar(
        val dateMode: DateMode
    ) : HistoryIntent

    data class UpdateDate(
        val dateMode: DateMode,
        val date: Long?
    ) : HistoryIntent
}