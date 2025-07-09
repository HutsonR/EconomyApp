package com.backcube.economyapp.features.histories.di

import com.backcube.economyapp.features.histories.HistoryViewModel
import dagger.Subcomponent

@Subcomponent
interface HistoryComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): HistoryComponent
    }

    val historyViewModel: HistoryViewModel
}