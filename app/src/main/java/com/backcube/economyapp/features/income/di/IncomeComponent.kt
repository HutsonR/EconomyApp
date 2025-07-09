package com.backcube.economyapp.features.income.di

import com.backcube.economyapp.features.income.IncomeViewModel
import dagger.Subcomponent

@Subcomponent
interface IncomeComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): IncomeComponent
    }

    val incomeViewModel: IncomeViewModel
}