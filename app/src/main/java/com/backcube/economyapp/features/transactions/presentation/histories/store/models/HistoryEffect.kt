package com.backcube.economyapp.features.transactions.presentation.histories.store.models

import com.backcube.economyapp.core.ui.components.date.DateMode

sealed interface HistoryEffect {
    data object GoBack : HistoryEffect

    data class ShowCalendar(
        val dateMode: DateMode
    ) : HistoryEffect

    data object ShowClientError : HistoryEffect
}