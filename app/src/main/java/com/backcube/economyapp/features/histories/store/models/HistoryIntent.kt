package com.backcube.economyapp.features.histories.store.models

import com.backcube.economyapp.features.common.ui.date.DateMode

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