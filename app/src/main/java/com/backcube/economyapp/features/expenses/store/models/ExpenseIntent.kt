package com.backcube.economyapp.features.expenses.store.models

sealed interface ExpenseIntent {
    data object GoToHistory : ExpenseIntent
}