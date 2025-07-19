package com.backcube.transactions.presentation.analyze.models

import com.backcube.ui.components.date.DateMode

sealed interface AnalyzeEffect {
    data object GoBack : AnalyzeEffect

    data class ShowDatePickerModal(val dateMode: DateMode) : AnalyzeEffect

    data object ShowFetchError : AnalyzeEffect
}