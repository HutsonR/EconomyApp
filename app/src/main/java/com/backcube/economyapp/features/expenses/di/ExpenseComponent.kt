package com.backcube.economyapp.features.expenses.di

import com.backcube.economyapp.features.expenses.ExpensesViewModel
import dagger.Subcomponent

@Subcomponent
interface ExpenseComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ExpenseComponent
    }

    val expensesViewModel: ExpensesViewModel
}