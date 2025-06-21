package com.backcube.economyapp.features.income.store.models

sealed interface IncomeIntent {
    data object GoToHistory : IncomeIntent
}