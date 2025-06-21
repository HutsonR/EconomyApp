package com.backcube.economyapp.features.expenses.store.models

sealed interface ExpenseEffect {
    data object NavigateToHistory : ExpenseEffect

    data object ShowClientError : ExpenseEffect
}