package com.backcube.economyapp.features.histories.store.models

import com.backcube.economyapp.features.common.ui.date.DateMode

sealed interface HistoryEffect {
    data object GoBack : HistoryEffect

    data class ShowCalendar(
        val dateMode: DateMode
    ) : HistoryEffect

    data object ShowClientError : HistoryEffect
}